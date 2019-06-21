package com.ggpi.laguilde.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ggpi.laguilde.R;
import com.ggpi.laguilde.tools.AndyUtils;
import com.ggpi.laguilde.tools.Notificator;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_TIME = 30;

    //Todo: recuperer la liste des events au demarrage

    private static Context context;

    public static Context getAppContext() {
        return SplashActivity.context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SplashActivity.context = getApplicationContext();

        setTheme(R.style.AppTheme_Splash);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                routeToPage();
                finish();
            }
        };

        // Handler Post Delayed
        new Handler().postDelayed(runnable, SPLASH_TIME );

        Notificator.createNotificationChannel(this.getApplicationContext());
        // isNetworkAvailable();
        // loadPreferences();
        // checkForUpdates();



        //Notificator.doNotify();


    }

    private void routeToPage() {
        // startActivity(new Intent(this, EventsActivity.class));
        //startActivity(new Intent(this, ResultsActivity.class));
        startActivity(new Intent(this, HomeActivity.class));
        // overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

}
