package com.example.admin.miniproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {



    private TextInputEditText name,email,mobno,password;
    private TextView login;
    private Button signup;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog=new ProgressDialog(MainActivity.this);
        login=(TextView)findViewById(R.id.login);
        name= (TextInputEditText) findViewById(R.id.editText);
        email= (TextInputEditText) findViewById(R.id.editText3);
        mobno= (TextInputEditText) findViewById(R.id.editText4);
        password= (TextInputEditText) findViewById(R.id.editText2);

        signup=(Button)findViewById(R.id.button);

        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("USER");

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(name.getText().toString()) && !TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(mobno.getText().toString()) && !TextUtils.isEmpty(password.getText().toString()))
                {
                    if(mobno.getText().toString().length()==10)
                    {
                        progressDialog.setTitle("Verifying Email");
                        progressDialog.setMessage("Loading Please Wait..");
                        progressDialog.show();
                        Signup();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Please Enter a Valid Phone No.",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Enter all the Fields",Toast.LENGTH_SHORT).show();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Main3Activity.class));
            }
        });


//        authStateListener=new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                if(firebaseAuth.getCurrentUser()!=null)
//                {
//                    startActivity(new Intent(MainActivity.this,Main2Activity.class));
//                    //finish();
//
//                }
//            }
//        };

    }

    @Override
    protected void onStart() {
        super.onStart();
//        firebaseAuth.addAuthStateListener(authStateListener);

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            startActivity(new Intent(MainActivity.this,Main2Activity.class));
            finish();
        }

    }

    private void Signup()
    {
        String E=email.getText().toString();
        String P=password.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(E,P).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            final FirebaseUser user=firebaseAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name.getText().toString())
                                    .build();
                            user.updateProfile(profileUpdates);



                            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        String store_name,store_email,store_mobno;
                                        store_email=email.getText().toString();
                                        store_name=name.getText().toString();
                                        store_mobno=mobno.getText().toString();
                                        //String key=databaseReference.push().getKey();
                                        String profilephoto=null;
                                        //Map m=null;
                                        DatabaseUser databaseuser= new DatabaseUser(store_name,store_email,store_mobno,user.getUid().toString(),profilephoto,1000);
                                        databaseReference.child(user.getUid().toString()).setValue(databaseuser);

                                            firebaseAuth.signOut();
                                           startActivity(new Intent(MainActivity.this,Main3Activity.class));
                                            finish();
                                            progressDialog.dismiss();
                                    }
                                    else
                                    {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(),"Failed to send Email",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

//    private void Add_user_info()
//    {
//        //DatabaseReference mroot=FirebaseDatabase.getInstance().getReference();
//      // DatabaseReference s= mroot.child("USER").push();
//         String currentid = firebaseAuth.getCurrentUser().getUid();
//
//      String  rootref="USER"+"/"+currentid;
//        DatabaseReference db1=databaseReference.child("USER").push();
//        //DatabaseReference db=db1.push();
//        String rootid=db1.getKey();
//
//
//        String store_name,store_email,store_mobno;
//        store_email=email.getText().toString();
//        store_name=name.getText().toString();
//        store_mobno=mobno.getText().toString();
//        String key=databaseReference.push().getKey();
//       // databaseReference=databaseReference.child(key);
//       //Map userdeatails=new HashMap();
//       //userdeatails.put("NAME","SAGAR");
//
//        //Map user=new HashMap();
//       // user.put(rootref+"/"+rootid,user);
//
//        //DatabaseUser databaseuser= new DatabaseUser(store_name,store_email,store_mobno);
//        //databaseReference.setValue(databaseuser);
//        Toast.makeText(getApplicationContext(),"Ho Gaya",Toast.LENGTH_SHORT).show();
//// mroot.updateChildren(userdeatails, new DatabaseReference.CompletionListener() {
////            @Override
////            public void onComplete(DatabaseError databaseError, DatabaseReference s) {
////
////            }
////        });
//    }

}
