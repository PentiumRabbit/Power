package com.storm.powerimprove.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.storm.powerimprove.R;

/**
 * 退出对话框
 *
 * @author ----zhaoruyang----
 * @data: 2015/6/12
 */
public class ExitDialog extends NoFrameDialog implements View.OnClickListener {
    private static final String TAG = "ExitDialog";
    private TextView tvBtnCancel;
    private TextView tvBtnOk;

    public static NoFrameDialog instance() {
        return new ExitDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_exit, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View root) {
        tvBtnCancel = (TextView) root.findViewById(R.id.tv_btn_cancel);
        tvBtnOk = (TextView) root.findViewById(R.id.tv_btn_ok);
        tvBtnCancel.setOnClickListener(this);
        tvBtnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_btn_ok:
                dismiss();
                setDialogMsg(0, null);
                break;
            case R.id.tv_btn_cancel:
                dismiss();
                break;
        }

    }
}
