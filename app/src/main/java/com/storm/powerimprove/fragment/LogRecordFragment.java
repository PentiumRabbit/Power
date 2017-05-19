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


/**
 * Log操作界面
 * Description: LogRecordFragment
 * Author: ZhaoRuYang
 * Update: ZhaoRuYang(2015-07-09 17:50)
 */
public class LogRecordFragment extends LocalDialogFragment implements View.OnClickListener {
    private static final String TAG = LogRecordFragment.class.getSimpleName();

    TextInputLayout tilName;

    AppCompatSpinner spLogType;

    Button btnStart;

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
        etInputTag = (EditText) view.findViewById(R.id.et_input_tag);
        btnStart = (Button) view.findViewById(R.id.btn_start);
        spLogType = (AppCompatSpinner) view.findViewById(R.id.sp_log_type);
        tilName = (TextInputLayout) view.findViewById(R.id.til_name);

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
