package com.ggpi.laguilde.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ggpi.laguilde.R;

import static com.ggpi.laguilde.tools.GGConstants.Paths.URL_EVENT_IMG;

public class PromoDialog extends Dialog implements View.OnClickListener {

    private String gameName;
    private String imageUrl;
    private String linkUrl;
    private Activity activity;

    private Button btnOk;
    private TextView tvGameName;
    private ImageView ivGameImage;

    public PromoDialog(final Activity activity) {
        super(activity);

        this.activity = activity;

        setContentView(R.layout.promo);

        int width = (int)(activity.getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(activity.getResources().getDisplayMetrics().heightPixels*0.90);
        getWindow().setLayout(width, height);

        ivGameImage = (ImageView)findViewById(R.id.ivGameImage);
        tvGameName = (TextView)findViewById(R.id.tvGameName);


        height = (int)(height*0.30);
        ivGameImage.setMinimumHeight(height);
        ivGameImage.setMaxHeight(height);// getWindow().setLayout(width, height)


        //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, 100);
        //ivGameImage.setLayoutParams(layoutParams);

        btnOk = (Button)findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ivGameImage.setOnClickListener(this);
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }


    public void build() {

        tvGameName.setText(gameName);
        Glide.with(activity).load(imageUrl).into(ivGameImage);
        show();
    }

    /*
     * Click sur l'image envoie sur la page du produit
     */
    @Override
    public void onClick(View view) {
        if ( view == ivGameImage )
        {
            Intent openURL = new Intent(Intent.ACTION_VIEW);
            openURL.setData(Uri.parse(linkUrl));
            activity.startActivity(openURL);
        }
    }
}
