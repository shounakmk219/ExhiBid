package com.example.admin.miniproject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by shounak on 25/9/17.
 */

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.MyViewHolder> {
    private List<String> artist;
    private FirebaseDatabase firebaseDatabase;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public CheckBox checkBox;
        public Button button;

        public MyViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.Artist_name);
            checkBox = (CheckBox) view.findViewById(R.id.checkBox);
            button=(Button) view.findViewById(R.id.pop_add_filter);
        }

    }

    public FilterAdapter(List<String> artist)
    {
        this.artist = artist;
    }

    @Override
    public FilterAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artist_filter, parent, false);
        return new FilterAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final FilterAdapter.MyViewHolder holder, final int position) {

        final String user =artist.get(position);
        String name= Filter_Popup.displist.get(user);
        holder.textView.setText(name);
        if(Filter_Popup.checked.contains(user))
            holder.checkBox.setChecked(true);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.checkBox.isChecked()) {
                    Filter_Popup.checked.add(user);
                }
                else
                {
                    Filter_Popup.checked.remove(user);
                }


            }
        });



    }

    @Override
    public int getItemCount() {
        return artist.size();
    }

}