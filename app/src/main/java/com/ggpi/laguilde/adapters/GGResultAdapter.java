package com.ggpi.laguilde.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ggpi.laguilde.models.GGEventModel;
import com.example.ggpi.laguilde.R;
import com.ggpi.laguilde.tools.AndyUtils;

public class GGResultAdapter extends GGEventBaseAdapter<GGResultAdapter.ResultViewHolder> {

    public GGResultAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context ctx = viewGroup.getContext();

        LayoutInflater inflater = LayoutInflater.from(ctx);

        View view = inflater.inflate(R.layout.results_lv_item, viewGroup, false);

        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder resultViewHolder, int position) {
        GGEventModel ggEvent = ggEvents.get(position);

        /* Display/Hide Format if needed */
        if ( position > 0 && ggEvent.getFormatName().compareTo(ggEvents.get(position-1).getFormatName()) == 0 ) {
            resultViewHolder.tvFormat.setVisibility(View.GONE);
        }
        resultViewHolder.display(ggEvent);
    }


    class ResultViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvDate;
        protected TextView tvFormat;
        protected TextView tvWinner;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = (TextView) itemView.findViewById(R.id.date);
            tvFormat = (TextView) itemView.findViewById(R.id.format);
            tvWinner = (TextView) itemView.findViewById(R.id.winner);
        }


        void display(GGEventModel ggEvent) {
            /* Display Format if needed - Rupture */
            /*
            if (position == 0 ||
                    ggEvent.getFormatName().compareToIgnoreCase(ggEvents.get(position - 1).getFormatName()) > 0) {

            } else {
                holder.tvFormat.setVisibility(View.GONE);
            }
            */

            // Si pas de format, on a un '-' a la place
            // Dans ce cas, on affiche le nom du jeu
            if (ggEvent.getFormatName().length()> 1 ) {
                tvFormat.setText(ggEvent.getFormatName());
            }
            else {
                tvFormat.setText(ggEvent.getGameName());
            }

            tvDate.setText(AndyUtils.capitalize(ggEvent.getDateText()));
            tvWinner.setText(ggEvent.getWinnerName());
        }
    }

}