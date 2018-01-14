package com.example.admin.miniproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shounak on 5/10/17.
 */

public class Price_Filter extends Activity {

    private CheckBox low;
    private CheckBox medium;
    private CheckBox high;
    private Button button;
    public static List<String> price_check=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.price_filter);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.5), (int) (height * 0.3));

        low=(CheckBox) findViewById(R.id.low);
        medium=(CheckBox) findViewById(R.id.medium);
        high=(CheckBox) findViewById(R.id.high);

        button=(Button) findViewById(R.id.add_filter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Filter_Popup.checked.clear();
                startActivity(new Intent(Price_Filter.this,Main2Activity.class));
                finish();
            }
        });

        if(price_check.contains("LOW"))
            low.setChecked(true);
        if(price_check.contains("MEDIUM"))
            medium.setChecked(true);
        if(price_check.contains("HIGH"))
            high.setChecked(true);
        low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(low.isChecked()) {
                    price_check.add("LOW");
                }
                else
                {
                    price_check.remove("LOW");
                }


            }
        });

        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(medium.isChecked()) {
                    price_check.add("MEDIUM");
                }
                else
                {
                    price_check.remove("MEDIUM");
                }


            }
        });

        high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(high.isChecked()) {
                    price_check.add("HIGH");
                }
                else
                {
                    price_check.remove("HIGH");
                }


            }
        });
    }
}
