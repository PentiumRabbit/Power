package com.android.base.common.utils;

import android.view.View;

/**
 * View工具类
 * <p/>
 * ZhaoRuYang
 * 3/9/16 3:22 PM
 */
public class ViewUtil {
    private static final String TAG = "ViewUtil";

    public static <T extends View> T find(View view, int rid) {
        if (view == null) {
            return null;
        }
        return (T) view.findViewById(rid);
    }

    public static void initListeners(View.OnClickListener listener, View... views) {
        for (View view : views) {
            view.setOnClickListener(listener);
        }
    }

}
