package com.example.admin.miniproject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import junit.framework.Test;

import de.hdodenhof.circleimageview.CircleImageView;

public class settings extends AppCompatActivity {

    private static final long DOUBLE_CLICK_TIME_DELTA = 300;
    long lastClickTime = 0;

    private TextView setting_premium_textview;
    private  Button make_premium;
    private AutoCompleteTextView username,email,phone;
    private CircleImageView setting_profileimage;
    private Button apply;
    private ProgressDialog progressDialog;
    private StorageReference profile;
    private DatabaseReference databaseReference;
    FirebaseUser user;
    private  AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }

        user= FirebaseAuth.getInstance().getCurrentUser();
        String profileID=user.getUid();


        username=(AutoCompleteTextView)findViewById(R.id.setting_username);
        email=(AutoCompleteTextView)findViewById(R.id.setting_email);
        phone=(AutoCompleteTextView)findViewById(R.id.setting_phone);


        setting_premium_textview=(TextView) findViewById(R.id.setting_premium_textview);
        make_premium =(Button)findViewById(R.id.setting_premium);
        setting_profileimage=(CircleImageView)findViewById(R.id.setting_profilephoto);
        apply=(Button)findViewById(R.id.setting_Apply_premium);

        databaseReference=FirebaseDatabase.getInstance().getReference("Premium_Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    Log.w("Heloooooo",dataSnapshot.child(user.getUid()).toString());
                    if(dataSnapshot.child(user.getUid())!=null)
                    {
                        setting_premium_textview.setVisibility(View.VISIBLE);
                        make_premium.setVisibility(View.INVISIBLE);
//                        setting_premium_textview.setOnClickListener(new DoubleTap() {
//                            @Override
//                            public void onDoubleClick(View v) {
//                                Log.w("Ha","Hoaya");
//                                animateHeart(setting_premium_textview);
//
//                            }
//                        });
                    }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference=FirebaseDatabase.getInstance().getReference("USER").child(user.getUid().toString()).child("databaseUser_mobno");

        username.setText(user.getDisplayName());
        email.setText(user.getEmail());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange (DataSnapshot dataSnapshot){

                        String value = dataSnapshot.getValue().toString();
                        phone.setText(value);
            }

            @Override public void onCancelled (DatabaseError databaseError){

            }
        });

        profile = FirebaseStorage.getInstance().getReference("Profile Photos/").child(profileID);
        profile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(settings.this).load(uri.toString()).centerCrop().crossFade().into(setting_profileimage);
            }
        });


        make_premium.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent myIntent = new Intent(settings.this,makepre.class);
                startActivityForResult(myIntent,0);
            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Apply Changes")
                        .setMessage("Are you sure you want to Apply Changes")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                databaseReference=FirebaseDatabase.getInstance().getReference("USER").child(user.getUid().toString());
                                String click_username=username.getText().toString();
                                String click_email=email.getText().toString();
                                String click_phone=phone.getText().toString();

                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(click_username)
                                        .build();
                                user.updateProfile(profileUpdates);

                                databaseReference.child("databaseUser_mobno").setValue(click_phone);
                                databaseReference.child("databaseUser_name").setValue(click_username);
                                Toast.makeText(getApplicationContext(),"Changes Applied Successfully",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(settings.this,Main2Activity.class);
                                i.putExtra("Username",click_username);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mkpay.pre==false)
        {
            setting_premium_textview.setVisibility(View.INVISIBLE);
            make_premium.setVisibility(View.VISIBLE);
        }
        else
        {
            setting_premium_textview.setVisibility(View.VISIBLE);
            make_premium.setVisibility(View.GONE);
        }

    }

}
