package com.ggpi.laguilde.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.ggpi.laguilde.R;
import com.ggpi.laguilde.models.GGPreferences;
import com.ggpi.laguilde.tools.EventsLoader;
import com.ggpi.laguilde.tools.VersionChecker;

public abstract class GuildeMenuBaseActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private static final int SWIPE_NONE = 0;
    private static final int SWIPE_LEFT = 1;
    private static final int SWIPE_RIGHT = 2;
    private static final int SWIPE_UP = 3;

    public static final int SWIPE_SEUIL = 100;
    public static final int SWIPE_VELOCITY_SEUIL = 100;

    protected GestureDetectorCompat detector;


    protected void onCreate(Bundle savedInstanceState) {
        Log.d("ggpi:onCreate", this.getLocalClassName() );

        super.onCreate(savedInstanceState);

        detector = new GestureDetectorCompat(this,this);
    }

    /* OnGestureListener Implementation */
    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent downEvent, MotionEvent moveEvent, float velocityX, float velocityY) {

        float diffY = moveEvent.getY() - downEvent.getY();
        float diffX = moveEvent.getX() - downEvent.getX();

        float aDiffX = Math.abs(diffX);
        float aDiffY = Math.abs(diffY);

        Log.d( "ggpi:onFling", " Down X:"+downEvent.getX()+"  Y:"+downEvent.getY() );
        Log.d( "ggpi:onFling", " Move X:"+moveEvent.getX()+"  Y:"+moveEvent.getY() );
        Log.d( "ggpi:onFling", "Diff Y:"+diffY+"  Velocity Y:"+velocityY+"  aDiff Y"+aDiffY );
        Log.d( "ggpi:onFling", "Diff X:"+diffX+"  Velocity X:"+velocityX+"  aDiff X"+aDiffX );

        if ( aDiffX > aDiffY ) {
            // Gauche / Droite
            if ( aDiffX > SWIPE_SEUIL && Math.abs(velocityX) > SWIPE_VELOCITY_SEUIL ) {
                if ( diffX > 0 ) {
                    return onSwipeRight();
                }
                else {
                    return onSwipeLeft();
                }
            }
        }
        else {
            // Up / Down
            if ( aDiffY > SWIPE_SEUIL && Math.abs(velocityY) > SWIPE_VELOCITY_SEUIL ) {
                if ( diffY > 0 ) {
                    return onSwipeDown();
                }
                else {
                    return onSwipeUp();
                }
            }
        }

        /*
        return false; // Event not handled
        return true; // Event handled
        */
        return false;
    }

    private boolean onSwipeRight() {
        switch ( this.getClass().getSimpleName() ) {
            case "HomeActivity":
                return false;

            case "EventsActivity":
                switchToActivity(HomeActivity.class, SWIPE_RIGHT );
                return true;

            case "ResultsActivity":
                switchToActivity(EventsActivity.class, SWIPE_RIGHT );
                return true;

            case "SettingsActivity":
                switchToActivity(ResultsActivity.class, SWIPE_RIGHT );
                return true;
        }

        return false;
    }

    private boolean onSwipeLeft() {
        switch ( this.getClass().getSimpleName() ) {
            case "HomeActivity":
                switchToActivity(EventsActivity.class, SWIPE_LEFT );
                return true;

            case "EventsActivity":
                switchToActivity(ResultsActivity.class, SWIPE_LEFT );
                return true;

            case "ResultsActivity":
                switchToActivity(SettingsActivity.class, SWIPE_LEFT );
                return true;

            case "SettingsActivity":
                return false;
        }

        return false;
    }

    private boolean onSwipeUp() {
        return false;
    }

    private boolean onSwipeDown() {
        return false;
    }
    /* End Of OnGestureListener Implementation */

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d( "ggpi:onTouchEvent", "X:"+event.getX()+"  Y:"+event.getY() );

        detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d( "ggpi:dispatchTouchEvent", "X:"+event.getX()+"  Y:"+event.getY() );
        detector.onTouchEvent(event);

        /*
        public boolean dispatchTouchEvent(MotionEvent event) {
            int eventaction=event.getAction();
            switch(eventaction) {
                case MotionEvent.ACTION_MOVE:
                    reg.setText("hey");
                    break;
                default:
                    break;
            }
            return super.dispatchTouchEvent(event);
        }
        */

        return super.dispatchTouchEvent(event);
    }


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
            //case "MagicActivity":
            //    menu.findItem(R.id.action_magic).setVisible(false);
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


    private void switchToActivity( Class param ) {
        switchToActivity(param,SWIPE_UP);
    }


    private void switchToActivity( Class param, int swipe ) {
        Intent homeIntent = new Intent(this, param );
        //homeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(homeIntent);
        if ( swipe == SWIPE_LEFT )
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        if ( swipe == SWIPE_RIGHT )
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        if ( swipe == SWIPE_UP )
            overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_down);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            switchToActivity(HomeActivity.class);
            return false;
        }
        if (id == R.id.action_events) {
            switchToActivity(EventsActivity.class);
            return false;
        }
        if (id == R.id.action_winners) {
            switchToActivity(ResultsActivity.class);
            return false;
        }
        /*
        if (id == R.id.action_magic) {
            Intent magicIntent = new Intent(this, MagicActivity.class);
            magicIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(magicIntent);
            return false;
        }
        */

        if (id == R.id.action_settings) {
            switchToActivity(SettingsActivity.class);
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
