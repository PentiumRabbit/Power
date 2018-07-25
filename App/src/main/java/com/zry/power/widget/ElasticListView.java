
package com.zry.power.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ListView;

/**
 * 简单实现
 * 弹性listView。 注意，当且仅当在列表项目够多可占满一个屏幕以上时才能显现出弹性效果。
 */
public class ElasticListView extends ListView {

    /**
     * 初始可拉动Y轴方向距离
     */
    private static final int OVER_SCROLL_Y = 120;

    // 实际可上下拉动Y轴上的距离
    private int distance;

    public ElasticListView(Context context) {
        super(context);
        initWidget();
    }

    public ElasticListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWidget();
    }

    public ElasticListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initWidget();
    }

    private void initWidget() {
        // 初始化参数
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        float density = metrics.density;
        distance = (int) (density * OVER_SCROLL_Y);
    }

    /* 拓展：弹性 */

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY,
                                   int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY,
                                   boolean isTouchEvent) {
        // 实现的本质就是在这里动态改变了maxOverScrollY的值
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY,
                maxOverScrollX, distance, isTouchEvent);
    }

}
