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

import java.util.HashMap;

public class PromoChecker extends ApiCall {

    private String gameName;
    private String imageUrl;
    private String linkUrl;
    private boolean forceDisplay;
    private Activity activity;

    public PromoChecker(final Activity activity,Context ctx) {
        super(GGConstants.Api.URL_READ_PROMO,null,METHOD_POST,ctx);

        this.params = new HashMap<>();
        this.params.put("jour", Integer.toString(19) );
        this.params.put("mois", Integer.toString(11) );

        this.activity = activity;
        this.forceDisplay = false;

        gameName = "Not Yet";

        execute();
    }

    public PromoChecker(final Activity activity,Context ctx, boolean forceDisplay) {
        super(GGConstants.Api.URL_READ_PROMO,null,METHOD_POST,ctx);
        this.activity = activity;
        this.forceDisplay = forceDisplay;

        gameName = "Nope";

        execute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if ( !this.wasOk ) {
            gameName = "Not Ok";
            showDialog();
            return;
        }

        try {
            gameName = object.getString("gameName");
            imageUrl = object.getString("image");
            linkUrl  = object.getString("link");
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        if ( gameName.length() > 0 && gameName.compareTo("none") != 0 ) {
            showDialog();
        }
    }

    /*
     * Nouvelle version disponible
     */
    private void showDialog() {
        PromoDialog promo = new PromoDialog(this.activity);
        promo.setGameName(gameName);
        promo.setImageUrl(imageUrl);
        promo.setLinkUrl(linkUrl);

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
