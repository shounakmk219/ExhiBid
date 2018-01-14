package com.example.admin.miniproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class makepre extends AppCompatActivity {

    private Button payment;
    private EditText address,adhaar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makepre);
        payment=(Button) findViewById(R.id.make_payment);
        address=(EditText) findViewById(R.id.editText_makepre);
        adhaar=(EditText) findViewById(R.id.editText2_makepre);

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
//            public void onClick(View v) {
//              pre=true;
//                finish();
//            }
            public void onClick(View view){
                if(!TextUtils.isEmpty(address.getText().toString()) && !TextUtils.isEmpty(adhaar.getText().toString()))
                {
                    Intent myIntent1 = new Intent(view.getContext(),mkpay.class);
                    myIntent1.putExtra("Address",address.getText().toString());
                    myIntent1.putExtra("Adhaar",adhaar.getText().toString());
                    startActivity(myIntent1);

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please Enter All Details",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
