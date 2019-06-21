package com.ggpi.laguilde.activities;

import android.app.AlertDialog;
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

    //Todo: activer les switches
    //Todo: ajouter une fenetre a propos ggPi

    private Switch swMagic;
    private Switch swPokemon;
    private Switch swMisc;

    private Switch swTooltips;
    private Switch swCheckVersion;

    private int clicks = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // getCon
        //findViewById( android.R.id.content ).setOnClickListener( this );


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
              new VersionChecker(view.getContext());
          }
        });

        Button btnAbout = findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            showAbout();
            }              });

        TextView tvVersion = findViewById(R.id.tvVersion);
        String version = GGGlobals.getVersionName();

        // Recuperer la version dispo

        tvVersion.setText( getResources().getString( R.string.current_version ) + ": " + version );
        tvVersion.setOnClickListener( this );

        TextView tvUUID = findViewById(R.id.tvUUID);
        tvUUID.setText( GGGlobals.getUUID() );

        /* Get Widgets */
        swMagic = findViewById(R.id.swMagic);
        swPokemon = findViewById(R.id.swPokemon);
        swMisc = findViewById(R.id.swMisc);

        swMagic.setVisibility(View.GONE);
        swPokemon.setVisibility(View.GONE);
        swMisc.setVisibility( View.GONE );

        swTooltips = findViewById(R.id.swTooltips);
        swCheckVersion = findViewById(R.id.swCheckVersion);
    }


    @Override
    protected void onResume() {
        super.onResume();
        clicks = 0;
        prefsLoad();
    }

    private void prefsSave() {
        GGPreferences.setShowMagic(swMagic.isChecked());
        GGPreferences.setShowPokemon(swPokemon.isChecked());
        GGPreferences.setShowMisc(swMisc.isChecked());

        GGPreferences.setShowTooltips( swTooltips.isChecked() );
        GGPreferences.setCheckVersion(swCheckVersion.isChecked());

        GGPreferences.save();
    }


    private void prefsLoad() {
        swMagic.setChecked( GGPreferences.showMagic() );
        swPokemon.setChecked( GGPreferences.showPokemon() );
        swMisc.setChecked( GGPreferences.showMisc() );

        swTooltips.setChecked( GGPreferences.showTooltips() );
        swCheckVersion.setChecked( GGPreferences.checkVersion() );
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
    private void prefsDo() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(keyString, valueString);
        putBoolean
        putFloat
        putLong
        putInt
        putStringSet

        remove

        editor.commit();

        String fileNameString = sharedPreferencesBinding.fileNameEditView.getText().toString();
        SharedPreferences sharedPreferences;
        if(fileNameString.isEmpty()) {
            sharedPreferences = getPreferences(MODE_PRIVATE);
        }
        else {
            sharedPreferences = getSharedPreferences(fileNameString, MODE_PRIVATE);
        }
    }
        */

    public void showAbout() {

        AboutDialog about = new AboutDialog(this);
        about.setTitle(getString(R.string.app_name));
        about.setDescription(getString(R.string.app_descrip));
        about.setIcon(R.drawable.laguilde_logo);
        about.build();

        /*

        // Inflate the about message contents
        View messageView = getLayoutInflater().inflate(R.layout.about, null, false);

        // When linking text, force to always use default color. This works
        // around a pressed color state bug.
        TextView textView = (TextView) messageView.findViewById(R.id.about_credits);
        int defaultColor = textView.getTextColors().getDefaultColor();
        textView.setTextColor(defaultColor);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.laguilde_logo);
        builder.setTitle(R.string.app_name);
        builder.setView(messageView);
        builder.create();
        builder.show();
        */
    }
}
