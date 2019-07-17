package com.ggpi.laguilde.tools;

import android.app.Activity;

import com.ggpi.laguilde.models.GGEventModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ParseGGEventContent {

    public static ArrayList<GGEventModel> getEventList(String response) {
        ArrayList<GGEventModel> ggEvents = new ArrayList<>();
        try {
            JSONObject dataObject = new JSONObject( response );
            JSONArray dataArray = dataObject.getJSONArray( "events"); //new JSONArray(response);

            for (int i = 0; i < dataArray.length(); i++) {
                GGEventModel ggEvent = new GGEventModel();
                JSONObject dataObj = dataArray.getJSONObject(i);
                ggEvent.setId(dataObj.getString(GGEventModel.Params.ID));
                ggEvent.setStart(dataObj.getString(GGEventModel.Params.START));
                ggEvent.setBackgroundColor(dataObj.getString(GGEventModel.Params.BACKGROUND));
                ggEvent.setBorderColor(dataObj.getString(GGEventModel.Params.BORDER));
                ggEvent.setTextColor(dataObj.getString(GGEventModel.Params.TEXT));
                ggEvent.setTitle(dataObj.getString(GGEventModel.Params.TITLE));
                ggEvent.setDescription(dataObj.getString(GGEventModel.Params.DESCRIPTION));
                ggEvent.setImage(dataObj.getString(GGEventModel.Params.IMAGE));
                ggEvent.setGameName(dataObj.getString(GGEventModel.Params.GAME));
                ggEvent.setTypeName(dataObj.getString(GGEventModel.Params.TYPE));
                ggEvent.setEventName(dataObj.getString(GGEventModel.Params.EVENT));
                ggEvent.setFormatName(dataObj.getString(GGEventModel.Params.FORMAT));
                ggEvent.setPrice(dataObj.getString(GGEventModel.Params.PRICE));
                ggEvent.setWinnerName(dataObj.getString(GGEventModel.Params.WINNER));
                ggEvents.add(ggEvent);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ggEvents;
    }

}