package com.mohanad.myownbank.view.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;

import com.mohanad.myownbank.R;

public class LandActivity extends AppCompatActivity {
    private static int TIME_OUT=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LandActivity.this, LoginActivity.class));
                finish();
            }
        },TIME_OUT);



    }


}
