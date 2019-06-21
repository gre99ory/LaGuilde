package com.ggpi.laguilde.dialogs;

import android.app.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ggpi.laguilde.R;

public class AboutDialog extends Dialog {

    private String title;
    private String description;
    private int icon;

    private ImageView ivIcon;
    private TextView tvTitle;
    private TextView tvDescription;
    private Button btnOk;
    private ImageView ivGGPI;

    public AboutDialog(final Activity activity) {
        super(activity); //, R.style.Theme_AppCompat_Dialog);// Theme_AppCompat_DayNight_Dialog);

        setContentView(R.layout.about);

        title = "Default Title";
        description = "Default Description";

        ivIcon = (ImageView)findViewById(R.id.ivIcon);
        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvDescription = (TextView)findViewById(R.id.tvDescription);
        ivGGPI = (ImageView)findViewById(R.id.ggpi_logo);
        btnOk = (Button)findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ivGGPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openURL = new Intent(android.content.Intent.ACTION_VIEW);
                openURL.setData(Uri.parse("http://ggpi.fr/"));
                activity.startActivity(openURL);
            }
        });
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIcon(int resId) { this.icon = resId; }


    public void build() {
        tvTitle.setText(title);
        tvDescription.setText(description);
        ivIcon.setImageResource(icon);
        show();
    }


}
