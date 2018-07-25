package com.zry.power.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zry.base.common.base.BaseFragment;
import com.zry.power.R;

/**
 * ZhaoRuYang
 * 7/8/16 1:55 PM
 */
public class ProgressFragment extends BaseFragment {
    private static final String TAG = ProgressFragment.class.getSimpleName();

    public static ProgressFragment instance() {
        return new ProgressFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        return view;
    }
}
