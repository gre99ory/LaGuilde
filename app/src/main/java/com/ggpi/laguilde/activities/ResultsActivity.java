package com.ggpi.laguilde.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ggpi.laguilde.models.GGEventModel;
import com.ggpi.laguilde.adapters.GGResultAdapter;
import com.ggpi.laguilde.tools.ParseGGEventContent;
import com.example.ggpi.laguilde.R;

import java.util.Comparator;

public class ResultsActivity extends BaseParseActivity implements AdapterView.OnItemSelectedListener {

    private GGResultAdapter ggResultAdapter;
    private Spinner cbo;

    //Todo: grouper les resultats
    //todo: ajouter un/des filtres et/ou onglets

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        cbo = findViewById(R.id.cbo);
        cbo.setOnItemSelectedListener(this);
        cbo.setVisibility(View.GONE);

        parseContent = new ParseGGEventContent(this);
        listView = (ListView) findViewById(R.id.lv);

        ggEventSorter = new Comparator<GGEventModel>() {
            @Override
            public int compare(GGEventModel ggEventA, GGEventModel ggEventB) {
                int a = ggEventA.getFormatName().compareToIgnoreCase(ggEventB.getFormatName());
                if (a == 0)
                    return ggEventB.getStart().compareToIgnoreCase(ggEventA.getStart());
                return a;
            }
        };

        ggEventAdapter = new GGResultAdapter(this.getBaseContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        doParse("winners.json");
    }

    /*
    @Override
    protected void onTaskCompleted(String response, int serviceCode) {
        super.onTaskCompleted(response,serviceCode);

        Set<String> formatSet = new HashSet<>();
        ArrayList<String> formats = new ArrayList<>();

        for( GGEventModel ggEvent : ggEvents ) {
            formatSet.add(ggEvent.getFormatName());
        }
        formats.addAll(formatSet);
        Collections.sort(formats, new Comparator<String>() {
                         @Override
                         public int compare(String sA, String sB) {
                             return sA.compareToIgnoreCase(sB);
                         }
                     });
        formats.add(0,getResources().getString(R.string.all));

        ArrayAdapter < String > adapter =
                        new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinner_item, formats);
        adapter.setDropDownViewResource( R.layout.simple_spinner_dropdown_item );

        cbo.setAdapter(adapter);
    }
    */

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        String toast = String.valueOf(cbo.getItemAtPosition(pos));
        Toast.makeText(this, toast, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Another interface callback
    }

}
