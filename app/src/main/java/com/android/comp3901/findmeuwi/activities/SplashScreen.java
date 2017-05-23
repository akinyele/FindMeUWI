package com.android.comp3901.findmeuwi.activities;

/**
 * Created by Kyzer on 5/11/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.android.comp3901.findmeuwi.ui.main.MainActivity;

public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 4500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
//
//        new Handler().postDelayed(new Runnable() {
//
//            /*
//             * Showing splash screen with a timer. This will be useful when you
//             * want to show case your app logo / company
//             */
//
//            @Override
//            public void run() {
//                // This method will be executed once the timer is over
//                // Start your app main activity
//                Intent i = new Intent(SplashScreen.this, MainActivity.class);
//                startActivity(i);
//
//                // close this activity
//                finish();
//            }
//        }, SPLASH_TIME_OUT);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }

}