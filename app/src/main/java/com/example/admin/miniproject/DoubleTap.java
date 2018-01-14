package com.example.admin.miniproject;

import android.view.View;

/**
 * Created by ADMIN on 30-09-2017.
 */

public abstract class DoubleTap implements View.OnClickListener {

    private static final long DOUBLE_CLICK_TIME_DELTA = 300;//milliseconds

    long lastClickTime = 0;


    @Override
    public void onClick(View v) {

        long clickTime = System.currentTimeMillis();
        if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA){
            onDoubleClick(v);
            lastClickTime = 0;
        }
        lastClickTime = clickTime;

    }

    public abstract void onDoubleClick(View v);
}
