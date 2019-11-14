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
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.example.ggpi.laguilde.R;
import com.ggpi.laguilde.models.GGGlobals;
import com.ggpi.laguilde.models.GGPreferences;
import com.ggpi.laguilde.tools.PromoChecker;

import java.util.Calendar;

public class HomeActivity extends GuildeMenuBaseActivity {

    PromoChecker promoChecker = null;
    FloatingActionButton btnPromo;
    FloatingActionButton btnDebug;

    private Calendar calendar;

    @Override
    protected void onResume() {
        super.onResume();
        if (GGGlobals.getDebug()) {
            btnDebug.show();
        }
        else {
            btnDebug.hide();
        }

        if ( promoChecker == null || !isSameDay()) {
            calendar = Calendar.getInstance();
            promoChecker = new PromoChecker(this, this);
        }
        // Attendre la fin de promoChecker //
        if ( promoChecker.isPromo() ) {
            btnPromo.show();
        }
        else {
            btnPromo.hide();
        }
    }

    /* Parametre necessaire pour layout:onClick */
    public void showPromo(View view) {
        promoChecker.showDialog();
    }

    private boolean isSameDay() {
        Calendar now = Calendar.getInstance();
        if ( calendar == null ) return false;

        return ( calendar.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH)
                &&
                calendar.get(Calendar.MONTH) == now.get(Calendar.MONTH) );
    }

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

        btnPromo = findViewById(R.id.fpb);
        btnDebug = findViewById(R.id.dbgHome);


        loadEvents(null);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    /* Parametre necessaire pour layout:onClick */
    public void debugHome(View view) {

        Integer day =  GGGlobals.getInstance().getDay();
        GGGlobals.getInstance().setDay( day + 1 );

        promoChecker = new PromoChecker(this, this, day,12);
    }

}
