package com.example.admin.miniproject;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MyUploads extends AppCompatActivity {

    DatabaseReference databaseReference;
    Upload_Adapter adapter;
    FirebaseUser user;
    private RecyclerView recyclerView;
    List<My_UploadsInfo> my_uploadsInfos=new List<My_UploadsInfo>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<My_UploadsInfo> iterator() {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(@NonNull T[] ts) {
            return null;
        }

        @Override
        public boolean add(My_UploadsInfo my_uploadsInfo) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends My_UploadsInfo> collection) {
            return false;
        }

        @Override
        public boolean addAll(int i, @NonNull Collection<? extends My_UploadsInfo> collection) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public My_UploadsInfo get(int i) {
            return null;
        }

        @Override
        public My_UploadsInfo set(int i, My_UploadsInfo my_uploadsInfo) {
            return null;
        }

        @Override
        public void add(int i, My_UploadsInfo my_uploadsInfo) {

        }

        @Override
        public My_UploadsInfo remove(int i) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator<My_UploadsInfo> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<My_UploadsInfo> listIterator(int i) {
            return null;
        }

        @NonNull
        @Override
        public List<My_UploadsInfo> subList(int i, int i1) {
            return null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_uploads);

        user= FirebaseAuth.getInstance().getCurrentUser();
         FirebaseDatabase.getInstance().getReference("Uploads/"+user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot paintshot: dataSnapshot.getChildren()) {
                    my_uploadsInfos.add(paintshot.getValue(My_UploadsInfo.class));
                }
                adapter = new Upload_Adapter(getApplicationContext(), my_uploadsInfos);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }
}
