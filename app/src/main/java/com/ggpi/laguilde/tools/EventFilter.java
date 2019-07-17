package com.ggpi.laguilde.tools;

import com.ggpi.laguilde.models.GGEventModel;

import java.util.ArrayList;
import java.util.Comparator;

public class EventFilter {

    //Todo : se baser sur comparator mais avec un seul argument

    static public ArrayList<GGEventModel> filter(ArrayList<GGEventModel> ggEvents,  Comparator<GGEventModel> ggEventFilter ) {

        ArrayList<GGEventModel> retEvents = new ArrayList<>();

        for ( GGEventModel ggEvent : ggEvents ) {
            if ( ggEventFilter.compare(ggEvent,null) > 0 ) {
                    retEvents.add(ggEvent);
            }
        }

        return retEvents;
    }
}
