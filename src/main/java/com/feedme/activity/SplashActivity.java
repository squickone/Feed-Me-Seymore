package com.feedme.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.feedme.R;

public class SplashActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
//        getApplicationContext().deleteDatabase("babyData");
        
        /** set time to splash out */
        final int welcomeScreenDisplay = 4000;
        /** create a thread to show splash up to splash time */
        Thread welcomeThread = new Thread() {

            int wait = 0;

            @Override
            public void run() {
                try {
                    super.run();
                    /**
                     * use while to get the splash time. Use sleep() to increase
                     * the wait variable for every 100L.
                     */
                    while (wait < welcomeScreenDisplay) {
                        sleep(100);
                        wait += 100;
                    }
                } catch (Exception e) {
                    System.out.println("EXc=" + e);
                } finally {
                    /**
                     * Called after splash times up. Do some action after splash
                     * times up. Here we moved to another main activity class
                     */
                    startActivity(new Intent(SplashActivity.this,
                            HomeActivity.class));
                    finish();
                }
            }
        };
        welcomeThread.start();}
}