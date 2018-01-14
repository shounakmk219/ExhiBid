package com.example.admin.miniproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class Paintiing_Upload extends AppCompatActivity {


    ImageView painting_upload;
    EditText Decription,cost,Title;
    Button upload;
    int cat;
    CheckBox uploadBID,uploadSAlE;
    private StorageReference All_Paintings;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paintiing__upload);

        uploadBID=(CheckBox)findViewById(R.id.uploadBID);
        uploadSAlE=(CheckBox)findViewById(R.id.uploadSALE);
        painting_upload=(ImageView)findViewById(R.id.uploaded_image);
        upload=(Button)findViewById(R.id.upload);
        Decription=(EditText)findViewById(R.id.Description);
        cost=(EditText)findViewById(R.id.cost);
        Title=(EditText)findViewById(R.id.Title);
        progressDialog=new ProgressDialog(Paintiing_Upload.this);
        final FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        All_Paintings= FirebaseStorage.getInstance().getReference("All_Paintings/");
        Bitmap bitmap;
        try {
            Log.d("Hello","Hello");
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),Main2Activity.painting);
            painting_upload.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        uploadSAlE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    uploadSAlE.setChecked(true);
                    uploadBID.setChecked(false);
            }
        });

        uploadBID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    uploadBID.setChecked(true);
                    uploadSAlE.setChecked(false);

            }
        });
            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(uploadBID.isChecked() || uploadSAlE.isChecked())
                    {
                        final Artwork artwork;
                        databaseReference = FirebaseDatabase.getInstance().getReference("All_Paintings");
                        final String key = databaseReference.push().getKey();
                        progressDialog.setTitle("Please Wait");
                        progressDialog.setMessage("Uploading..");
                        progressDialog.show();
                        if (uploadSAlE.isChecked()) {
                            cat = 0;
                            artwork = new Artwork(Title.getText().toString(),user.getUid() ,Integer.parseInt(cost.getText().toString()), key,cat,Decription.getText().toString());
                        }
                        else
                        {
                            cat=1;
                            artwork = new Artwork(Title.getText().toString(),user.getUid() ,Integer.parseInt(cost.getText().toString()), key,cat,Decription.getText().toString(),System.currentTimeMillis(),System.currentTimeMillis()+9000000);
                        }

                        All_Paintings.child(key).putFile(Main2Activity.painting).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                databaseReference.child(key).setValue(artwork);
                                progressDialog.dismiss();

                                databaseReference= FirebaseDatabase.getInstance().getReference("Filter/ArtistFilter").child(user.getUid());
                                databaseReference.child(key).setValue(key);
                                databaseReference=FirebaseDatabase.getInstance().getReference("Uploads/").child(user.getUid());

                                String str=new String();
                                if(artwork.getCategory()==0)
                                    str="On Sale";
                                else
                                    str="On Bid";

                                My_UploadsInfo my_uploadsInfo=new My_UploadsInfo(artwork.getName(),String.valueOf(artwork.getPrice()),str);
                                databaseReference.child(key).setValue(my_uploadsInfo);

                                if(Integer.parseInt((String)cost.getText().toString())<500)
                                {
                                    databaseReference=FirebaseDatabase.getInstance().getReference("Filter/PriceFilter").child("LOW");
                                    databaseReference.child(key).setValue(key);
                                }
                                else if(Integer.parseInt(cost.getText().toString())>500 && Integer.parseInt(cost.getText().toString())<1500)
                                {
                                    databaseReference=FirebaseDatabase.getInstance().getReference("Filter/PriceFilter").child("MEDIUM");
                                    databaseReference.child(key).setValue(key);
                                }
                                else
                                {
                                    databaseReference=FirebaseDatabase.getInstance().getReference("Filter/PriceFilter").child("HIGH");
                                    databaseReference.child(key).setValue(key);
                                }

                                startActivity(new Intent(Paintiing_Upload.this, Main2Activity.class));
                            }
                        });


                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Please select Sale or Bid",Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
}
