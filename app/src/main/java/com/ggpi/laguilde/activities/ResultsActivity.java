package com.ggpi.laguilde.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.ggpi.laguilde.R;
import com.ggpi.laguilde.adapters.GGResultAdapter;
import com.ggpi.laguilde.models.GGEventModel;

import java.util.Calendar;
import java.util.Comparator;

public class ResultsActivity extends BaseParseActivity implements AdapterView.OnItemSelectedListener {

    private Spinner cbo;

    //Todo: grouper les resultats
    //todo: ajouter un/des filtres et/ou onglets

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Date minDate = now - 365;
        final Calendar minCalendar = Calendar.getInstance();
        minCalendar.add(Calendar.YEAR, -1);
        // minCalendar.add(Calendar.MONTH, -3);

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

        ggEventFilter = new Comparator<GGEventModel>() {
            @Override
            public int compare(GGEventModel ggEventA, GGEventModel unused) {
                if ( minCalendar.before(ggEventA.getCalendar()) &&
                    (ggEventA.getWinnerName().length() > 0 ))
                    return 1;
                return 0;
            }
        };
    }

    // @Override
    /* Liste des Formats dans un Spinner */
    /*
    protected void oonResume() {
        super.onResume();

        // Le passage par un set dedoublonne
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

        ArrayAdapter< String > adapter =
                new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, formats);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

        cbo.setAdapter(adapter);
    }
    */

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        String toast = String.valueOf(cbo.getItemAtPosition(pos));
        // Toast.makeText(this, toast, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Another interface callback
    }

}
