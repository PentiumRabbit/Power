package com.android.base.common.view.stickydecoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.android.base.common.view.stickydecoration.listener.OnGroupClickListener;
import com.android.base.common.utils.Logger;

import java.util.List;

public abstract class BaseDecoration extends RecyclerView.ItemDecoration {

    int mGroupHeight = 120;  //悬浮栏高度

    /**
     * 缓存分组第一个item的position
     */
    private SparseIntArray firstInGroupCash = new SparseIntArray(100);
    /**
     * 记录每个头部和悬浮头部的坐标信息【用于点击事件】
     * 位置由子类添加
     */
    SparseArray<ClickInfo> stickyHeaderPosArray = new SparseArray<>();


    protected OnGroupClickListener mOnGroupClickListener;

    public BaseDecoration() {
    }

    private GestureDetector gestureDetector;


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            //网格布局
            int spanCount = ((GridLayoutManager) manager).getSpanCount();
            if (isFirstLineInGroup(pos, spanCount)) {
                //为悬浮view预留空间
                outRect.top = mGroupHeight;
            }
        } else {
            //其他的默认为线性布局
            //只有是同一组的第一个才显示悬浮栏
            if (isFirstInGroup(pos)) {
                //为悬浮view预留空间
                outRect.top = mGroupHeight;
            }
        }
    }

    /**
     * 判断是不是组中的第一个位置
     * 根据前一个组名，判断当前是否为新的组
     * 当前为groupId为null时，则与上一个为同一组
     */
    protected boolean isFirstInGroup(int position) {
        String preGroupId;
        if (position == 0) {
            preGroupId = null;
        } else {
            preGroupId = getGroupName(position - 1);
        }
        String curGroupId = getGroupName(position);
        if (curGroupId == null) {
            return false;
        }
        return !TextUtils.equals(preGroupId, curGroupId);
    }

    /**
     * 判断是不是新组的第一行（GridLayoutManager使用）
     * 利用当前行的第一个对比前一个组名，判断当前是否为新组的第一样
     */
    protected boolean isFirstLineInGroup(int pos, int spanCount) {
        if (pos == 0) {
            return true;
        } else {
            int posFirstInGroup = getFirstInGroupWithCash(pos);
            if (pos - posFirstInGroup < spanCount) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 网格布局需要调用
     *
     * @param recyclerView      recyclerView
     * @param gridLayoutManager gridLayoutManager
     */
    public void resetSpan(RecyclerView recyclerView, GridLayoutManager gridLayoutManager) {
        if (recyclerView == null) {
            throw new NullPointerException("recyclerView not allow null");
        }
        if (gridLayoutManager == null) {
            throw new NullPointerException("gridLayoutManager not allow null");
        }
        final int spanCount = gridLayoutManager.getSpanCount();
        GridLayoutManager.SpanSizeLookup lookup = new GridLayoutManager.SpanSizeLookup() {//相当于weight
            @Override
            public int getSpanSize(int position) {
                int span;
                String curGroupId = getGroupName(position);
                String nextGroupId;
                try {
                    //防止外面没判断，导致越界
                    nextGroupId = getGroupName(position + 1);
                } catch (Exception e) {
                    nextGroupId = curGroupId;
                }
                if (!TextUtils.equals(curGroupId, nextGroupId)) {
                    //为本行的最后一个
                    int posFirstInGroup = getFirstInGroupWithCash(position);
                    span = spanCount - (position - posFirstInGroup) % spanCount;
                } else {
                    span = 1;
                }
                return span;
            }
        };
        gridLayoutManager.setSpanSizeLookup(lookup);
    }

    /**
     * 得到当前分组第一个item的position
     *
     * @param position position
     */
    protected int getFirstInGroupWithCash(int position) {
        if (firstInGroupCash.get(position) == 0) {
            int firstPosition = getFirstInGroup(position);
            firstInGroupCash.put(position, firstPosition);
            return firstPosition;
        } else {
            return firstInGroupCash.get(position);
        }
    }

    /**
     * 得到当前分组第一个item的position
     *
     * @param position position
     */
    private int getFirstInGroup(int position) {
        if (position == 0) {
            return 0;
        } else {
            if (isFirstInGroup(position)) {
                return position;
            } else {
                return getFirstInGroup(position - 1);
            }
        }
    }

    private GestureDetector.OnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {

            for (int i = 0; i < stickyHeaderPosArray.size(); i++) {
                int key = stickyHeaderPosArray.keyAt(i);
                ClickInfo value = stickyHeaderPosArray.get(key);
                float y = e.getY();
                float x = e.getX();
                if (value.mBottom - mGroupHeight <= y && y <= value.mBottom) {
                    //如果点击到分组头
                    if (value.mDetailInfoList == null || value.mDetailInfoList.size() == 0) {
                        //没有子View的点击事件
                        onGroupClick(key, value.mGroupId);
                    } else {
                        List<ClickInfo.DetailInfo> list = value.mDetailInfoList;
                        boolean isChildViewClicked = false;
                        for (ClickInfo.DetailInfo detailInfo : list) {
                            if (detailInfo.top <= y && y <= detailInfo.bottom
                                    && detailInfo.left <= x && detailInfo.right >= x) {
                                //当前view被点击
                                onGroupClick(key, detailInfo.id);
                                isChildViewClicked = true;
                                break;
                            }
                        }
                        if (!isChildViewClicked) {
                            //点击范围不在带有id的子view中，则表示整个groupView被点击
                            onGroupClick(key, value.mGroupId);
                        }

                    }
                    return true;
                }
            }


            return false;
        }

    };

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);
        addTouchEvent(parent);
        //绘制
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < childCount; i++) {
            Logger.i("zry", " --- onDrawOver()" + i);
            View childView = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(childView);
            if (isFirstInGroup(position) || i == 0) {

                int viewBottom = childView.getBottom();
                //top 决定当前顶部第一个悬浮Group的位置
                int bottom = Math.max(mGroupHeight, childView.getTop() + parent.getPaddingTop());

                Logger.i("zry", " --- viewBottom () : " + viewBottom + " bottom " + bottom);

                if (i == 0) {
                    //下一组的第一个View接近头部
                    bottom = getFirstHeight(parent, state);
                }

                Logger.i("zry", " bottom " + bottom);
                drawDecoration(canvas, position, left, right, bottom);
            }
        }
    }

    /**
     * 设置点击事件
     *
     * @param listener
     */
    protected void setOnGroupClickListener(OnGroupClickListener listener) {
        this.mOnGroupClickListener = listener;
    }

    /**
     * 获取分组名
     *
     * @param position position
     * @return group
     */
    abstract String getGroupName(int position);

    /**
     * 点击事件调用
     *
     * @param position position
     */
    private void onGroupClick(int position, int viewId) {
        if (mOnGroupClickListener != null) {
            mOnGroupClickListener.onClick(position, viewId);
        }
    }

    /**
     * 获取第一个元素的高度
     *
     * @param parent
     * @param state
     * @return
     */
    protected abstract int getFirstHeight(RecyclerView parent, RecyclerView.State state);

    /**
     * 绘制悬浮框
     *
     * @param position position
     * @param left     left
     * @param right    right
     * @param bottom   bottom
     */
    protected abstract void drawDecoration(Canvas canvas, int position, int left, int right, int bottom);

    private void addTouchEvent(RecyclerView parent) {
        //点击事件处理
        if (gestureDetector == null) {
            gestureDetector = new GestureDetector(parent.getContext(), gestureListener);
            parent.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                @Override
                public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                    return false;
                }

                @Override
                public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                    gestureDetector.onTouchEvent(e);
                }

                @Override
                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                }
            });
        }
        stickyHeaderPosArray.clear();
    }


}
