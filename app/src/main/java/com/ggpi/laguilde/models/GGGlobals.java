package com.ggpi.laguilde.models;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.Calendar;

import static com.ggpi.laguilde.activities.SplashActivity.getAppContext;

public class GGGlobals {
    private boolean debug = false;
    private String uuid;
    private int versionNumber;
    private String versionName;

    private ArrayList<GGEventModel> ggEvents;

    private Integer debugDay;
    private Integer debugMonth;


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

        Calendar cal = Calendar.getInstance();
        debugDay = cal.get(Calendar.DAY_OF_MONTH);
        debugMonth = cal.get(Calendar.MONTH);

        debugDay = 1;
        debugMonth = 12;
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

    public void setDay( Integer day ) {
        debugDay = day;
    }

    public void setMonth( Integer month ) {
        debugMonth = month;
    }

    public Integer getDay() {
        return debugDay;
    }

    public Integer getMonth() {
        return debugMonth;
    }

}
