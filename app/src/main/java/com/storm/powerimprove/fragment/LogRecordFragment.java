package com.storm.powerimprove.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.storm.powerimprove.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Log操作界面
 * Description: LogRecordFragment
 * Author: ZhaoRuYang
 * Update: ZhaoRuYang(2015-07-09 17:50)
 */
public class LogRecordFragment extends LocalDialogFragment implements View.OnClickListener {
    private static final String TAG = LogRecordFragment.class.getSimpleName();
    @InjectView(R.id.til_name)
    TextInputLayout tilName;
    @InjectView(R.id.sp_log_type)
    AppCompatSpinner spLogType;
    @InjectView(R.id.btn_start)
    Button btnStart;
    @InjectView(R.id.et_input_tag)
    EditText etInputTag;

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
        View view = inflater.inflate(R.layout.fragment_log_record, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        btnStart.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    /**
     * 开始捕获日志
     */
    private void startLog() {
        int itemPosition = spLogType.getSelectedItemPosition();
        String logTag = etInputTag.getText().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                startLog();
                break;
        }
    }
}
