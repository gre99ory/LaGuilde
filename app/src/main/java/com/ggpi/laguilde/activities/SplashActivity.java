package com.ggpi.laguilde.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.ggpi.laguilde.R;
import com.ggpi.laguilde.tools.Notificator;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_TIME = 1500;

    //Todo: verifier si context est encore pertinent

    private static Context context;

    public static Context getAppContext() {
        return SplashActivity.context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * Mise a jour du Theme pour affichage Splash Screen sans layout
         */
        SplashActivity.context = getApplicationContext();
        setTheme(R.style.AppTheme_Splash);

        // isNetworkAvailable();

        /*
         * Creation de la chaine pour les notifications
         */
        Notificator.createNotificationChannel(this.getApplicationContext());

        /*
         * Verification de la version
         * Peut afficher une fenetre dans ce cas, on devrait attendre
         * Todo: tester ceci une nouvelle fois
         */
        /*
        if ( GGPreferences.checkVersion() ) {
            new VersionChecker(this);
        }
        */


        /*
         * Chargement des evenements depuis http://laguilde-jeux.fr/~API~
         */
        // new EventsLoader(this);


        /*
         * Attendre durant le SPLASH_TIME
         */
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                /*
                 * Renvoyer vers la premiere page
                 */
                routeToPage();
                finish();
            }
        };
        new Handler().postDelayed(runnable, SPLASH_TIME );
    }

    /*
     * Choix de la page
     */
    private void routeToPage() {
        startActivity(new Intent(this, HomeActivity.class));
        // startActivity(new Intent(this, EventsActivity.class));
        //startActivity(new Intent(this, ResultsActivity.class));
        // overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

}
