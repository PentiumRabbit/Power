package com.zry.base.common.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.common.R;


/**
 * ZhaoRuYang
 * time : 17-9-11
 */

public class ErrorViewDelegate implements View.OnClickListener, IErrorViewControl {

    private ViewGroup rootView;

    private View errorView;
    private View emptyView;
    private View loadingView;


    public ErrorViewDelegate(View rootView) {

        if (rootView == null) {
            throw new NullPointerException("ErrorViewDelegate rootView 不能为空");
        }

        if (!(rootView instanceof ViewGroup)) {
            throw new IllegalStateException("ErrorViewDelegate rootView 必须为ViewGroup");
        }


        this.rootView = (ViewGroup) rootView;
    }


    @Override
    public void setErrorRootView(View view) {
        if (rootView == null) {
            throw new NullPointerException("ErrorViewDelegate rootView 不能为空");
        }

        if (!(rootView instanceof ViewGroup)) {
            throw new IllegalStateException("ErrorViewDelegate rootView 必须为ViewGroup");
        }

        this.rootView = (ViewGroup) view;
    }

    @Override
    public void showCommonErrorView() {

        if (errorView != null) {
            return;
        }

        dismissCommonErrorView();
        errorView = LayoutInflater.from(rootView.getContext()).inflate(R.layout.view_error, rootView, false);
        rootView.addView(errorView);
        errorView.setOnClickListener(this);

    }

    @Override
    public void showCommonEmptyView() {
        if (emptyView != null) {
            return;
        }
        dismissCommonErrorView();
        emptyView = LayoutInflater.from(rootView.getContext()).inflate(R.layout.view_empty, rootView, false);
        rootView.addView(emptyView);
        emptyView.setOnClickListener(this);
    }

    @Override
    public void showCommonLoadingView() {
        if (loadingView != null) {
            return;
        }
        dismissCommonErrorView();
        loadingView = LayoutInflater.from(rootView.getContext()).inflate(R.layout.view_loading, rootView, false);
        rootView.addView(loadingView);
        loadingView.setOnClickListener(this);
    }

    @Override
    public void dismissCommonErrorView() {
        if (errorView != null) {
            rootView.removeView(errorView);
        }

        if (emptyView != null) {
            rootView.removeView(emptyView);
        }

        if (loadingView != null) {
            rootView.removeView(loadingView);
        }

        errorView = null;
        emptyView = null;
        loadingView = null;
    }


    @Override
    public void onClick(View view) {
        // 回调通知
    }
}
