package com.zry.power.widget.ImmersionType;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.RelativeLayout;

/**
 * 外框不会变化
 *
 * @Description: FitSysNoChangeLayout
 * @Author: ZhaoRuYang
 * @Update: ZhaoRuYang(2015-10-30 18:38)
 */
public class FitSysNoChangeLayout extends RelativeLayout {
    private static final String TAG = FitSysNoChangeLayout.class.getSimpleName();

    public FitSysNoChangeLayout(Context context) {
        super(context);
    }

    public FitSysNoChangeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FitSysNoChangeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected boolean fitSystemWindows(Rect insets) {
        if (insets.bottom != 0) {
            WindowManager systemService = (WindowManager) getContext()
                    .getSystemService(Context.WINDOW_SERVICE);
            insets.top = insets.bottom - systemService.getDefaultDisplay().getHeight();
        } else {
            insets.top = 0;
        }

        return super.fitSystemWindows(insets);
    }
}
