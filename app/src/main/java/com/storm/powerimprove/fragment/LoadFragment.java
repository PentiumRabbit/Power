package com.storm.powerimprove.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.base.common.base.BaseFragment;
import com.storm.powerimprove.R;

/**
 * 用于上拉刷新,下拉加载
 *
 * @Description: LoadFragment
 * @Author: ZhaoRuYang
 * @Update: ZhaoRuYang(2015-07-22 17:48)
 */
public class LoadFragment extends BaseFragment {
    private static final String TAG = LoadFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_load, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
