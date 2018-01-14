package com.example.admin.miniproject;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class mkpay extends AppCompatActivity {

    private Button bb1;
    public static boolean pre = false;
    private AlertDialog.Builder builder;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mkpay);
        bb1 = (Button) findViewById(R.id.mkpremium);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }

        user= FirebaseAuth.getInstance().getCurrentUser();

        bb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference=FirebaseDatabase.getInstance().getReference("USER").child(user.getUid().toString()).child("balance");

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if((long)dataSnapshot.getValue()>500)
                        {
                            builder.setTitle("Make Premium")
                                    .setMessage("Confirm Payment")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            databaseReference= FirebaseDatabase.getInstance().getReference("Premium_Users").child(user.getUid().toString());
                                            databaseReference.setValue(new Premium_Info(getIntent().getStringExtra("Address").toString(),getIntent().getStringExtra("Adhaar")));
                                            databaseReference=FirebaseDatabase.getInstance().getReference("USER").child(user.getUid().toString()).child("balance");
                                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    long bal=(long)dataSnapshot.getValue();
                                                    bal=bal-500;
                                                    databaseReference.setValue(bal);
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });


                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();


                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Low Balance",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }

        });

       }

}






