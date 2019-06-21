package com.ggpi.laguilde.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.example.ggpi.laguilde.R;
import com.ggpi.laguilde.models.GGGlobals;
import com.ggpi.laguilde.widgets.MySwitch;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class VersionChecker extends ApiCall {

    private int versionNumber;
    private String versionName;

    public VersionChecker(Context ctx) {
        super(GGConstants.Api.URL_READ_VERSION,null,METHOD_GET,ctx);

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
            //Toast.makeText(context,"> 0 : " + versionName + " vs " + GGGlobals.getVersionName()  , Toast.LENGTH_LONG).show();
            showDialog(versionName);
        }
        /*
        else {
            Toast.makeText(context,"<= 0 : " + versionName + " vs " + GGGlobals.getVersionName()  , Toast.LENGTH_LONG).show();
        }
        */
    }

    private void showDialog(String newVersion) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle( context.getString(R.string.title_new_version))
                .setMessage( context.getString(R.string.text_new_version) + " " + newVersion )
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(
                        context.getString(R.string.download),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://laguilde-jeux.fr/appdownload")));
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
}
