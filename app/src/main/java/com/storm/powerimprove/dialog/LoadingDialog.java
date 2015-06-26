package com.storm.powerimprove.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.storm.powerimprove.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author ----zhaoruyang----
 * @data: 2015/6/17
 */
public class LoadingDialog extends NoFrameDialog {
    private static final String TAG = "LoadingDialog";

    private static final String VALUE_CANCLE = "value_cancle";

    private static final String VALUE_MSG = "value_msg";
    @InjectView(R.id.iv_public_loading_view_rotate)
    ProgressBar ivPublicLoadingViewRotate;
    @InjectView(R.id.tv_public_loading)
    TextView tvPublicLoading;
    @InjectView(R.id.layout_loading)
    RelativeLayout layoutLoading;

    private String loadingMsg;

    public static NoFrameDialog instance(String msg, boolean canCancle) {

        LoadingDialog fragment = new LoadingDialog();
        Bundle args = new Bundle();
        args.putString(VALUE_MSG, msg);
        args.putBoolean(VALUE_CANCLE, canCancle);
        fragment.setArguments(args);
        return fragment;
    }

    public static NoFrameDialog instance(String msg) {

        LoadingDialog fragment = new LoadingDialog();
        Bundle args = new Bundle();
        args.putString(VALUE_MSG, msg);
        args.putBoolean(VALUE_CANCLE, true);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(getArguments().getBoolean(VALUE_CANCLE));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_loading, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        loadingMsg = getArguments().getString(VALUE_MSG);
        tvPublicLoading.setText(loadingMsg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
