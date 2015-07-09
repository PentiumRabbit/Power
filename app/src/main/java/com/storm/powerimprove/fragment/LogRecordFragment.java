package com.storm.powerimprove.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.storm.powerimprove.R;

/**
 * Log操作界面
 * Description: LogRecordFragment
 * Author: ZhaoRuYang
 * Update: ZhaoRuYang(2015-07-09 17:50)
 */
public class LogRecordFragment extends LocalDialogFragment {
    private static final String TAG = LogRecordFragment.class.getSimpleName();

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static LogRecordFragment newInstance() {
        LogRecordFragment fragment = new LogRecordFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_log_record, container, false);
    }
}
