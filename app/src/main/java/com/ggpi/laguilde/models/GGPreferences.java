package com.ggpi.laguilde.models;

import android.content.SharedPreferences;

import java.util.UUID;

import static android.content.Context.MODE_PRIVATE;
import static com.ggpi.laguilde.activities.SplashActivity.getAppContext;

public class GGPreferences {
    private static final GGPreferences ourInstance = new GGPreferences();

    private String uniqueID = null;

    private boolean showMagic = true;
    private boolean showPokemon = true;
    private boolean showMisc = true;

    private boolean showTooltips = true;
    private boolean checkVersion = true;

    private static final String PREF_UNIQUE_ID = "PREFS_LAGUILDE";
    private static final String PREF_CHECK_VERSION = "CHECK_VERSION";
    private static final String PREF_TOOLTIPS = "SHOW_TOOLTIPS";
    private static final String PREF_MAGIC = "SHOW_MAGIC";
    private static final String PREF_POKEMON = "SHOW_POKEMON";
    private static final String PREF_MISC = "SHOW_MISC";

    private static final String IS_REGISTERED = "IS_REGISTERED_";

    private SharedPreferences prefs;

    public static GGPreferences getInstance() {
        return ourInstance;
    }

    private GGPreferences() {
        prefs = getAppContext().getSharedPreferences(
                PREF_UNIQUE_ID, MODE_PRIVATE);

        uniqueID = prefs.getString(PREF_UNIQUE_ID, null);

        /* Create unique ID if not set */
        if (uniqueID == null) {
            uniqueID = UUID.randomUUID().toString();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(PREF_UNIQUE_ID, uniqueID);
            editor.commit();
        }

        load();
    }


    public static String getUniqueID() {
        return getInstance().uniqueID;
    }

    public static void save() {
        getInstance().doSave();
    }

    private void doSave() {
        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean(PREF_CHECK_VERSION,checkVersion);
        editor.putBoolean(PREF_TOOLTIPS,showTooltips);

        editor.putBoolean(PREF_MAGIC,showMagic);
        editor.putBoolean(PREF_POKEMON,showPokemon);

        editor.putBoolean(PREF_MISC,showMisc);

        /*
        editor.putBoolean("swMagic",swMagic.isChecked());
        editor.putBoolean("swPokemon",swPokemon.isChecked());

        editor.putBoolean("cbMagic",cbMagic.isChecked());
        editor.putBoolean("cbPokemon",cbPokemon.isChecked());
        */

        editor.commit();
    }


    private void load() {
        showMagic = prefs.getBoolean(PREF_MAGIC,true);
        showPokemon = prefs.getBoolean(PREF_POKEMON,true);
        showMisc = prefs.getBoolean(PREF_MISC,true);

        showTooltips = prefs.getBoolean(PREF_TOOLTIPS,true);
        checkVersion = prefs.getBoolean(PREF_CHECK_VERSION,true);
    }

    static public boolean isRegistered( String eventId ) {
        return getInstance().prefs.getBoolean( IS_REGISTERED + eventId, false );
    }

    static public void setRegistered( String eventId, boolean value ) {
        SharedPreferences.Editor editor = getInstance().prefs.edit();

        editor.putBoolean(IS_REGISTERED + eventId, value );

        editor.commit();
    }



    static public boolean showMagic() {
        return getInstance().showMagic;
    }
    static public void setShowMagic(boolean b) {
        getInstance().showMagic = b;
    }

    static public boolean showPokemon() {
        return getInstance().showPokemon;
    }
    static public void setShowPokemon(boolean b) {
        getInstance().showPokemon = b;
    }

    static public boolean showMisc() {
        return getInstance().showMisc;
    }
    static public void setShowMisc(boolean b) {
        getInstance().showMisc = b;
    }

    static public boolean showTooltips() {
        return getInstance().showTooltips;
    }
    static public void setShowTooltips(boolean b) {
        getInstance().showTooltips = b;
    }

    static public boolean checkVersion() {
        return getInstance().checkVersion;
    }
    static public void setCheckVersion(boolean b) {
        getInstance().checkVersion = b;
    }


}
