package com.ggpi.laguilde.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ggpi.laguilde.models.GGEventModel;
import com.example.ggpi.laguilde.R;
import com.ggpi.laguilde.tools.AndyUtils;

public class GGResultAdapter extends GGEventBaseAdapter {

    public GGResultAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        GGEventModel ggEvent = ggEvents.get(position);
        int layout;

        layout = R.layout.results_lv_item;

        if (convertView == null) {
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(layout, null, true);

            holder.tvDate = (TextView) convertView.findViewById(R.id.date);
            holder.tvFormat = (TextView) convertView.findViewById(R.id.format);
            holder.tvWinner = (TextView) convertView.findViewById(R.id.winner);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }


        /* Display Format if needed */
        if ( position == 0 ||
                ggEvent.getFormatName().compareToIgnoreCase(ggEvents.get(position-1).getFormatName()) > 0 ) {
            holder.tvFormat.setText(ggEvent.getFormatName());
        }
        else {
            holder.tvFormat.setVisibility(View.GONE);
        }


        holder.tvDate.setText(AndyUtils.capitalize(ggEvent.getDateText()));

        holder.tvWinner.setText(ggEvent.getWinnerName());

        return convertView;
    }


    private class ViewHolder {
        protected TextView tvDate;
        protected TextView tvFormat;
        protected TextView tvWinner;
    }

}