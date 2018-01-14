package com.example.admin.miniproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Place_bid extends AppCompatActivity {

    public String title, artist, description,email,phone,pid;
    public TextView price,bid;
    public ImageView image;
    int Bidding_Price;
    public int bidding_value;
    public Button pbid, minus, plus;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private StorageReference storageReference_bid;
    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_bid);
        //Bundle bundle= getIntent().getExtras();
        bidding_value=0;
        pbid=(Button)findViewById(R.id.pbid);
        price = (TextView) findViewById(R.id.bid_amount);
        bid = (TextView) findViewById(R.id.cur_bid);
        image = (ImageView) findViewById(R.id.bid_image);
        minus=(Button) findViewById(R.id.sub_btn);
        plus=(Button) findViewById(R.id.add_btn);
        expListView = (ExpandableListView) findViewById(R.id.Artistexp);

        pid=getIntent().getStringExtra("ID");
        String uid=getIntent().getStringExtra("USER");
        artist=getIntent().getStringExtra("Artist");
        title=getIntent().getStringExtra("Title");

        description=getIntent().getStringExtra("Description");
        email=getIntent().getStringExtra("Email");
        phone=getIntent().getStringExtra("Phone");

        storageReference_bid = FirebaseStorage.getInstance().getReference("All_Paintings/").child(pid);


        storageReference_bid.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(Place_bid.this).load(uri.toString()).override(300,500).crossFade().into(image);
            }
        });

        //title= bundle.getString("title");
        //artist= bundle.getString("artist");
        //description=bundle.getString("description");
        // preparing list data
        prepareListData();
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bidding_value>0)
                {
                    bidding_value=bidding_value-10;
                    bid.setText(""+bidding_value);
                }
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bidding_value=bidding_value+10;
                bid.setText(""+bidding_value);
            }
        });
        listAdapter = new ArtistExpandable(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        //price.setText("Current bid: "+bundle.getInt("price",0));
        //image.setBackgroundResource(bundle.getInt("image",0));
        bid.setText("0");
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();


        // Adding child data
        listDataHeader.add(title);
        listDataHeader.add(artist);


        // Adding child data
        List<String> Title = new ArrayList<String>();
        Title.add(description);

        List<String> Artist = new ArrayList<String>();
        Artist.add(phone);
        Artist.add(email);
        listDataChild.put(listDataHeader.get(0), Title); // Header, Child data
        listDataChild.put(listDataHeader.get(1), Artist);


        pbid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(bid.getText().toString())>0)
                {
                    FirebaseDatabase.getInstance().getReference("All_Paintings").child(pid).child("price").setValue(Bidding_Price+Integer.parseInt(bid.getText().toString()));
                }
                else
                {

                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseDatabase.getInstance().getReference("All_Paintings/"+pid).child("price").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                price.setText(dataSnapshot.getValue().toString());
                Bidding_Price=Integer.parseInt(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
