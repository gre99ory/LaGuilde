package com.ggpi.laguilde.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.ggpi.laguilde.adapters.GGEventAdapter;
import com.ggpi.laguilde.models.GGEventModel;
import com.ggpi.laguilde.tools.ParseGGEventContent;
import com.example.ggpi.laguilde.R;

import java.util.Comparator;


public class EventsActivity extends BaseParseActivity {

    //todo: ne pas afficher les events termines -sauf si qq chose debug ??

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        parseContent = new ParseGGEventContent(this);
        listView = (ListView) findViewById(R.id.lv);

        ggEventSorter = new Comparator<GGEventModel>() {
            @Override
            public int compare(GGEventModel ggEventA, GGEventModel ggEventB) {
                return ggEventA.getStart().compareToIgnoreCase(ggEventB.getStart());
            }};

        ggEventAdapter = new GGEventAdapter(this.getBaseContext()); //, ggEvents);
    }

    @Override
    protected void onResume() {
        super.onResume();

        doParse("next.json");
    }


}
