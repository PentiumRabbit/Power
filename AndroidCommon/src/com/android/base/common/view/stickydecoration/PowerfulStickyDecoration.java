package com.android.base.common.view.stickydecoration;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.android.base.common.view.stickydecoration.listener.OnGroupClickListener;
import com.android.base.common.view.stickydecoration.listener.PowerGroupListener;
import com.android.base.common.view.stickydecoration.util.ViewUtil;
import com.android.base.common.utils.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 原来的存在bug,改造之
 *
 * @author zhaoruyang
 */

public class PowerfulStickyDecoration extends BaseDecoration {

    private Paint mGroutPaint;

    /**
     * 缓存图片
     * 使用软引用，防止内存泄露
     */
    private Map<String, Bitmap> mBitmapCache = new HashMap<>();

    /**
     * 缓存View
     * 若不使用软引用，内存会明显增多
     * (注意：在异步显示图片时，建议使用强引用)
     */
    private Map<String, View> mHeadViewCache = new HashMap<>();

    private PowerGroupListener mGroupListener;

    private PowerfulStickyDecoration(PowerGroupListener groupListener) {
        super();
        this.mGroupListener = groupListener;
        //设置悬浮栏的画笔---mGroutPaint
        mGroutPaint = new Paint();
    }


    @Override
    protected int getFirstHeight(RecyclerView recyclerView, RecyclerView.State state) {
        int startChild = 0;
        View childAt = recyclerView.getChildAt(startChild);
        int position = recyclerView.getChildAdapterPosition(childAt);


        String curGroupName = getGroupName(position);
        String nextGroupName;
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        //默认往下查找的数量
        int findCount = 1;
        if (manager instanceof GridLayoutManager) {
            int spanCount = ((GridLayoutManager) manager).getSpanCount();
            int firstPositionInGroup = getFirstInGroupWithCash(position);
            findCount = spanCount - (position - firstPositionInGroup) % spanCount;
        }

        while (findCount < state.getItemCount()) {
            View findChild = recyclerView.getChildAt(startChild + findCount);
            int top = findChild.getTop() + recyclerView.getPaddingTop();
            if (mGroupHeight <= top) {
                Logger.i("zry", " --- isLastShowLineInGroup() findCount : " + findCount);
                break;
            }
            findCount++;
        }

        try {
            nextGroupName = getGroupName(position + findCount);
        } catch (Exception e) {
            nextGroupName = curGroupName;
        }
        if (nextGroupName == null) {
            return mGroupHeight;
        }

        if (!TextUtils.equals(curGroupName, nextGroupName)) {
            View findChild = recyclerView.getChildAt(startChild + findCount - 1);
            int bottom = findChild.getBottom() + recyclerView.getPaddingTop();
            if (bottom < mGroupHeight) {
                return bottom;
            }
        }
        return mGroupHeight;
    }


    @Override
    protected void drawDecoration(Canvas c, int position, int left, int right, int bottom) {
        c.drawRect(left, bottom - mGroupHeight, right, bottom, mGroutPaint);
        //根据position获取View
        View groupView;
        String groupName = getGroupName(position);
        if (mHeadViewCache.get(groupName) == null) {
            groupView = getGroupView(position);
            if (groupView == null) {
                return;
            }
            measureAndLayoutView(groupView, left, right);
            mHeadViewCache.put(groupName, groupView);
        } else {
            groupView = mHeadViewCache.get(groupName);
        }
        Bitmap bitmap = getBitmapWithCache(groupView, groupName);
        c.drawBitmap(bitmap, left, bottom - mGroupHeight, null);
        if (mOnGroupClickListener != null) {
            setClickInfo(groupView, left, bottom, position);
        }
    }

    private Bitmap getBitmapWithCache(View groupView, String groupName) {
        Bitmap bitmap;
        if (mBitmapCache.get(groupName) != null) {
            bitmap = mBitmapCache.get(groupName);
        } else {
            bitmap = Bitmap.createBitmap(groupView.getDrawingCache());
            mBitmapCache.put(groupName, bitmap);
        }
        return bitmap;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        super.getItemOffsets(outRect, view, parent, state);
    }

    /**
     * 对view进行测量和布局
     *
     * @param groupView groupView
     * @param left      left
     * @param right     right
     */
    private void measureAndLayoutView(View groupView, int left, int right) {
        groupView.setDrawingCacheEnabled(true);
        //手动对view进行测量，指定groupView的高度、宽度
        groupView.measure(
                View.MeasureSpec.makeMeasureSpec(right, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(mGroupHeight, View.MeasureSpec.EXACTLY));
        mGroupHeight = groupView.getMeasuredHeight();
        Logger.i("zry", " --- measureAndLayoutView() : " + mGroupHeight);
        groupView.layout(left, 0 - mGroupHeight, right, 0);
    }

    /**
     * 点击的位置信息
     *
     * @param groupView
     * @param parentBottom
     * @param position
     */
    private void setClickInfo(View groupView, int parentLeft, int parentBottom, int position) {
        int parentTop = parentBottom - mGroupHeight;
        List<ClickInfo.DetailInfo> list = new ArrayList<>();
        List<View> viewList = ViewUtil.getChildViewWithId(groupView);
        for (View view : viewList) {
            int top = view.getTop() + parentTop;
            int bottom = view.getBottom() + parentTop;
            int left = view.getLeft() + parentLeft;
            int right = view.getRight() + parentLeft;
            list.add(new ClickInfo.DetailInfo(view.getId(), left, right, top, bottom));
        }
        ClickInfo clickInfo = new ClickInfo(parentBottom, list);
        clickInfo.mGroupId = groupView.getId();
        stickyHeaderPosArray.put(position, clickInfo);
    }

    /**
     * 获取组名
     *
     * @param position position
     * @return 组名
     */
    @Override
    String getGroupName(int position) {
        if (mGroupListener != null) {
            return mGroupListener.getGroupName(position);
        } else {
            return null;
        }
    }

    /**
     * 获取组View
     *
     * @param position position
     * @return 组名
     */
    private View getGroupView(int position) {
        if (mGroupListener != null) {
            return mGroupListener.getGroupView(position);
        } else {
            return null;
        }
    }

    /**
     * 是否使用缓存
     *
     * @param b b
     */
    public void setCacheEnable(boolean b) {
        //mHeadViewCache.isCacheable(b);
    }

    /**
     * 是否使用强引用
     *
     * @param b b
     */
    public void setStrongReference(boolean b) {
        //mHeadViewCache.isStrongReference(b);
    }

    /**
     * 清空缓存
     */
    public void cleanCache() {
        mHeadViewCache.clear();
    }

    /**
     * 通知重新绘制
     * 使用场景：网络图片加载后调用
     * 建议：配合{@link #setStrongReference(boolean)}方法使用，体验更佳
     *
     * @param recyclerView recyclerView
     */
    public void notifyRedraw(RecyclerView recyclerView, View viewGroup, String groupName) {
        viewGroup.setDrawingCacheEnabled(false);
        mBitmapCache.remove(groupName);
        mHeadViewCache.remove(groupName);
        int left = recyclerView.getPaddingLeft();
        int right = recyclerView.getWidth() - recyclerView.getPaddingRight();
        measureAndLayoutView(viewGroup, left, right);
        mHeadViewCache.put(groupName, viewGroup);
        recyclerView.invalidate();
    }

    public static class Builder {
        PowerfulStickyDecoration mDecoration;

        private Builder(PowerGroupListener listener) {
            mDecoration = new PowerfulStickyDecoration(listener);
        }

        public static Builder init(PowerGroupListener listener) {
            return new Builder(listener);
        }

        /**
         * 设置Group高度
         *
         * @param groutHeight 高度
         * @return this
         */
        public Builder setGroupHeight(int groutHeight) {
            mDecoration.mGroupHeight = groutHeight;
            return this;
        }

        /**
         * 设置点击事件
         *
         * @param listener 点击事件
         * @return this
         */
        public Builder setOnClickListener(
                OnGroupClickListener listener) {
            mDecoration.setOnGroupClickListener(listener);
            return this;
        }

        /**
         * 重置span
         *
         * @param recyclerView      recyclerView
         * @param gridLayoutManager gridLayoutManager
         * @return this
         */
        public Builder resetSpan(RecyclerView recyclerView, GridLayoutManager gridLayoutManager) {
            mDecoration.resetSpan(recyclerView, gridLayoutManager);
            return this;
        }

        /**
         * 是否使用强引用
         *
         * @param b b
         */
        public Builder setStrongReference(boolean b) {
            mDecoration.setStrongReference(b);
            return this;
        }

        /**
         * 是否使用缓存
         *
         * @param b
         * @return
         */
        public Builder setCacheEnable(boolean b) {
            mDecoration.setCacheEnable(b);
            return this;
        }

        public PowerfulStickyDecoration build() {
            return mDecoration;
        }
    }

}