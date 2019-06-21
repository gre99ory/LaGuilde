package com.ggpi.laguilde.models;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;

import static com.ggpi.laguilde.activities.SplashActivity.getAppContext;

public class GGGlobals {
    private boolean debug = false;
    private String uuid;
    private int versionNumber;
    private String versionName;

    private ArrayList<GGEventModel> ggEvents;


    /* This one should be the last */
    private static final GGGlobals ourInstance = new GGGlobals();

    public static GGGlobals getInstance() {
        return ourInstance;
    }

    // Private constructor
    private GGGlobals() {
        //
        try {
            PackageInfo pinfo = getAppContext().getPackageManager().getPackageInfo(getAppContext().getPackageName(), 0);
            versionNumber = pinfo.versionCode;
            versionName = pinfo.versionName;
        } catch ( PackageManager.NameNotFoundException e ) {
            e.printStackTrace();
        }

        uuid = GGPreferences.getUniqueID();
    }


    public static String getUUID() {
        return getInstance().uuid;
    }

    public static int getVersionNumber() {
        return getInstance().versionNumber;
    }

    public static String getVersionName() {
        return getInstance().versionName;
    }

    public static void setDebug(boolean debug) { getInstance().debug = debug; }

    public static boolean getDebug() { return getInstance().debug; }

    public static ArrayList<GGEventModel> getEvents() {
        return getInstance().ggEvents;
    }

    public static void setEvents(ArrayList<GGEventModel> ggEvents) {
        getInstance().ggEvents = ggEvents;
    }

}
