package com.ggpi.laguilde.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ggpi.laguilde.models.GGEventModel;
import com.example.ggpi.laguilde.R;
import com.ggpi.laguilde.tools.AndyUtils;

import java.util.ArrayList;

public abstract class GGEventBaseAdapter extends BaseAdapter {

    protected Context context;
    protected ArrayList<GGEventModel> ggEvents;


    public GGEventBaseAdapter() {
        this.context = null;
        this.ggEvents = null;
    }

    public GGEventBaseAdapter(Context context) {
        this.context = context;
        this.ggEvents = null;
    }

    public void setContext(Context context) { this.context = context; }

    public void setEventList(ArrayList<GGEventModel> eventList ) { this.ggEvents = eventList; }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return ggEvents.size();
    }

    @Override
    public Object getItem(int position) {
        return ggEvents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}