package com.example.admin.miniproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class Main3Activity extends AppCompatActivity {

    private EditText email,password;
    private Button login,reset;
    private TextView clickhere,forgot;
    public FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    public FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        firebaseAuth=FirebaseAuth.getInstance();

        email=(EditText)findViewById(R.id.editText5);
        password=(EditText)findViewById(R.id.editText6);
        login=(Button)findViewById(R.id.button2);
        reset=(Button)findViewById(R.id.reset);
        clickhere=(TextView)findViewById(R.id.clickhere);
        reset.setVisibility(View.INVISIBLE);

        forgot=(TextView)findViewById(R.id.textView);
//        authStateListener=new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                if(firebaseAuth.getCurrentUser()!=null)
//                {
//                    Toast.makeText(getApplicationContext(),"Login Successfully",Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(Main3Activity.this,Main2Activity.class));
//                    finish();
//                }
//            }
//        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn();
            }
        });


        clickhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Please Enter EmailId",Toast.LENGTH_SHORT).show();

                login.setVisibility(View.INVISIBLE);
                forgot.setVisibility(View.INVISIBLE);
                password.setVisibility(View.INVISIBLE);
                password.setHint("");
                clickhere.setVisibility(View.INVISIBLE);
                reset.setVisibility(View.VISIBLE);

                reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firebaseAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                login.setText("Login");
                                forgot.setVisibility(View.VISIBLE);
                                password.setVisibility(View.VISIBLE);
                                password.setHint("Password");
                                clickhere.setVisibility(View.VISIBLE);
                            }
                        });

                    }
                });


            }
        });

    }

    private void signIn()
    {
        String E=email.getText().toString();
        String P=password.getText().toString();

        if(!TextUtils.isEmpty(E) && !TextUtils.isEmpty(P))
        {
            firebaseAuth.signInWithEmailAndPassword(E,P).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(),"SignIn Problem or Enter a valid Email-Id and password",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        user=firebaseAuth.getCurrentUser();
                        //firebaseAuth.addAuthStateListener(authStateListener);
                        if(user.isEmailVerified())
                        {
                            Toast.makeText(getApplicationContext(),"Login Successfully",Toast.LENGTH_LONG).show();
                            Intent I=new Intent(Main3Activity.this,Main2Activity.class);
                            I.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(I);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Please Verify your Email",Toast.LENGTH_SHORT).show();
                            firebaseAuth.signOut();
                        }

                    }
                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please Fill in All Details",Toast.LENGTH_LONG).show();
        }



    }

}
