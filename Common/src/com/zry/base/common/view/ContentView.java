package com.zry.base.common.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.zry.base.common.utils.Logger;
import com.zry.base.common.utils.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 根部View,负责切换View`
 *
 * @author : ZhaoRuYang
 * @date : 2018/7/9
 */
public class ContentView extends ViewGroup {


    private static final String TAG = ContentView.class.getSimpleName();
    private Map<View, Rect> mapsViewRect = new HashMap<>();

    private Map<View, Integer> mao = new HashMap<>();
    private SparseArray<View> otherViews = new SparseArray<>();


    public ContentView(Context context) {
        super(context);
    }

    public ContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        Logger.d("zry", "onLayout() called with " + "changed = [" + changed + "], l = [" + l + "], t = [" + t + "], r = [" + r + "], b = [" + b + "]");
        int childCount = getChildCount();
        if (childCount == 0) {
            return;
        }

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() == GONE) {
                continue;
            }
            updateLayoutLocation(childView);

            Rect rect = mapsViewRect.get(childView);
            if (rect != null) {
                childView.layout(rect.left, rect.top, rect.right, rect.bottom);
            } else {
                childView.layout(l, t, r, b);
            }

        }


    }

    public void add(int layoutId) {
        View inflate = inflate(getContext(), layoutId, null);
        inflate.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        inflate.setVisibility(GONE);
        addView(inflate);
        Logger.i("zry", TAG + " --- add()" + inflate.getClass().getSimpleName());
        otherViews.put(layoutId, inflate);
    }


    public void show(int layoutId) {
        View view = otherViews.get(layoutId);
        if (view == null) {
            Logger.i("zry", TAG + " --- show() view == null ");
            return;
        }
        dismissAll();
        view.setVisibility(VISIBLE);
        Logger.i("zry", TAG + " --- show() view  " + view.getClass().getSimpleName());
    }


    public void dismissAll() {
        int size = otherViews.size();
        for (int i = 0; i < size; i++) {
            int key = otherViews.keyAt(i);
            View view = otherViews.get(key);
            if (view == null) {
                continue;
            }
            view.setVisibility(GONE);
        }
    }

    @Override
    public void addView(View child, int index, LayoutParams params) {

        if (child.getId() == NO_ID) {
            // TODO: ZRY: 2018/7/9 抛出异常,提示用户设置id,如何避免contentView添加时异常?
            Logger.i("zry", TAG + " --- addView() NO_ID child view " + child.getClass().getSimpleName());

        }
        super.addView(child, index, params);
    }


    /**
     * 设置依赖浮在上面的的view
     */
    public void setRootRect(int LayoutId, int rootViewId) {

        View view = otherViews.get(LayoutId);
        if (view == null) {
            return;
        }
        mao.put(view, rootViewId);


    }

    private void updateLayoutLocation(View view) {
        Integer integer = mao.get(view);

        if (integer == null) {
            return;
        }

        Rect rect = mapsViewRect.get(view);
        if (rect != null) {
            return;
        }


        View viewById = findViewById(integer);

        if (viewById == null) {
            return;
        }

        Rect rootViewRect = new Rect();
        viewById.getGlobalVisibleRect(rootViewRect);

        Logger.i("zry", TAG + " --- rootViewRect()" + rootViewRect.toShortString());

        Rect selfRect = new Rect();
        getGlobalVisibleRect(selfRect);
        Logger.i("zry", TAG + " --- selfRect()" + selfRect.toShortString());

        Rect newRect = new Rect();

        newRect.left = rootViewRect.left - selfRect.left;
        newRect.right = getWidth() - (selfRect.right - rootViewRect.right);
        newRect.top = rootViewRect.top - selfRect.top;
        newRect.bottom = getHeight() - (selfRect.bottom - rootViewRect.bottom);

        mapsViewRect.put(view, newRect);
    }


    public void shareRootView() {
        // TODO: ZRY: 2018/7/9 与根部 ContentView 使用一个view
    }

}
