package com.example.admin.miniproject;

/**
 * Created by shounak on 1/9/17.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by hadoop on 8/29/17.
 */

public class Sale_Adapter extends RecyclerView.Adapter<Sale_Adapter.MyViewHolder> {

    private Context mContext;
    private List<Artwork> artworkList;
    private StorageReference storageReference_sale;
    private StorageReference storageReference_bid;
    private FirebaseUser user;
    DatabaseUser artist=new DatabaseUser();

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView title, price;
        private ImageView thmbimg;
        private Button buy_now;
        private ImageView wishy;
        //public int category;
        private StorageReference storageReference;
        public MyViewHolder(View view) {
            super(view);
            buy_now=(Button)view.findViewById(R.id.bid_buy);
            title = (TextView) view.findViewById(R.id.title);
            price = (TextView) view.findViewById(R.id.price);
            thmbimg = (ImageView) view.findViewById(R.id.thmbimg);
            buy_now.setText("Buy Now");
//            wishn=(ImageButton) view.findViewById(R.id.wish_list_no);
            wishy=(ImageView) view.findViewById(R.id.Instagram);
            wishy.setVisibility(View.INVISIBLE);

//            bid=(ImageButton) view.findViewById(R.id.place_bid);
//            cart=(ImageButton) view.findViewById(R.id.place_cart);
//            //thmbimg.setBackgroundResource(R.drawable.album1);

        }
    }
    public Sale_Adapter(Context mContext, List<Artwork> artworkList) {
        this.mContext = mContext;
        this.artworkList = artworkList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.art_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Artwork art = artworkList.get(position);
        //holder.title.setText(art.getName());

        holder.price.setText(art.getPrice() + " coins");
        holder.title.setText(art.getName());

                    storageReference_sale =FirebaseStorage.getInstance().getReference("All_Paintings/").child(art.getThmbimg());
                    storageReference_sale.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(mContext.getApplicationContext()).load(uri.toString()).override(300,300).crossFade().into(holder.thmbimg);
                        }
                    });

        FirebaseDatabase.getInstance().getReference("USER/"+art.getArtist()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                artist=dataSnapshot.getValue(DatabaseUser.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //holder.thmbimg.setBackgroundResource(art.getThmbimg());

//        final Bundle b=new Bundle();
//        b.putString("title",art.getName());
//        //b.putInt("image", art.getThmbimg());
//        b.putString("artist",art.getArtist());
//        b.putInt("price", art.getPrice());
//        b.putString("description",art.getDescription());
//        holder.wishy.setVisibility(View.INVISIBLE);
//        holder.wishn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                holder.wishn.setVisibility(View.INVISIBLE);
//                holder.wishy.setVisibility(View.VISIBLE);
//            }
//        });
//        holder.wishy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                holder.wishy.setVisibility(View.INVISIBLE);
//                holder.wishn.setVisibility(View.VISIBLE);
//            }
//        });
//
//                holder.bid.setVisibility(View.INVISIBLE);
//                holder.cart.setVisibility(View.VISIBLE);
                holder.buy_now.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    final Intent i = new Intent(mContext, Place_order.class);
                    i.putExtra("ID", art.getThmbimg());
                    i.putExtra("Title", art.getName());
                    i.putExtra("Description","Description: "+art.getDescription());
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("Artist",artist.getDatabaseUser_name());
                    i.putExtra("USER", artist.getDatabaseUser_uid().toString());
                    i.putExtra("Email", "Email id: "+artist.getDatabaseUser_email().toString());
                    i.putExtra("Phone", "Phone: "+artist.getDatabaseUser_mobno().toString());
                    mContext.startActivity(i);

                }

            });

            holder.thmbimg.setOnClickListener(new DoubleTap() {
                @Override
                public void onDoubleClick(View v) {
                    animateHeart(holder.wishy);
                }
            });


    }

    private Animation prepareAnimation(Animation animation){
        animation.setRepeatCount(1);
        animation.setRepeatMode(Animation.REVERSE);
        return animation;
    }

    public void animateHeart(final ImageView view) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        prepareAnimation(scaleAnimation);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        prepareAnimation(alphaAnimation);

        AnimationSet animation = new AnimationSet(true);
        animation.addAnimation(alphaAnimation);
        animation.addAnimation(scaleAnimation);
        animation.setDuration(700);
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return artworkList.size();
    }
}
