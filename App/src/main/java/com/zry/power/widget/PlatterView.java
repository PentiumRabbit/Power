
package com.zry.power.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 拼版视图,用于首页显示
 *
 * @Description: PlatterView
 * @Author: ZhaoRuYang
 * @Update: ZhaoRuYang(2015-07-13 15:45)
 */
public class PlatterView extends ViewGroup {
    private static final String TAG = PlatterView.class.getSimpleName();

    public PlatterView(Context context) {
        super(context);
    }

    public PlatterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlatterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 水平中点
        int hMiddle = (l + r) / 2;
        // 垂直中点
        int vMiddle = (t + b) / 2;

        int size = getChildCount();
        if (size == 1) {
            View view = getChildAt(0);
            view.layout(l, t, r, b);
        } else if (size == 2) {
            getChildAt(0).layout(l, t, hMiddle - 1, b);
            getChildAt(1).layout(hMiddle + 1, t, r, b);
        } else if (size == 3) {
            getChildAt(0).layout(l, t, hMiddle - 1, b);
            getChildAt(1).layout(hMiddle + 1, t, r, vMiddle - 1);
            getChildAt(2).layout(hMiddle + 1, vMiddle + 1, r, b);
        } else if (size == 4) {
            getChildAt(0).layout(l, t, hMiddle - 1, vMiddle - 1);
            getChildAt(1).layout(hMiddle + 1, t, r, vMiddle - 1);
            getChildAt(2).layout(l, vMiddle + 1, hMiddle - 1, b);
            getChildAt(3).layout(hMiddle + 1, vMiddle + 1, r, b);
        }

    }
}
