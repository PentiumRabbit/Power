package com.storm.powerimprove.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
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
    private View headerView;
    private View footView;

    private final static float OFFSET_RADIO = 1.8f; // support iOS like pull
    private float mLastY;
    // feature.

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
        headerView = new View(context);
        footView = new View(context);
        addHeaderView(headerView);
        addFooterView(footView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                if (getFirstVisiblePosition() == 0
                        && (headerView.getHeight() > 0 || deltaY > 0)) {
                    // the first item is showing, header has shown or pull down.
                    updateHeaderHeight(deltaY / OFFSET_RADIO);
                } else if (getLastVisiblePosition() == getChildCount() - 1
                        && (footView.getHeight() > 0 || deltaY < 0)) {
                    // last item, already pulled up or want to pull up.
                    updateFooterHeight(-deltaY / OFFSET_RADIO);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                mLastY = -1; // reset
                break;
        }
        return super.onTouchEvent(ev);

    }
    
    private void updateFooterHeight(float v) {

    }
    
    private void updateHeaderHeight(float v) {

    }

}
