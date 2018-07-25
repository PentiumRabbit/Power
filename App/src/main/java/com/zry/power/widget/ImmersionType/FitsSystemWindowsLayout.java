package com.zry.power.widget.ImmersionType;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * 自适应布局(全球仅此一家,完美解决沉浸式)
 *
 * @Description: FitsSystemWindowsFrameLayout
 * @Author: ZhaoRuYang
 * @Update: ZhaoRuYang(2015-10-30 15:58)
 */
public class FitsSystemWindowsLayout extends RelativeLayout {
    private static final String TAG = FitsSystemWindowsLayout.class.getSimpleName();

    public FitsSystemWindowsLayout(Context context) {
        super(context);
    }

    public FitsSystemWindowsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FitsSystemWindowsLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected boolean fitSystemWindows(Rect insets) {
        insets.top = 0;
        return super.fitSystemWindows(insets);
    }
}