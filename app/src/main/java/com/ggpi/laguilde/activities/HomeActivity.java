package com.ggpi.laguilde.activities;
/*
This Home Activity displays La Guilde's Card
and main menu to go to other activities:
    Events
    Winners
    Preferences
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.ggpi.laguilde.R;
import com.ggpi.laguilde.models.GGPreferences;
import com.ggpi.laguilde.tools.AndyUtils;
import com.ggpi.laguilde.tools.EventsLoader;
import com.ggpi.laguilde.tools.VersionChecker;

public class HomeActivity extends GuildeMenuBaseActivity {


    //Todo: add a refresh button to reload events
    // Button refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        if (!GGPreferences.onBoardingDone()) {
            // The user hasn't seen the OnboardingFragment yet, so show it
            startActivity(new Intent(this, IntroActivity.class));
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        /*
        refresh = findViewById(R.id.fab);
        refresh.setOnClickListener(onClickListener);
        */

        loadEvents(null);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}
