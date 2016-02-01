package com.digibase.bettingtips.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.digibase.bettingtips.network.ConnectionDetector;

import digibase.com.bettingtips.R;

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(!ConnectionDetector.isConnection(this)){
            Toast toast = Toast.makeText(getApplicationContext(), R.string.check_connection, Toast.LENGTH_LONG);
            toast.show();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2 * 1000);
    }
}