package com.ggpi.laguilde.widgets;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.ggpi.laguilde.models.GGEventModel;
import com.ggpi.laguilde.models.GGGlobals;
import com.ggpi.laguilde.models.GGPreferences;
import com.ggpi.laguilde.tools.ApiCall;
import com.ggpi.laguilde.tools.GGConstants;
import com.ggpi.laguilde.tools.Notificator;
import com.tooltip.Tooltip;

import java.util.HashMap;

public class RegisterSwitch extends SwitchCompat implements CompoundButton.OnCheckedChangeListener {

    private GGEventModel event;
    // private String eventId;

    public RegisterSwitch(Context context) {
        super(context);
    }

    public RegisterSwitch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setOnCheckedChangeListener(this);
    }


    public String getEventId() {
        return event.getId();
    }

    public void setEvent( GGEventModel event ) {
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

    private void toggleRegistration() {
        HashMap<String, String> params = new HashMap<>();
        params.put("uuid", GGGlobals.getUUID() );
        params.put("id_event", this.event.getId() );
        if ( GGPreferences.pseudo() != null && GGPreferences.pseudo().length() > 1 )
            params.put("pseudo", GGPreferences.pseudo() );
        else
            params.put("pseudo", "inconnu" );

        MyCall request = new MyCall(
                isChecked() ? GGConstants.Api.URL_ADD_REG : GGConstants.Api.URL_DEL_REG,
                params, ApiCall.METHOD_POST);
        request.execute();
    }


    public void setCheckedExt(boolean checked) {
        setOnCheckedChangeListener(null);
        setChecked(checked);
        setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        toggleRegistration();
    }


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
        }
    }
}
