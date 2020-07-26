package com.ggpi.laguilde.activities;
/*
This Home Activity displays La Guilde's Card
and main menu to go to other activities:
    Events
    Winners
    Preferences
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.ggpi.laguilde.R;
import com.ggpi.laguilde.models.GGGlobals;
import com.ggpi.laguilde.models.GGPreferences;
import com.ggpi.laguilde.tools.GGConstants;
import com.ggpi.laguilde.tools.PromoChecker;

import java.util.Calendar;

public class HomeActivity extends GuildeMenuBaseActivity implements View.OnClickListener {

    PromoChecker promoChecker = null;
    FloatingActionButton btnPromo;
    FloatingActionButton btnDebug;
    TextView tvAddress;
    TextView tvPhone;
    TextView tvContact;
    TextView tvWebsite;

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

        tvAddress = findViewById(R.id.address);
        tvPhone = findViewById(R.id.phone);
        tvContact = findViewById(R.id.contact);
        tvWebsite = findViewById(R.id.website);

        tvAddress.setOnClickListener(this);
        tvPhone.setOnClickListener(this);
        tvContact.setOnClickListener(this);
        tvWebsite.setOnClickListener(this);


        loadEvents(null);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    /* Parametre necessaire pour layout:onClick */
    public void debugHome(View view) {

        Integer day =  GGGlobals.getInstance().getDay();
        GGGlobals.getInstance().setDay( day + 1 );

        promoChecker = new PromoChecker(this, this, day,12);
    }

    @Override
    public void onClick(View view) {
        if ( view == tvAddress )
        {
            // 74Q5+CX Witry-lès-Reims
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //intent.setData("geo:49.2885391,4.1077767,17z(La%20Guilde");
            //intent.setData(Uri.parse("geo:49.29,4.11?z=15(La%20Guilde)"));
            intent.setData(Uri.parse("geo:0,0?q=49.288572,4.109935(LaGuilde)"));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

        }

        if ( view == tvPhone )
        {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:+33 326406511"));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

        }
        if ( view == tvContact )
        {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, getString(R.string.cardContact));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Contact depuis l'app LaGuilde");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

        }
        if ( view == tvWebsite )
        {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(GGConstants.Api.LAGUILDE_URL));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }
/*
    private boolean confirm(String msg ) {
        boolean ret = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                this.ret = true;
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "You've changed your mind to delete all records", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }
    */
}
