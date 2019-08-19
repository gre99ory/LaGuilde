package com.ggpi.laguilde.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ggpi.laguilde.R;
import com.ggpi.laguilde.fragments.IntroFragment;

public class IntroAdapter extends FragmentPagerAdapter {
    public IntroAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch(i) {
            case 0:
                return IntroFragment.newInstance(0,IntroFragment.FRAGMENT_START);
            case 1:
                return IntroFragment.newInstance(1,IntroFragment.FRAGMENT_MIDDLE);
            case 2:
                return IntroFragment.newInstance(2,IntroFragment.FRAGMENT_MIDDLE);
            case 3:
                return IntroFragment.newInstance(3,IntroFragment.FRAGMENT_MIDDLE);
            case 4:
                return IntroFragment.newInstance(4,IntroFragment.FRAGMENT_END);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
