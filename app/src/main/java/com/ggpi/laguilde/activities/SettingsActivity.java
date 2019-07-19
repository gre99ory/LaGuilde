package com.ggpi.laguilde.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ggpi.laguilde.R;
import com.ggpi.laguilde.dialogs.AboutDialog;
import com.ggpi.laguilde.models.GGGlobals;
import com.ggpi.laguilde.models.GGPreferences;
import com.ggpi.laguilde.tools.VersionChecker;

public class SettingsActivity extends GuildeMenuBaseActivity  implements View.OnClickListener {

    private Switch swMagic;
    private Switch swPokemon;
    private Switch swMisc;

    private Switch swTooltips;
    private Switch swCheckVersion;

    private Switch swOnBoardingDone;

    private int clicks = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /*
         * Boutons
         */
        Button btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                prefsSave();
                finish();
            }
        });

        Button btnVersion = findViewById(R.id.btnVersion);
        btnVersion.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              new VersionChecker(view.getContext(),true);
          }
        });


        Button btnAbout = findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            showAbout();
            }              });


        /*
         * Textes
         */
        TextView tvVersion = findViewById(R.id.tvVersion);
        String version = GGGlobals.getVersionName();
        tvVersion.setText( getResources().getString( R.string.current_version ) + ": " + version );
        tvVersion.setOnClickListener( this );

        TextView tvUUID = findViewById(R.id.tvUUID);
        tvUUID.setText( GGGlobals.getUUID() );
        // tvUUID.setVisibility(View.GONE);



        /*
         * Recuperation des objets pour gerer les preferences
         */
        swMagic = findViewById(R.id.swMagic);
        swPokemon = findViewById(R.id.swPokemon);
        swMisc = findViewById(R.id.swMisc);

        swTooltips = findViewById(R.id.swTooltips);
        swCheckVersion = findViewById(R.id.swCheckVersion);

        swOnBoardingDone = findViewById(R.id.swOnBoardingDone);

        // swMagic.setVisibility(View.GONE);
        // swPokemon.setVisibility(View.GONE);
        // swMisc.setVisibility( View.GONE );
        swTooltips.setVisibility(View.GONE);
        // swCheckVersion.setVisibility(View.GONE);
        // btnVersion.setVisibility(View.GONE);
        // swOnBoardingDone.setVisibility(View.GONE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        clicks = 0;
        prefsLoad();
    }

    /*
     * Enregistrement des valeurs en local
     */
    private void prefsSave() {
        GGPreferences.setShowMagic(swMagic.isChecked());
        GGPreferences.setShowPokemon(swPokemon.isChecked());
        GGPreferences.setShowMisc(swMisc.isChecked());

        GGPreferences.setShowTooltips( swTooltips.isChecked() );
        GGPreferences.setCheckVersion(swCheckVersion.isChecked());

        GGPreferences.setOnBoardingDone(swOnBoardingDone.isChecked());

        GGPreferences.save();
    }

    /*
     * Initialisation des valeurs
     */
    private void prefsLoad() {
        swMagic.setChecked( GGPreferences.showMagic() );
        swPokemon.setChecked( GGPreferences.showPokemon() );
        swMisc.setChecked( GGPreferences.showMisc() );

        swTooltips.setChecked( GGPreferences.showTooltips() );
        swCheckVersion.setChecked( GGPreferences.checkVersion() );

        swOnBoardingDone.setChecked( GGPreferences.onBoardingDone() );
    }

    @Override
    public void onClick(View view) {
        clicks++;
        if ( clicks == 10 ) {
           Toast.makeText(view.getContext(), "Debug Mode Is ON", Toast.LENGTH_SHORT).show();
           GGGlobals.setDebug(true);
        }
    }


    /*
     * Affichage de la fenetre A Propos
     */
    public void showAbout() {
        AboutDialog about = new AboutDialog(this);
        about.setTitle(getString(R.string.app_name));
        about.setDescription(getString(R.string.app_descrip));
        about.setIcon(R.drawable.laguilde_logo);
        about.build();
    }
}
