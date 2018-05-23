package com.android.base.common.holder;

import android.support.v4.widget.Space;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * ZhaoRuYang,逐步使用工厂类替换,统一抽取各个界面样式,大幅度减少工作内容
 * time : 17-5-26
 */

public class ViewItemFactory {

    public BaseHolder getHolder(int type, ViewGroup parent) {
        return new EmptyHolder(new Space(parent.getContext()));
    }


    private View inflater(int layoutId, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
    }


}
