package com.ggpi.laguilde.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import com.example.ggpi.laguilde.R;
import com.ggpi.laguilde.models.GGGlobals;

import org.json.JSONException;

public class VersionChecker extends ApiCall {

    // Todo: convert to playstore checker

    private String versionName;
    private boolean forceDisplay;

    public VersionChecker(Context ctx) {
        super(GGConstants.Api.URL_READ_VERSION,null,METHOD_GET,ctx);
        this.forceDisplay = false;

        execute();
    }

    public VersionChecker(Context ctx,boolean forceDisplay) {
        super(GGConstants.Api.URL_READ_VERSION,null,METHOD_GET,ctx);
        this.forceDisplay = forceDisplay;

        execute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if ( !this.wasOk ) {
           return;
        }

        try {
            versionName = object.getString("version");
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        if ( versionName.compareTo(GGGlobals.getVersionName()) > 0 ) {
            showDialog(versionName);
        }
        else {
            if ( forceDisplay ) {
                showNoNew();
            }
        }
    }

    /*
     * Nouvelle version disponible
     */
    private void showDialog(String newVersion) {

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
    }

    /*
     * Pas de nouvelle version disponible
     */
    private void showNoNew() {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle( context.getString(R.string.title_new_version))
                .setMessage( context.getString(R.string.text_no_new_version) )
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setNegativeButton(
                        context.getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .show();
    }
}
