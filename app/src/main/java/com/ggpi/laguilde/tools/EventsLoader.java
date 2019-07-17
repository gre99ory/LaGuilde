package com.ggpi.laguilde.tools;

import android.content.Context;

import com.ggpi.laguilde.models.GGGlobals;

public class EventsLoader extends ApiCall {

    public EventsLoader(Context ctx) {
        super(GGConstants.Api.URL_GET_EVENTS,null,METHOD_GET,ctx);

        execute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if ( !this.wasOk ) {
            return;
        }

        GGGlobals.setEvents( ParseGGEventContent.getEventList(s) );
    }

}
