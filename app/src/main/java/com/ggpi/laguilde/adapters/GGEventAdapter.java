package com.ggpi.laguilde.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ggpi.laguilde.models.GGPreferences;
import com.ggpi.laguilde.widgets.RegisterSwitch;
import com.ggpi.laguilde.models.GGEventModel;
import com.example.ggpi.laguilde.R;
import com.ggpi.laguilde.tools.AndyUtils;

import static com.ggpi.laguilde.tools.GGConstants.Paths.URL_EVENT_IMG;

public class GGEventAdapter extends GGEventBaseAdapter<GGEventAdapter.EventViewHolder> {

    public GGEventAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context ctx = viewGroup.getContext();

        LayoutInflater inflater = LayoutInflater.from(ctx);

        View view = inflater.inflate(R.layout.events_lv_item, viewGroup, false);

        return new GGEventAdapter.EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder eventViewHolder, int position) {

        GGEventModel ggEvent = ggEvents.get(position);

        /* Display/Hide Month if needed */
        if ( position > 0 && ggEvent.getMonth() == ggEvents.get(position-1).getMonth()) {
            eventViewHolder.tvMonth.setVisibility(View.GONE);
        }

        eventViewHolder.display(ggEvent);
    }


    class EventViewHolder extends RecyclerView.ViewHolder {

        // protected LinearLayout ll;
        protected TextView tvMonth;
        protected TextView tvDate;
        protected TextView tvHead;
        protected TextView tvTitle;
        protected TextView tvDescription;
        protected TextView tvPrice;

        //protected TextView tvImage;
        protected ImageView ivImage;
        protected RegisterSwitch cbRegister;


        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMonth = (TextView) itemView.findViewById(R.id.month);
            tvDate = (TextView) itemView.findViewById(R.id.date);
            tvHead = (TextView) itemView.findViewById(R.id.head);
            tvTitle = (TextView) itemView.findViewById(R.id.title);
            tvDescription = (TextView) itemView.findViewById(R.id.description);
            tvPrice = (TextView) itemView.findViewById(R.id.price);
            ivImage = (ImageView) itemView.findViewById(R.id.imageView);
            cbRegister = (RegisterSwitch)itemView.findViewById(R.id.cbRegister);
        }


        void display(GGEventModel ggEvent) {


            tvMonth.setText(AndyUtils.capitalize(ggEvent.getMonthText()));

            String strHead = new String(ggEvent.getGameName());

            if ( ggEvent.getTypeName().compareTo("-") != 0 &&
                    !ggEvent.getTitle().contains(ggEvent.getTypeName())) {
                strHead = new String(ggEvent.getTypeName() + " - "+strHead );
            }
            if ( ggEvent.getFormatName().compareTo("-") != 0 &&
                    !ggEvent.getTitle().contains(ggEvent.getFormatName())) {
                strHead = new String( strHead + " - "+ggEvent.getFormatName());
            }

            tvDate.setText(AndyUtils.capitalize(ggEvent.getStartText()));
            tvHead.setText(strHead);
            tvTitle.setText(ggEvent.getTitle());

            tvPrice.setText(ggEvent.getPriceText());

            if ( ggEvent.getDescription().isEmpty() ) {
                tvDescription.setVisibility(View.GONE);
            }
            else {
                tvDescription.setText(ggEvent.getDescription());

            }

            cbRegister.setEvent( ggEvent );
            cbRegister.setChecked( GGPreferences.isRegistered( ggEvent.getId()) );


            // http://bumptech.github.io/glide/
            Glide.with(itemView).load(
                    URL_EVENT_IMG+ggEvent.getImage()
            ).into(ivImage);

        }
    }
}