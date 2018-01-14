package com.example.admin.miniproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shounak on 8/9/17.
 */

public class On_bid extends Fragment {

    private RecyclerView recyclerView;
    private Bid_Adapter adapter;
    private List<Artwork> albumList,albumList2;
    private DatabaseReference databaseReference,databaseReference2;
    private StorageReference storageReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.on_bid, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_onbid);

        albumList = new ArrayList<>();
        albumList2= new ArrayList<>();
        //adapter = new Sale_Adapter(getActivity(), albumList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setItemViewCacheSize(1000);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        prepareArt();
        return rootView;
    }

    private void prepareArt() {

            databaseReference= FirebaseDatabase.getInstance().getReference("All_Paintings");
            if(Filter_Popup.checked.isEmpty() && Price_Filter.price_check.isEmpty()) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        albumList.clear();
                        for (DataSnapshot paintshot : dataSnapshot.getChildren()) {
                            if (paintshot.getValue(Artwork.class).getCategory() == 1) {
                                Artwork p = paintshot.getValue(Artwork.class);
                                albumList.add(p);
                            }
                        }
                        adapter = new Bid_Adapter(getActivity().getApplicationContext(), albumList);
                        recyclerView.setAdapter(adapter);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            else {
                if (Price_Filter.price_check.isEmpty()){
                    adapter = new Bid_Adapter(getActivity().getApplicationContext(), albumList2);
                    for(String s : Filter_Popup.checked)
                    {
                        databaseReference2= FirebaseDatabase.getInstance().getReference("Filter/ArtistFilter/"+s);
                        databaseReference2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                albumList2.clear();
                                for (DataSnapshot paintshot : dataSnapshot.getChildren()) {
                                    final String obj=paintshot.getValue(String.class);
                                    FirebaseDatabase.getInstance().getReference("All_Paintings/"+obj).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.getValue(Artwork.class).getCategory()==1) {
                                                albumList2.add(dataSnapshot.getValue(Artwork.class));
                                            }
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
                else {

                    adapter = new Bid_Adapter(getActivity().getApplicationContext(), albumList2);
                    for(String s: Price_Filter.price_check)
                    {
                        databaseReference2= FirebaseDatabase.getInstance().getReference("Filter/PriceFilter/"+s);
                        databaseReference2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                albumList2.clear();
                                for (DataSnapshot paintshot : dataSnapshot.getChildren()) {
                                    final String obj=paintshot.getValue(String.class);
                                    FirebaseDatabase.getInstance().getReference("All_Paintings/"+obj).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.getValue(Artwork.class).getCategory()==1) {
                                                albumList2.add(dataSnapshot.getValue(Artwork.class));
                                            }
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

            }

    }
}
