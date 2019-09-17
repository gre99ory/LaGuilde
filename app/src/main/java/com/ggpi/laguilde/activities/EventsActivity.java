package com.ggpi.laguilde.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.ggpi.laguilde.R;
import com.ggpi.laguilde.adapters.GGEventAdapter;
import com.ggpi.laguilde.models.GGEventModel;
import com.ggpi.laguilde.models.GGGlobals;

import java.util.Comparator;


public class EventsActivity extends BaseParseActivity {

    //todo: ne pas afficher les events termines =WINNER= -sauf si qq chose debug ??

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ggEventSorter = new Comparator<GGEventModel>() {
            @Override
            public int compare(GGEventModel ggEventA, GGEventModel ggEventB) {
                return ggEventA.getStart().compareToIgnoreCase(ggEventB.getStart());
            }};

        if (GGGlobals.getDebug()) {
            ggEventFilter = new Comparator<GGEventModel>() {
                @Override
                public int compare(GGEventModel ggEventA, GGEventModel unused) {
                    return 1;
                }
            };
        }
        else {
            ggEventFilter = new Comparator<GGEventModel>() {
                @Override
                public int compare(GGEventModel ggEventA, GGEventModel unused) {
                    // Evenement sans vainqueur => ne s'est pas encore deroule
                    if (ggEventA.getWinnerName().length() <= 0)
                        return 1;
                    return 0;
                }
            };
        }

        ggEventAdapter = new GGEventAdapter(this.getBaseContext());
    }
}
