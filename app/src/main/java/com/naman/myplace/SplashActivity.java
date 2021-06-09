package com.naman.myplace;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;



public class SplashActivity extends AppCompatActivity {


    private static final String TAG = "SplashActivity";
    private static final int MY_REQUEST_CODE = 1;
    private Context context;


    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        context = getApplicationContext();
        intent = new Intent(context, MyPlaceActivity.class);



        ScaleAnimation fade_in = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        fade_in.setDuration(250);
        fade_in.setFillAfter(true);
        findViewById(R.id.SplashScreenImage).startAnimation(fade_in);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MyPlaceActivity.class);
            startActivity(intent);
            finish();
        }, 500);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                Log.d(TAG, "Update flow failed! Result code: " + resultCode);

                // If the update is cancelled or fails,
                // you can request to start the update again.
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}