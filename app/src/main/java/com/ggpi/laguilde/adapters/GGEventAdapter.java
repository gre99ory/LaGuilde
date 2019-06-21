package com.ggpi.laguilde.adapters;

import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ggpi.laguilde.models.GGPreferences;
import com.ggpi.laguilde.widgets.MySwitch;
import com.ggpi.laguilde.models.GGEventModel;
import com.example.ggpi.laguilde.R;
import com.ggpi.laguilde.tools.AndyUtils;

import static com.ggpi.laguilde.tools.GGConstants.Paths.URL_EVENT_IMG;

public class GGEventAdapter extends GGEventBaseAdapter {

    public GGEventAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        GGEventModel ggEvent = ggEvents.get(position);
        int layout;
        String strHead;

        // layout = ( position % 2 == 1 ) ? R.layout.events_lv_item : R.layout.lv_item_2;
        layout = R.layout.events_lv_item;

        if (convertView == null) {
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null, true);

            holder.tvMonth = (TextView) convertView.findViewById(R.id.month);
            holder.tvDate = (TextView) convertView.findViewById(R.id.date);
            holder.tvHead = (TextView) convertView.findViewById(R.id.head);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.title);
            holder.tvDescription = (TextView) convertView.findViewById(R.id.description);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.price);
            holder.ivImage = (ImageView) convertView.findViewById(R.id.imageView);
            holder.cbRegister = (MySwitch)convertView.findViewById(R.id.cbRegister);

            if ( position == 0 && GGPreferences.showTooltips() ) {
                holder.cbRegister.showToolTip(context.getString(R.string.click_to_register), Gravity.BOTTOM);
            }

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        /* Display Month if needed */
        if ( position == 0 || ggEvent.getMonth() > ggEvents.get(position-1).getMonth()) {
            holder.tvMonth.setText(AndyUtils.capitalize(ggEvent.getMonthText()));
        }
        else {
            holder.tvMonth.setVisibility(View.GONE);
        }

        strHead = new String(ggEvent.getGameName());

        if ( ggEvent.getTypeName() != "" && !ggEvent.getTitle().contains(ggEvent.getTypeName())) {
            strHead = new String(ggEvent.getTypeName() + " - "+strHead );
        }
        if ( ggEvent.getFormatName() != "" && !ggEvent.getTitle().contains(ggEvent.getFormatName())) {
            strHead = new String( strHead + " - "+ggEvent.getFormatName());
        }

        holder.tvDate.setText(AndyUtils.capitalize(ggEvent.getStartText()));
        holder.tvHead.setText(strHead);
        holder.tvTitle.setText(ggEvent.getTitle());

        holder.tvPrice.setText(ggEvent.getPriceText());

        if ( ggEvent.getDescription().isEmpty() ) {
            holder.tvDescription.setVisibility(View.GONE);
        }
        else {
            holder.tvDescription.setText(ggEvent.getDescription());
            // holder.tvDescription.setTextColor(Color.parseColor(ggEvents.get(position).getTextColor()));
        }
        //holder.tvImage.setText(ggEvent.getImage());

        //if ( position > 2 ) holder.ivImage.setImageURI(Uri.parse("http://laguilde-jeux.fr/modules/gg_events/img/"+ggEvent.getImage()));


        // holder.cbRegister.setEventId(ggEvent.getId());
        // holder.cbRegister.setChecked( ( position % 2 == 1 ));
        holder.cbRegister.setEvent( ggEvent );
        holder.cbRegister.setChecked( GGPreferences.isRegistered( ggEvent.getId()) );


        // http://bumptech.github.io/glide/
        Glide.with(convertView).load(
                URL_EVENT_IMG+ggEvent.getImage()
        ).into(holder.ivImage);
        return convertView;
    }


    private class ViewHolder {
        // protected LinearLayout ll;
        protected TextView tvMonth;
        protected TextView tvDate;
        protected TextView tvHead;
        protected TextView tvTitle;
        protected TextView tvDescription;
        protected TextView tvPrice;
        //protected TextView tvImage;
        protected ImageView ivImage;
        protected MySwitch cbRegister;
    }

}