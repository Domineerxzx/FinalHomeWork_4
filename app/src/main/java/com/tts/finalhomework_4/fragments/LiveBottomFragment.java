package com.tts.finalhomework_4.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tts.finalhomework_4.R;

/**
 * Created by 37444 on 2017/12/14.
 */

public class LiveBottomFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.activity_live_buttom, container, false);
        return view;
    }


}
