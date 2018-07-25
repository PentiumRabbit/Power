package com.zry.power.view;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ScrollerCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

/**
 * 弹性托盘
 * <p>
 * ZhaoRuYang
 * 10/31/16 4:04 PM
 */
public class ElasticTray extends ViewGroup implements NestedScrollingParent, NestedScrollingChild {
    private static final String TAG = ElasticTray.class.getSimpleName();
    private NestedScrollingParentHelper parentHelper;
    private NestedScrollingChildHelper childHelper;
    private ScrollerCompat scroller;
    private int maximumVelocity;
    private int[] offset = new int[2];
    private int mHeaderHeight = 200;
    private int minimumFlingVelocity;

    static final Interpolator sQuinticInterpolator = new Interpolator() {
        @Override
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };


    public ElasticTray(Context context) {
        this(context, null);
    }

    public ElasticTray(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ElasticTray(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        parentHelper = new NestedScrollingParentHelper(this);
//        childHelper = new NestedScrollingChildHelper(this);
//        setNestedScrollingEnabled(true);

        scroller = ScrollerCompat.create(getContext(), sQuinticInterpolator);
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        maximumVelocity = configuration.getScaledMaximumFlingVelocity();
        minimumFlingVelocity = configuration.getScaledMinimumFlingVelocity();

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof RecyclerView) {
                childAt.layout(l, t, r, b);
            }

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }


    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(0, scroller.getCurrY());
            invalidate();
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////NestedScrollingParent////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////


    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        parentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        parentHelper.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {

        //dy > 0 向上滑动  getScrollY() < mHeaderHeight 头部未完全隐藏
        //dy < 0 向下滑动  getScrollY() >= 0 头部未完全显示
        boolean hide = dy > 0 && getScrollY() < 0;
        boolean show = dy < 0 && !ViewCompat.canScrollVertically(target, -1);

        if (show || hide) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }


    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.i(TAG, " onNestedFling velocityY  : " + velocityY + "  consumed  : " + consumed);
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.i(TAG, " onNestedPreFling velocityY  : " + velocityY);

        //头部隐藏,则交给滚动列表处理
        int scrollY = getScrollY();
        if (scrollY >= 0) {
            return false;
        }
        //滚动
        scroller.fling(0, scrollY, 0, (int) velocityY, 0, 0, scrollY, 0);
        invalidate();
        return true;
    }

    @Override
    public int getNestedScrollAxes() {
        return parentHelper.getNestedScrollAxes();
    }
    ///////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////NestedScrollingChild////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////

//
//    @Override
//    public void setNestedScrollingEnabled(boolean enabled) {
//        childHelper.setNestedScrollingEnabled(enabled);
//    }
//
//    @Override
//    public boolean isNestedScrollingEnabled() {
//        return childHelper.isNestedScrollingEnabled();
//    }
//
//    @Override
//    public boolean startNestedScroll(int axes) {
//        return childHelper.startNestedScroll(axes);
//    }
//
//    @Override
//    public void stopNestedScroll() {
//        childHelper.stopNestedScroll();
//    }
//
//    @Override
//    public boolean hasNestedScrollingParent() {
//        return childHelper.hasNestedScrollingParent();
//    }
//
//    @Override
//    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
//        return childHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
//    }
//
//    @Override
//    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
//        return childHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
//    }
//
//    @Override
//    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
//        return childHelper.dispatchNestedFling(velocityX, velocityY, consumed);
//    }
//
//    @Override
//    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
//        return childHelper.dispatchNestedPreFling(velocityX, velocityY);
//    }

}
