package com.ggpi.laguilde.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ggpi.laguilde.R;
import com.ggpi.laguilde.models.GGPreferences;
import com.ggpi.laguilde.tools.AndyUtils;
import com.ggpi.laguilde.tools.EventsLoader;
import com.ggpi.laguilde.tools.PromoChecker;
import com.ggpi.laguilde.tools.VersionChecker;
import com.tooltip.Tooltip;

public abstract class GuildeMenuBaseActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //Todo: ajouter le swipe gauche droite
    //Todo: adapter le menu ?

    /*
    Menu Setup
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // MenuItem mItem = null;
        // String mText = "Default";

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);
        switch ( this.getClass().getSimpleName() ) {
            case "HomeActivity" :
                menu.findItem(R.id.action_home).setVisible(false);
           //     mItem = menu.findItem(R.id.action_events);
             //   mText = "Evènements";
                break;
            case "EventsActivity" :
                menu.findItem(R.id.action_events).setVisible(false);
               // mItem = menu.findItem(R.id.action_winners);
             //   mText = "Résultats";
                break;
            case "ResultsActivity" :
                menu.findItem(R.id.action_winners).setVisible(false);
             //   mItem = menu.findItem(R.id.action_settings);
             //   mText = "Préférences";
                break;
            case "SettingsActivity" :
                menu.findItem(R.id.action_settings).setVisible(false);
                break;

        }

        /*
        if ( false && GGPreferences.showTooltips() && mItem != null ) {
            this.showToolTip(mItem,mText,Gravity.BOTTOM);
        }
        */

        return true;
    }

    /*
    public void showToolTip(MenuItem v, String text, int gravity) {
        // v.setActionView(R.layout.events_lv_item);
        Tooltip tip = new Tooltip.Builder(v)
                .setText(text)
                .setTextColor(Color.WHITE)
                .setGravity(gravity)
                .setCornerRadius(8f)
                .setDismissOnClick(true)
                .show();
    }
    */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            Intent homeIntent = new Intent(this,HomeActivity.class);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(homeIntent);
            return false;
        }
        if (id == R.id.action_events) {
            Intent eventsIntent = new Intent(this,EventsActivity.class);
            eventsIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(eventsIntent);
            return false;
        }
        if (id == R.id.action_winners) {
            Intent winnersIntent = new Intent(this, ResultsActivity.class);
            winnersIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(winnersIntent);
            return false;
        }

        if (id == R.id.action_settings) {
            Intent prefsIntent = new Intent(this, SettingsActivity.class);
            prefsIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(prefsIntent);
            return false;
        }

        return super.onOptionsItemSelected(item);
    }


    /* Parametre necessaire pour layout:onClick */
    public void loadEvents(View view) {
        //
        // if ( AndyUtils.isNetworkAvailable(this) ) {
            /*
             * Verification de la version
             * Si possible le laisser dans Splash
             */
            if (GGPreferences.checkVersion()) {
                new VersionChecker(this);
            }

            new EventsLoader(this);

            // FloatingActionButton fab = findViewById(R.id.fab);
            // fab.setVisibility(View.GONE);
        /*

         }
        else {
            Toast.makeText(this,R.string.err_no_network, Toast.LENGTH_LONG).show();
        }
        */
    }
}
