package com.storm.powerimprove.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;

/**
 * @author ----zhaoruyang----
 * @data: 2015/6/12
 */
public class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.inject(this, view);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
