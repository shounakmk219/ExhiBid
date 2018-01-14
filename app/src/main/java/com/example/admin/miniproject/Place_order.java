package com.example.admin.miniproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Place_order extends AppCompatActivity {

    static String title="", description="",email="",phone="",artist="",cost="";
    public TextView price;
    public ImageView image;
    public Button buy;
    private StorageReference storageReference_sale;
    private DatabaseReference databaseReference_sale,databaseReference_art;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        //Bundle bundle= getIntent().getExtras();
        price = (TextView) findViewById(R.id.order_amount);
        image = (ImageView) findViewById(R.id.order_image);
        expListView = (ExpandableListView) findViewById(R.id.Artistexp);

        String pid=getIntent().getStringExtra("ID");
        String uid=getIntent().getStringExtra("USER");
        artist=getIntent().getStringExtra("Artist");
        title=getIntent().getStringExtra("Title");
        description=getIntent().getStringExtra("Description");
        email=getIntent().getStringExtra("Email");
        phone=getIntent().getStringExtra("Phone");

             storageReference_sale = FirebaseStorage.getInstance().getReference("All_Paintings/").child(pid);
        storageReference_sale.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("BYE ","gfd");
                Glide.with(Place_order.this).load(uri.toString()).override(300,500).crossFade().into(image);
            }
        });
        //title= bundle.getString("title");
        //artist= bundle.getString("artist");
        //description=bundle.getString("description");
        // preparing list data
        prepareListData();


        listAdapter = new ArtistExpandable(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        //price.setText("Amount: "+bundle.getInt("price",0));
        //image.setBackgroundResource(bundle.getInt("image",0));



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
    }
}
