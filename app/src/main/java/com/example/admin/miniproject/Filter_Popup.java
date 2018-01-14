package com.example.admin.miniproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shounak on 24/9/17.
 */

public class Filter_Popup extends Activity {

    private RecyclerView recyclerView;
    private FilterAdapter adapter;
    public static HashMap<String,String> displist;
    public static List<String> checked=new ArrayList<>();
    private List<String> userList;
    private View view;
    private DatabaseReference databaseReference;
    private Button button,button2;

   /* Filter_Popup(){
        checked=new ArrayList<>();
    }*/


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_window);

        button2=(Button) findViewById(R.id.pop_clear_filter);
        button=(Button) findViewById(R.id.pop_add_filter);
        /*View rootView = inflater.inflate(R.layout.artist_filter, container, false);*/
        recyclerView = (RecyclerView) findViewById(R.id.filter_recycler);

        displist = new HashMap<>();
        userList=new ArrayList<>();

        prepareAlbums();



        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);




        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.8));
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checked.clear();
                startActivity(new Intent(Filter_Popup.this,Main2Activity.class));
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Price_Filter.price_check.clear();
                startActivity(new Intent(Filter_Popup.this,Main2Activity.class));
                finish();
            }
        });
    }

    private void prepareAlbums() {

        databaseReference = FirebaseDatabase.getInstance().getReference("Filter/ArtistFilter");
        userList.clear();
        displist.clear();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot paintshot : dataSnapshot.getChildren()) {
                    final String p = paintshot.getKey();
                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("USER");

                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            DatabaseUser obj=new DatabaseUser();
                            for(DataSnapshot usersnap : dataSnapshot.getChildren())
                            {
                                if(usersnap.getKey().equals(p))
                                {
                                    obj=usersnap.getValue(DatabaseUser.class);
                                    break;
                                }
                            }
                            displist.put(obj.getDatabaseUser_uid(),obj.getDatabaseUser_name());
                            userList.add(obj.getDatabaseUser_uid());
                            adapter = new FilterAdapter(userList);
                            recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}