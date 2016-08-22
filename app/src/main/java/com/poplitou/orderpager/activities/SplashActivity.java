package com.poplitou.orderpager.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.poplitou.orderpager.utils.AuthUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent mainIntent = new Intent(this, MainActivity.class);
        Intent introIntent = new Intent(this, IntroActivity.class);

        if(!AuthUtils.isSignedIn()) {
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(mainIntent);
            startActivity(introIntent);
        } else {
            startActivity(mainIntent);
        }
        finish();
    }
}
