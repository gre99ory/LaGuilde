package com.ggpi.laguilde.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import com.example.ggpi.laguilde.R;
import com.ggpi.laguilde.dialogs.PromoDialog;
import com.ggpi.laguilde.models.GGGlobals;
import com.ggpi.laguilde.models.GGPreferences;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class PromoChecker extends ApiCall {

    private String gameName;
    private String imageUrl;
    private String linkUrl;
    private String date;
    private boolean forceDisplay;
    private Activity activity;


    public PromoChecker(final Activity activity,Context ctx) {
        super(GGConstants.Api.URL_READ_PROMO,null,METHOD_POST,ctx);

        /*
        this.params = new HashMap<>();
        this.params.put("jour", Integer.toString(22) );
        this.params.put("mois", Integer.toString(11) );
        */

        this.activity = activity;
        this.forceDisplay = false;

        executeAndGet();
    }

    public PromoChecker(final Activity activity,Context ctx, Integer day, Integer month) {
        super(GGConstants.Api.URL_READ_PROMO,null,METHOD_POST,ctx);

        this.params = new HashMap<>();
        this.params.put("jour", Integer.toString(day) );
        this.params.put("mois", Integer.toString(month) );

        this.activity = activity;
        this.forceDisplay = false;

        executeAndGet();
    }

    public PromoChecker(final Activity activity,Context ctx, boolean forceDisplay) {
        super(GGConstants.Api.URL_READ_PROMO,null,METHOD_POST,ctx);
        this.activity = activity;
        this.forceDisplay = forceDisplay;

        gameName = "Nope";

        executeAndGet();
    }

    private void executeAndGet() {
        try {
            execute();
            get();
        }
        catch(InterruptedException e) {
            return;
        }
        catch(ExecutionException e) {
            return;
        }
    }

    public boolean isPromo() {
        return ( gameName != null && gameName.length() > 0 && gameName.compareTo("none") != 0 );
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if ( !this.wasOk ) {
            return;
        }

        try {
            gameName = object.getString("gameName");
            date     = object.getString("date");
            // Les deux premieres lignes sont OK meme si pas de promo sur ce jour
            imageUrl = object.getString("image");
            linkUrl  = object.getString("link");
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        if ( isPromo() ) {
            showDialog();
        }
    }

    public void setParams(HashMap params ) {
        this.params = params;
    }

    /*
     * Nouvelle version disponible
     */
    public void showDialog() {
        PromoDialog promo = new PromoDialog(this.activity);
        promo.setGameName(gameName);
        promo.setImageUrl(imageUrl);
        promo.setLinkUrl(linkUrl);
        promo.setDate(date);

        promo.build();
/*
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle( context.getString(R.string.title_new_version))
                .setMessage( context.getString(R.string.text_new_version) + " " + newVersion )
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(
                        context.getString(R.string.download),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(GGConstants.Api.MARKET_URL)));
                                dialog.cancel();
                            }
                        })
                .setNegativeButton(
                        context.getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .show();
                */
    }


}
