package com.storm.powerimprove.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.ListView;
import android.widget.Scroller;

/**
 * 用于上拉下拉的ListView
 *
 * @Description: LoadListView
 * @Author: ZhaoRuYang
 * @Update: ZhaoRuYang(2015-07-22 17:53)
 */
public class LoadListView extends ListView {
    private static final String TAG = LoadListView.class.getSimpleName();

    public LoadListView(Context context) {
        super(context);
        init(context);
    }

    public LoadListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        Scroller scroller = new Scroller(context, new DecelerateInterpolator());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }
}
