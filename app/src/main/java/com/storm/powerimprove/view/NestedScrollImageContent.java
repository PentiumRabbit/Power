package com.storm.powerimprove.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
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
import android.view.ViewParent;
import android.widget.AbsListView;

import com.android.base.utils.Logger;
import com.storm.powerimprove.R;

/**
 * 嵌套滑动图片控制
 *
 * @Description: NestedScrollImageContent
 * @Author: ZhaoRuYang
 * @Update: ZhaoRuYang(2015-12-02 15:51)
 */
public class NestedScrollImageContent extends ViewGroup implements NestedScrollingParent {
    private static final String TAG = NestedScrollImageContent.class.getSimpleName();
    private NestedScrollingParentHelper parentHelper;
    private ScrollerCompat scroller;
    private int maximumVelocity;
    private RecyclerView recyclerView;
    private CropImageView header;
    private boolean toucheRecycling;
    private boolean touchCurrent;

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


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Logger.d(TAG, "onLayout() called with l = [" + l + "], t = [" + t + "], r = [" + r + "], b = [" + b + "]");
        int childCount = getChildCount();
        int bottom = t;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof CropImageView) {
                Logger.d(TAG, "method >>>>  onLayout()  IControlHeader");
                bottom = t + 200;
                header.layout(l, t, r, bottom);
            }

            if (childAt instanceof RecyclerView) {
                Logger.d(TAG, "method >>>>  onLayout()  RecyclerView : top --- " + bottom);
                int measuredHeight = recyclerView.getMeasuredHeight();
                Logger.d(TAG, "method >>>>  onLayout()  RecyclerView : measuredHeight --- " + measuredHeight);
                recyclerView.layout(l, bottom, r, b);
            }
        }


    }

    private void init() {
        parentHelper = new NestedScrollingParentHelper(this);
        scroller = ScrollerCompat.create(getContext());
        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        maximumVelocity = configuration.getScaledMaximumFlingVelocity();


        header = new CropImageView(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        header.setLayoutParams(params);
        addView(header);
        Drawable drawable = getResources().getDrawable(R.drawable.pic_war);
        header.setImageDrawable(drawable);
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int actionMasked = MotionEventCompat.getActionMasked(ev);
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                toucheRecycling = checkView(ev, recyclerView);
                break;
            case MotionEvent.ACTION_MOVE:
                touchCurrent = checkView(ev, header);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }


    /**
     * 检查View
     *
     * @return
     */
    private boolean checkView(MotionEvent event, View view) {

        if (view == null) {
            return false;
        }

        MotionEvent obtain = MotionEvent.obtain(event);
        float x = obtain.getX();
        float y = obtain.getY();

        if (y <= view.getBottom()
                && y >= view.getTop()
                && x <= view.getRight()
                && x >= view.getLeft()) {
            return true;
        }
        return false;
    }

    /**
     * @return Whether it is possible for the child view of this layout to
     * scroll up. Override this if the child view is a custom view.
     */
    public boolean canChildScrollUp(View view) {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (view instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) view;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(view, -1) || view.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(view, -1);
        }
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
        int top = header.getTop();
        Logger.d(TAG, "method >>>>  onNestedPreScroll()  top -->" + top);
        if (touchCurrent && toucheRecycling && dy > 0) {
            consumed[1] = dy;
            moveHeader(dy);
        } else if (dy < 0 && top < 0) {
            int comsumedY;
            if (top >= dy) {
                comsumedY = top;
            } else {
                comsumedY = dy;
            }
            consumed[1] = comsumedY;
            moveHeader(comsumedY);
        }

    }

    private void moveHeader(int dy) {
        Logger.d(TAG, "moveHeader() called with " + "dy = [" + dy + "]");
        header.offsetTopAndBottom(-dy);
        recyclerView.offsetTopAndBottom(-dy);

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


}
