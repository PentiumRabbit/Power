package com.zry.power.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zry.power.R;


/**
 * @author ----zhaoruyang----
 * @data: 2015/6/17
 */
public class LoadingDialog extends NoFrameDialog {
    private static final String TAG = "LoadingDialog";

    private static final String VALUE_CANCLE = "value_cancle";

    private static final String VALUE_MSG = "value_msg";
    ProgressBar ivPublicLoadingViewRotate;
    TextView tvPublicLoading;
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
        ivPublicLoadingViewRotate = (ProgressBar) view.findViewById(R.id.pb_loading_bar);
        tvPublicLoading = (TextView) view.findViewById(R.id.tv_loading);
        layoutLoading = (RelativeLayout) view.findViewById(R.id.layout_loading);
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
    }
}
