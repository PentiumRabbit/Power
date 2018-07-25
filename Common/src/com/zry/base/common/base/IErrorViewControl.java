package com.zry.base.common.base;

import android.view.View;

/**
 * ZhaoRuYang
 * time : 17-9-12
 */

public interface IErrorViewControl {

    void setErrorRootView(View view);

    void showCommonErrorView();

    void showCommonEmptyView();

    void showCommonLoadingView();

    void dismissCommonErrorView();
}
