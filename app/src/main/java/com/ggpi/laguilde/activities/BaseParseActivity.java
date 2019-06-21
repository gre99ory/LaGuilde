package com.ggpi.laguilde.activities;

import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.ggpi.laguilde.models.GGEventModel;
import com.ggpi.laguilde.adapters.GGEventBaseAdapter;
import com.ggpi.laguilde.tools.ParseGGEventContent;
import com.ggpi.laguilde.tools.GGConstants;
import com.ggpi.laguilde.tools.AndyUtils;
import com.ggpi.laguilde.tools.HttpRequest;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/*
Base activity requiring parsing an events file
 */
public abstract class BaseParseActivity extends GuildeMenuBaseActivity {

    protected ParseGGEventContent parseContent;
    protected ListView listView;
    protected final int jsoncode = 1;
    protected ArrayList<GGEventModel> ggEvents;
    protected Comparator<GGEventModel> ggEventSorter;
    protected GGEventBaseAdapter ggEventAdapter;

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void doParse(String fileName) {
        try {
            parseJson(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void parseJson(final String fileName) throws IOException, JSONException {

        if (!AndyUtils.isNetworkAvailable(BaseParseActivity.this)) {
            Toast.makeText(BaseParseActivity.this, "Internet is required!", Toast.LENGTH_SHORT).show();
            return;
        }
        AndyUtils.showSimpleProgressDialog(BaseParseActivity.this);

        new AsyncTask<Void, Void, String>() {
            protected String doInBackground(Void[] params) {
                String response = "";
                HashMap<String, String> map = new HashMap<>();
                try {
                    HttpRequest req = new HttpRequest(GGConstants.ServiceType.SRVURL + fileName );
                    response = req.prepare(HttpRequest.Method.POST).withData(map).sendAndReadString();
                } catch (Exception e) {
                    response = e.getMessage();
                }
                return response;
            }

            protected void onPostExecute(String result) {
                //do something with response
                onTaskCompleted(result, jsoncode);
            }
        }.execute();
    }

    protected void onTaskCompleted(String response, int serviceCode) {

        AndyUtils.removeSimpleProgressDialog();  //will remove progress dialog

        // Get the list of ggEvent Objects
        ggEvents = parseContent.getEventList(response);

        Collections.sort(ggEvents,ggEventSorter);

        ggEventAdapter.setEventList(ggEvents);
        if (ggEventAdapter.getCount() > 0) {
            listView.setAdapter(ggEventAdapter);
        } else {
            Toast.makeText(this, "No Data Found !", Toast.LENGTH_LONG).show();
        }
    }

}
