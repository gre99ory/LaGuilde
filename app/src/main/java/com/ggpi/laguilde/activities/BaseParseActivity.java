package com.ggpi.laguilde.activities;

import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ggpi.laguilde.R;
import com.ggpi.laguilde.models.GGEventModel;
import com.ggpi.laguilde.adapters.GGEventBaseAdapter;
import com.ggpi.laguilde.models.GGGlobals;
import com.ggpi.laguilde.models.GGPreferences;
import com.ggpi.laguilde.tools.EventFilter;
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

    /*
     *
     */
    private RecyclerView recyclerView;
    private ArrayList<GGEventModel> ggFullEvents;
    private boolean initOk = false;

    protected ParseGGEventContent parseContent;

    protected final int jsoncode = 1;

    protected ArrayList<GGEventModel> ggEvents;
    protected Comparator<GGEventModel> ggEventSorter;
    protected Comparator<GGEventModel> ggEventFilter;

    protected GGEventBaseAdapter ggEventAdapter;


    protected ArrayList<GGEventModel> getFullEventList() {
        return ggFullEvents;
    }

    /*
     * Initialisation des donnees a afficher, filtre, tri
     */
    private void initializeDisplay() {
        recyclerView = (RecyclerView)findViewById(R.id.rv);

        // Get the list of ggEvent Objects
        ggFullEvents = GGGlobals.getEvents();

        if ( ggFullEvents == null ) {
            Toast.makeText(this,R.string.err_no_data,Toast.LENGTH_LONG).show();
            return;
        }

        // Filtre principal de l'activite
        ggFullEvents = EventFilter.filter(ggFullEvents,ggEventFilter);

        Collections.sort(ggFullEvents,ggEventSorter);

        initOk = true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if ( !initOk ) {
            initializeDisplay();
            if ( !initOk ) return;
        }

        ggEvents = ggFullEvents;
        if ( ggFullEvents == null ) {
            Toast.makeText(this,R.string.err_no_data,Toast.LENGTH_LONG).show();
            return;
        }

        // Filtres des preferences
        if ( !GGPreferences.showMagic() ) {
            ggEvents = EventFilter.filter(ggEvents, new Comparator<GGEventModel>() {
                @Override
                public int compare(GGEventModel ggEventModel, GGEventModel t1) {
                    return ( ggEventModel.getGameName().compareTo("Magic") == 0 ? 0 : 1);
                }
            });
        }

        if ( !GGPreferences.showPokemon() ) {
            ggEvents = EventFilter.filter(ggEvents, new Comparator<GGEventModel>() {
                @Override
                public int compare(GGEventModel ggEventModel, GGEventModel t1) {
                    return ( ggEventModel.getGameName().compareTo("Pokemon") == 0 ? 0 : 1);
                }
            });
        }


        ggEventAdapter.setEventList(ggEvents);
        if (ggEventAdapter.getItemCount() > 0) {
            recyclerView.setAdapter(ggEventAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

        } else {
            Toast.makeText(this, R.string.err_no_data, Toast.LENGTH_LONG).show();
        }
    }
}
