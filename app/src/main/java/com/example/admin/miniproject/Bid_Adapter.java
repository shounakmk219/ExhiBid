package com.example.admin.miniproject;

/**
 * Created by shounak on 1/9/17.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class Bid_Adapter extends RecyclerView.Adapter<Bid_Adapter.MyViewHolder> {

    private Context mContext;
    private List<Artwork> artworkList;
    private StorageReference storageReference_sale;
    private StorageReference storageReference_bid;
    DatabaseUser artist=new DatabaseUser();

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title, price,timer;
        public ImageView thmbimg;
        private Button bid_now;
        public ImageView wishy;
        //public int category;
        private StorageReference storageReference;
        public MyViewHolder(View view) {
            super(view);
            timer=(TextView)view.findViewById(R.id.timer);
            title = (TextView) view.findViewById(R.id.title);
            price = (TextView) view.findViewById(R.id.price);
            thmbimg = (ImageView) view.findViewById(R.id.thmbimg);
            bid_now=(Button)view.findViewById(R.id.bid_buy);
//            wishn=(ImageButton) view.findViewById(R.id.wish_list_no);
            wishy=(ImageView) view.findViewById(R.id.Instagram);
            wishy.setVisibility(View.INVISIBLE);
//            bid=(ImageButton) view.findViewById(R.id.place_bid);
//            cart=(ImageButton) view.findViewById(R.id.place_cart);
//            //thmbimg.setBackgroundResource(R.drawable.album1);
            bid_now.setText("Bid Now");
        }
    }
    public Bid_Adapter(Context mContext, List<Artwork> artworkList) {
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

        storageReference_bid =FirebaseStorage.getInstance().getReference("All_Paintings/").child(art.getThmbimg());
        storageReference_bid.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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

        CountDownTimer countDownTimer= new CountDownTimer(art.getStop()-System.currentTimeMillis(), 1000) {
            public void onTick(long millisUntilFinished) {
                holder.timer.setText("Ends in:- " + convert(millisUntilFinished/1000));
    }

            public void onFinish() {
                Log.w("A"+System.currentTimeMillis(),"B"+art.getStop());
            holder.timer.setText("Bid Over!!");
    }
        }.start();

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
//        holder.cart.setVisibility(View.INVISIBLE);
//        holder.bid.setVisibility(View.VISIBLE);



        holder.bid_now.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final Intent i=new Intent(mContext, Place_bid.class);
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

    private static String convert(long longmilli)
    {
        long s=longmilli%60;
        long m=(longmilli/60)%60;
        long h=(longmilli/(3600))%24;
        return String.format("%02dh:%02dm:%02ds",h,m,s);

    }

}
