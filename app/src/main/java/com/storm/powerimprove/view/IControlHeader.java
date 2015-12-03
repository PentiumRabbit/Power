package com.storm.powerimprove.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.storm.powerimprove.R;

/**
 * 伴随滑动的头
 * <p>
 * ZhaoRuYang
 * 2015/12/3 17:13
 */
public class IControlHeader extends TextView {
    private static final String TAG = IControlHeader.class.getSimpleName();

    private int offsetY;

    public IControlHeader(Context context) {
        super(context);
        init();
    }

    public IControlHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IControlHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public IControlHeader(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setText("wasfdasf");
        setTextSize(40);
        setBackgroundColor(Color.RED);
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }
}
