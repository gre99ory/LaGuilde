package com.ggpi.laguilde.activities;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.ggpi.laguilde.R;
import com.ggpi.laguilde.adapters.IntroAdapter;

public class IntroActivity extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity);

        viewPager = (ViewPager)findViewById(R.id.viewPager);

        IntroAdapter adapter = new IntroAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }
}
