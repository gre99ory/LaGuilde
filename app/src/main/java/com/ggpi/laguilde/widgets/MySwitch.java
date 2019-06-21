package com.ggpi.laguilde.widgets;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.ggpi.laguilde.models.GGEventModel;
import com.ggpi.laguilde.models.GGPreferences;
import com.ggpi.laguilde.tools.Notificator;
import com.ggpi.laguilde.models.GGGlobals;
import com.ggpi.laguilde.tools.ApiCall;
import com.ggpi.laguilde.tools.GGConstants;
import com.tooltip.Tooltip;

import java.util.HashMap;

public class MySwitch extends SwitchCompat implements View.OnClickListener {

    private GGEventModel event;
    // private String eventId;

    public MySwitch(Context context) {
        super(context);
    }

    public MySwitch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setOnClickListener( this );
    }


    public String getEventId() {
        return event.getId();
    }

    public void setEvent( GGEventModel event ) {
    // public void setEventId(String eventId) {
        this.event = event;
    }

    public void showToolTip(String text,int gravity) {
        Tooltip tip = new Tooltip.Builder(this)
                    .setText(text)
                    .setTextColor(Color.WHITE)
                    .setGravity(gravity)
                    .setCornerRadius(8f)
                    .setDismissOnClick(true)
                    .show();
    }

    public void onClick(View view) {
        HashMap<String, String> params = new HashMap<>();
        params.put("uuid", GGGlobals.getUUID() );
        params.put("id_event", this.event.getId() );

        MyCall request = new MyCall(
                    isChecked() ? GGConstants.Api.URL_ADD_REG : GGConstants.Api.URL_DEL_REG,
                    params, ApiCall.METHOD_POST);
        request.execute();
    }

    /*
    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        // changeColor(checked);
    }

    private void changeColor(boolean isChecked) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            int thumbColor;
            int trackColor;

            if(isChecked) {
                thumbColor = Color.argb(255, 253, 153, 0);
                trackColor = thumbColor;
            } else {
                thumbColor = Color.argb(255, 236, 236, 236);
                trackColor = Color.argb(255, 0, 0, 0);
            }

            try {
                getThumbDrawable().setColorFilter(thumbColor, PorterDuff.Mode.MULTIPLY);
                getTrackDrawable().setColorFilter(trackColor, PorterDuff.Mode.MULTIPLY);
            }
            catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
*/


    private class MyCall extends ApiCall {

        public MyCall(String url, HashMap<String, String> params, int methodCode) {
            super(url,params,methodCode,getContext());
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if ( !this.wasOk ) {
                toggle();
                return;
            }

            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            GGPreferences.setRegistered( event.getId(), isChecked() );

            if ( isChecked() ) {
                Notificator.scheduleNotificationForEvent( event );
            }

            // Notificator.doTest();
            // Notificator.doNotify();

        }
    }

    // setChecked
    // isChecked
}
