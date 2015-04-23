package com.android.base.common.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author ----zhaoruyang----
 * @data: 2015/2/6
 */
public class ExampleFragment extends BaseNewFragment {
    private static final String TAG = "ExampleFragment";


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ExampleFragment newInstance(Bundle bundle) {
        ExampleFragment fragment = new ExampleFragment();

        fragment.setArguments(bundle);
        return fragment;
    }

    public ExampleFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        /*获取传入的数据*/
        Bundle bundle = getArguments();

        /*获取activity的回调*/
        FragmentCallback callback = (FragmentCallback) activity;
        /*进行消息通知*/
//        callback.onFragmentMessage();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
