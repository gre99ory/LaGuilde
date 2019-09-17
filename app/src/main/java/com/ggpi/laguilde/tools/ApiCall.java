package com.ggpi.laguilde.tools;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.ggpi.laguilde.R;
import com.ggpi.laguilde.activities.SplashActivity;
import com.ggpi.laguilde.models.GGGlobals;
import com.ggpi.laguilde.models.GGPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ApiCall extends AsyncTask<Void, Void, String> {
    public static final int METHOD_GET  = 1024;
    public static final int METHOD_POST = 1025;

    //the url where we need to send the request
    String url;

    //the parameters
    HashMap<String, String> params;

    //the request code to define whether it is a GET or POST
    int methodCode;

    // the context
    Context context;

    // Return values
    protected boolean wasOk;
    protected JSONObject object;
    protected String message;

    //constructor to initialize values
    public ApiCall(String url, HashMap<String, String> params, int methodCode, Context context) {
        this.url = url;
        this.params = params;
        this.methodCode = methodCode;
        this.context = context;

        if ( !AndyUtils.isNetworkAvailable(context) ) {
            Toast.makeText(context,R.string.err_no_network, Toast.LENGTH_LONG).show();
            cancel(true);
        }

    }

    protected ApiCall() {
        super();
    }

    //when the task started displaying a progressbar
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        AndyUtils.showSimpleProgressDialog(context);
        //AndyUtils.doSleep(1000);
    }


    //this method will give the response from the request
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        AndyUtils.removeSimpleProgressDialog();
        try {
            object = new JSONObject(s);
            if (!object.getBoolean("error")) {
                wasOk = true;
            }
            else {
                wasOk = false;
            }
            message = object.getString("message");
            if ( !wasOk ) Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        AndyUtils.removeSimpleProgressDialog();
    }

    //the network operation will be performed in background
    @Override
    protected String doInBackground(Void... voids) {
        if ( isCancelled() ) {
            return null;
        }

        RequestHandler requestHandler = new RequestHandler();

        if (methodCode == METHOD_POST)
            return requestHandler.sendPostRequest(url, params);

        if (methodCode == METHOD_GET)
            return requestHandler.sendGetRequest(url);

        return null;
    }
}

