package com.ggpi.laguilde.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ggpi.laguilde.R;
import com.ggpi.laguilde.models.GGGlobals;
import com.ggpi.laguilde.models.GGPreferences;
import com.ggpi.laguilde.tools.ApiCall;
import com.ggpi.laguilde.tools.GGConstants;
import com.ggpi.laguilde.tools.ParseGGEventContent;

public class MagicActivity extends GuildeMenuBaseActivity {

    TextView tvDCI;
    TextView tvLifePoints;
    TextView tvSeasonPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.magic_activity);

        tvDCI = findViewById(R.id.tvDCI);
        tvLifePoints = findViewById(R.id.tvLifePoints);
        tvSeasonPoints = findViewById(R.id.tvSeasonPoints);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvDCI.setText(GGPreferences.DCI());






        new MagicLoader(this);



    }


    private class MagicLoader extends ApiCall {

        public MagicLoader(Context ctx) {

            super(GGConstants.Api.URL_MAGIC_GET_POINTS+GGPreferences.DCI(),null,METHOD_POST,ctx);

            execute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

            if ( !this.wasOk ) {
                return;
            }

            // GGGlobals.setEvents( ParseGGEventContent.getEventList(s) );

        }

    }
}
