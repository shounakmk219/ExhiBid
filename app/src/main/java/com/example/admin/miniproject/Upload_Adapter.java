package com.example.admin.miniproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by shounak on 6/10/17.
 */

public class Upload_Adapter extends RecyclerView.Adapter<Upload_Adapter.MyViewHolder> {
    private Context mContext;
    private List<My_UploadsInfo> uploadsInfo;



    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView UploadArt,UploadPrice,UploadStatus;
        public MyViewHolder(View view)
        {
            super(view);
            UploadArt=(TextView) view.findViewById(R.id.UploadArt);
            UploadPrice=(TextView) view.findViewById(R.id.UploadPrice);
            UploadStatus=(TextView) view.findViewById(R.id.UploadStatus);
        }
    }

    public Upload_Adapter(Context context,List<My_UploadsInfo> uploadsInfo)
    {
        this.mContext=context;
        this.uploadsInfo=uploadsInfo;
    }

    @Override
    public Upload_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_uploads_card, parent, false);
        return new Upload_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Upload_Adapter.MyViewHolder holder, final int position) {
        final My_UploadsInfo my_uploadsInfo=uploadsInfo.get(position);
        holder.UploadArt.setText(my_uploadsInfo.getUploadArt());
        holder.UploadPrice.setText(my_uploadsInfo.getUploadPrice());
        holder.UploadStatus.setText(my_uploadsInfo.getUploadStatus());

    }




        @Override
    public int getItemCount()
    {
        return uploadsInfo.size();
    }
}
