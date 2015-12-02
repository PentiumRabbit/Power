package com.storm.powerimprove.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.widget.ScrollerCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.base.utils.Logger;

/**
 * 嵌套滑动图片控制
 *
 * @Description: NestedScrollImageContent
 * @Author: ZhaoRuYang
 * @Update: ZhaoRuYang(2015-12-02 15:51)
 */
public class NestedScrollImageContent extends FrameLayout implements NestedScrollingParent {
    private static final String TAG = NestedScrollImageContent.class.getSimpleName();
    private NestedScrollingParentHelper parentHelper;
    private TextView textView;
    private ScrollerCompat scroller;
    private int minimumVelocity;
    private int maximumVelocity;
    private RecyclerView recyclerView;

    public NestedScrollImageContent(Context context) {
        super(context);
        init();
    }

    public NestedScrollImageContent(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NestedScrollImageContent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NestedScrollImageContent(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        parentHelper = new NestedScrollingParentHelper(this);

        scroller = ScrollerCompat.create(getContext());
        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        minimumVelocity = configuration.getScaledMinimumFlingVelocity();
        maximumVelocity = configuration.getScaledMaximumFlingVelocity();

        textView = new TextView(getContext());
        textView.setText("打击好");
        textView.setTextSize(60);
        addView(textView);
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////NestedScrollingParent////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////


    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.d(TAG, "method : onStartNestedScroll() ");
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        Log.d(TAG, "method : onNestedScrollAccepted() ");
        parentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }


    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        Log.d(TAG, "method : onNestedPreScroll() ");
        Log.i(TAG, "recyclerView.computeVerticalScrollOffset() :  " + recyclerView.computeVerticalScrollOffset());
        if (recyclerView.computeVerticalScrollOffset() == 0) {
//            recyclerView.setY(textView.getY() - dy);
            recyclerView.offsetTopAndBottom(-dy);
        }


    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.d(TAG, "method : onNestedScroll() ");

    }

    @Override
    public void onStopNestedScroll(View target) {
        Log.d(TAG, "method : onStopNestedScroll() ");
    }


    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.d(TAG, "onNestedFling() called with ");
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.d(TAG, "onNestedPreFling() called with ");
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        Log.d(TAG, "getNestedScrollAxes() called with ");
        return 0;
    }

    /**
     * 处理滑动
     */
    private void fling(float velocityX, float velocityY) {

        velocityX = Math.max(-maximumVelocity, Math.min(velocityX, maximumVelocity));
        velocityY = Math.max(-maximumVelocity, Math.min(velocityY, maximumVelocity));

        scroller.fling(0, 0, (int) velocityX, (int) velocityY,
                Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
}
