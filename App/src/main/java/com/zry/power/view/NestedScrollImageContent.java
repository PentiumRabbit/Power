package com.zry.power.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.widget.ScrollerCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;


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
    private ScrollerCompat scroller;
    private int maximumVelocity;
    private RecyclerView recyclerView;
    private View header;
    private int contentHeight = 0;
    private boolean stopNestScroll = false;

    private int rTop = -1;
    private int rBottom = -1;
    private int downloadViewID;
    private int moveViewID;

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

        if (stopNestScroll) {
            return;
        }

        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof CropImageView) {
                childAt.layout(l, t, r, t + contentHeight);

            }
            rBottom = b;
            rTop = t + contentHeight;
            if (childAt instanceof RecyclerView) {
                childAt.layout(l, rTop, r, rBottom);
            }


        }


    }

    private void init() {
        parentHelper = new NestedScrollingParentHelper(this);
        scroller = ScrollerCompat.create(getContext());
        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        maximumVelocity = configuration.getScaledMaximumFlingVelocity();


    }


    /**
     * 填充内容
     *
     * @param header       头部
     * @param recyclerView recyclerView
     * @param height       头部高度
     */
    public void addViews(CropImageView header, RecyclerView recyclerView, int height) {
        contentHeight = height;
        this.recyclerView = recyclerView;
        this.header = header;
        addView(header);
        addView(recyclerView, 1);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int actionMasked = MotionEventCompat.getActionMasked(ev);
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                downloadViewID = getViewID(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                moveViewID = getViewID(ev);
                break;
            case MotionEvent.ACTION_UP:
                downloadViewID = 0;
                moveViewID = 0;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }


    private int getViewID(MotionEvent event) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            boolean b = checkView(event, view);
            if (b) {
                return view.hashCode();
            }
        }
        return 0;
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


    ///////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////NestedScrollingParent////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////


    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        stopNestScroll = true;
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        parentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }


    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {


        if (downloadViewID == 0 || moveViewID == 0 || downloadViewID == moveViewID) {
            return;
        }
        int top = header.getTop();
        if (dy > 0 && top >= -contentHeight) {
            consumed[1] = dy;
            moveHeader(dy);
        } else if (dy < 0 && top <= 0) {
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
        header.offsetTopAndBottom(-dy);
        rTop -= dy;
        recyclerView.layout(recyclerView.getLeft(), rTop, recyclerView.getRight(), rBottom);


    }

    @Override
    public void requestLayout() {
        super.requestLayout();
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    }

    @Override
    public void onStopNestedScroll(View target) {
        Log.d(TAG, "method : onStopNestedScroll() ");
        stopNestScroll = false;

    }


    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        Log.d(TAG, "getNestedScrollAxes() called with ");
        return 0;
    }


}
