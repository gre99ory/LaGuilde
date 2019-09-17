package com.ggpi.laguilde.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.LoginFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ggpi.laguilde.R;
import com.ggpi.laguilde.dialogs.AboutDialog;
import com.ggpi.laguilde.models.GGGlobals;
import com.ggpi.laguilde.models.GGPreferences;
import com.ggpi.laguilde.tools.VersionChecker;

import java.util.ArrayList;

public class SettingsActivity extends GuildeMenuBaseActivity  implements View.OnClickListener {

    //todo: boutons annuler n'annulent rien :)

    private AlertDialog filterDialog;
    private AlertDialog identityDialog;
    // private View filterView;

    private Switch swMagic;
    private Switch swPokemon;
    private Switch swMisc;
    
    private EditText editPseudo;
    private EditText editDCI;

    private Switch swTooltips;
    private Switch swCheckVersion;

    private Switch swOnBoardingDone;

    private int clicks = 0;

    ArrayList selectedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
         * Boutons
         */
        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
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

        Button btnFilter = findViewById(R.id.btnFilter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFilterDialog();
            }
        });

        Button btnIdentity = findViewById(R.id.btnIdentity);
        btnIdentity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showIdentityDialog();
            }
        });
        btnIdentity.setVisibility(View.GONE);

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
        buildFilterDialog();
        buildIdentityDialog();
        /*
        swMagic = findViewById(R.id.swMagic);
        swPokemon = findViewById(R.id.swPokemon);
        swMisc = findViewById(R.id.swMisc);
        */



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
        
        GGPreferences.setPseudo(editPseudo.getText().toString());
        GGPreferences.setDCI(editDCI.getText().toString());

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
        
        editPseudo.setText( GGPreferences.pseudo() );
        editDCI.setText( GGPreferences.DCI() );

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
    private void showAbout() {
        AboutDialog about = new AboutDialog(this);
        about.setTitle(getString(R.string.app_name));
        about.setDescription(getString(R.string.app_descrip));
        about.setIcon(R.drawable.laguilde_logo);
        about.build();
    }

    /*
     * Affichage des filtres
     */

    private void showFilterDialog() {
        filterDialog.show();
    }

    private void buildFilterDialog() {
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();
        View filterView = inflater.inflate(R.layout.settings_filter, null);
        swMagic = filterView.findViewById(R.id.swMagic);
        swPokemon = filterView.findViewById(R.id.swPokemon);
        swMisc = filterView.findViewById(R.id.swMisc);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(filterView)
                .setTitle(R.string.title_filters)
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                    }
                })
                /*
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                }
                )*/
                ;
        filterDialog = builder.create();
    }

    /*
     * Affichage des filtres
     */

    private void showIdentityDialog() {
        identityDialog.show();
    }

    private void buildIdentityDialog() {
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();
        View filterView = inflater.inflate(R.layout.settings_identity, null);

        editPseudo = filterView.findViewById(R.id.editPseudo);
        editDCI    = filterView.findViewById(R.id.editDCI);

        editPseudo.setFilters(new InputFilter[] {
                    new InputFilter.LengthFilter(16),
                    new LoginFilter.UsernameFilterGMail() });
        editDCI.setFilters(new InputFilter[] {
                    new InputFilter.LengthFilter(16)});

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(filterView)
                .setTitle(R.string.title_identity)
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //LoginDialogFragment.this.getDialog().cancel();
                    }
                });
        identityDialog = builder.create();
    }
}
