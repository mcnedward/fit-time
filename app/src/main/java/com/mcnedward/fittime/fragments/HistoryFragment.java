package com.mcnedward.fittime.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcnedward.fittime.R;

/**
 * Created by Edward on 7/19/2017.
 */

public class HistoryFragment extends Fragment {
    private static final String TAG = HistoryFragment.class.getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        return view;
    }
}
