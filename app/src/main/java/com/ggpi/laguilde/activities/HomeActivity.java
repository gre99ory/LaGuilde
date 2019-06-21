package com.ggpi.laguilde.activities;
/*
This Home Activity displays La Guilde's Card
and main menu to go to other activities:
    Events
    Winners
    Preferences
 */

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.ggpi.laguilde.R;
import com.ggpi.laguilde.models.GGPreferences;
import com.ggpi.laguilde.tools.VersionChecker;

public class HomeActivity extends GuildeMenuBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if ( GGPreferences.checkVersion() ) {
            new VersionChecker(this);
        }
    }
}
