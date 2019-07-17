package com.ggpi.laguilde.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ggpi.laguilde.R;


public class IntroFragment extends Fragment {

    /*
    public static int FRAGMENT_START = 0;
    public static int FRAGMENT_MIDDLE = 1;
    public static int FRAGMENT_END = 2;

    private int[] layout = {
            R.layout.fragment_start,
            R.layout.fragment_middle,
            R.layout.fragment_end
    };
    */
    private int layout;
    private int index;

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
    public static IntroFragment newInstance(int index,int layout) {
        IntroFragment f = new IntroFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        args.putInt("layout", layout);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = getArguments().getInt("index", 0);
        layout = getArguments().getInt("layout", 0);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(layout, container, false);

        viewPager = getActivity().findViewById(R.id.viewPager);
        next = view.findViewById(R.id.slideNext);
        back = view.findViewById(R.id.slideBack);
        done = view.findViewById(R.id.slideDone);

        if ( next != null ) {
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewPager.setCurrentItem(index + 1);
                }
            });
        }

        if ( back != null ) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewPager.setCurrentItem(index - 1);
                }
            });
        }

        if ( done != null ) {
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "DONE", Toast.LENGTH_LONG).show();
                }
            });
        }

        return view;
    }
}


