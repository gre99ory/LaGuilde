package com.ggpi.laguilde.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ggpi.laguilde.R;

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);
        switch ( this.getClass().getSimpleName() ) {
            case "HomeActivity" :
                menu.findItem(R.id.action_home).setVisible(false);
                break;
            case "EventsActivity" :
                menu.findItem(R.id.action_events).setVisible(false);
                break;
            case "ResultsActivity" :
                menu.findItem(R.id.action_winners).setVisible(false);
                break;
            case "SettingsActivity" :
                menu.findItem(R.id.action_settings).setVisible(false);
                break;

        }
        return true;
    }

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

}
