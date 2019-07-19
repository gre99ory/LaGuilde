package com.ggpi.laguilde.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ggpi.laguilde.R;
import com.ggpi.laguilde.models.GGPreferences;


public class IntroFragment extends Fragment {

    public static int FRAGMENT_START = 0;
    public static int FRAGMENT_MIDDLE = 1;
    public static int FRAGMENT_END = 2;

    /*
    private int[] layout = {
            R.layout.fragment_start,
            R.layout.fragment_middle,
            R.layout.fragment_end
    };
    */
    private final int layout = R.layout.fragment_intro;
    private int index;
    private int position;

    // index texte surcharge
    private int sIndex;

    private ImageView topImage;
    private ImageView image;
    private TextView introText;

    private TextView next;
    private TextView back;
    private TextView done;
    private ViewPager viewPager;

    public IntroFragment() {
        // Required empty public constructor
    }


    /**
     * Create a new instance of DetailsFragment, initialized to
     * show the text at 'index'.
     */
    public static IntroFragment newInstance(int index, int position) {
        IntroFragment f = new IntroFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        args.putInt("position", position);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = getArguments().getInt("index", 0);
        sIndex = index;
        position = getArguments().getInt("position", 0);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(layout, container, false);

        viewPager = getActivity().findViewById(R.id.viewPager);

        buttonSetup(view);
        imageSetup(view);

        return view;
    }

    public void buttonSetup(View view) {

        next = view.findViewById(R.id.slideNext);
        back = view.findViewById(R.id.slideBack);
        done = view.findViewById(R.id.slideDone);

        if (position == FRAGMENT_START) {
            back.setVisibility(View.GONE);
            done.setVisibility(View.GONE);
        }
        if (position == FRAGMENT_MIDDLE) {
            done.setVisibility(View.GONE);
        }
        if (position == FRAGMENT_END) {
            next.setVisibility(View.GONE);
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(index + 1);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(index - 1);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GGPreferences.setOnBoardingDone(true);
                GGPreferences.save();
                getActivity().finish();
            }
        });
    }

    public void imageSetup(View view) {
        int resId;

        image = view.findViewById(R.id.imageView);
        topImage = view.findViewById(R.id.imageViewTop);
        introText = view.findViewById(R.id.textView);


        resId = getResources().getIdentifier("capture"+index,
                "drawable", getActivity().getPackageName());
        image.setImageResource(resId);

        resId = getResources().getIdentifier("capture"+index+"a",
                "drawable", getActivity().getPackageName());
        topImage.setImageResource(resId);

        resId = getResources().getIdentifier("intro"+sIndex,
                "string", getActivity().getPackageName());
        introText.setText(resId);
    }

    public void setSIndex(int sIndex) { this.sIndex = sIndex; }

}

