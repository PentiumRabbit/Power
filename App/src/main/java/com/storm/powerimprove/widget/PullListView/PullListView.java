
package com.storm.powerimprove.widget.PullListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.Scroller;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

/**
 * 下拉,上滑
 *
 * @author ZhaoRuYang
 */
public class PullListView extends ListView implements OnScrollListener {

    private float lastY = -1;
    private Scroller scroller;
    private OnScrollListener scrollListener;
    private PullListViewListener listViewListener;
    private PullListViewHeader headerView;
    // 默认HeaderView的高德,这里没有设置,所以每次直接回来,可以参看resetHeaderHeight
    private int headerViewHeight;
    private boolean enablePullRefresh = true;
    private boolean pullRefreshing = false;
    private PullListViewFooter footerView;
    private boolean enablePullLoad;
    private boolean pullLoading;
    private int allItemCount;
    private int scrollBack;
    private final static int SCROLL_BACK_HEADER = 0;
    private final static int SCROLL_BACK_FOOTER = 1;
    private final static int SCROLL_DURATION = 400;
    private final static int PULL_LOAD_MORE_DELTA = 50;
    private final static float OFFSET_RADIO = 1.8f;
    private boolean scrollNoFull = false;

    public PullListView(Context context) {
        super(context);
        initWithContext(context);
    }

    public PullListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithContext(context);
    }

    public PullListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initWithContext(context);
    }

    private void initWithContext(Context context) {
        scroller = new Scroller(context, new DecelerateInterpolator());
        super.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true,
                this));

        headerView = new PullListViewHeader(context);
        addHeaderView(headerView);
        footerView = new PullListViewFooter(context);
        addFooterView(footerView);
    }

    public boolean isEnablePullLoad() {
        return enablePullLoad;
    }

    public void setEnablePullLoad(boolean enablePullLoad) {
        this.enablePullLoad = enablePullLoad;
    }

    public boolean isEnablePullRefresh() {
        return enablePullRefresh;
    }

    public void setEnablePullRefresh(boolean enablePullRefresh) {
        this.enablePullRefresh = enablePullRefresh;
    }

    public void stopRefresh() {
        if (pullRefreshing) {
            pullRefreshing = false;
            resetHeaderHeight();
        }
    }

    public void stopLoadMore() {
        if (pullLoading) {
            pullLoading = false;
            footerView.setState(PullListViewFooter.STATE_NORMAL);
        }
    }

    private void invokeOnScrolling() {
        if (scrollListener instanceof OnPullScrollListener) {
            OnPullScrollListener l = (OnPullScrollListener) scrollListener;
            l.onPullScrolling(this);
        }
    }

    private void updateHeaderHeight(float delta) {
        int height = (int) delta + headerView.getVisibleHeight();
        int lvHeight = this.getHeight() / 5;
        if (height > lvHeight) {
            height = lvHeight;
        }
        headerView.setHeight(height);

        if (!enablePullRefresh) {
            return;
        }

        if (pullRefreshing) {
            return;
        }
        if (headerView.getVisibleHeight() > headerViewHeight) {
            headerView.setState(PullListViewHeader.STATE_READY);
        } else {
            headerView.setState(PullListViewHeader.STATE_NORMAL);
        }
        setSelection(0);
    }

    /**
     * reset header view's height.
     */
    private void resetHeaderHeight() {
        int height = headerView.getVisibleHeight();
        if (height == 0)
            return;
        // 当正在刷新,并且获取当前HeaderView小于默认HeaderView时,会有HeaderView露出停留(如果默认的headerViewHeight没有设置时,不会停留)
        if (pullRefreshing && height <= headerViewHeight) {
            return;
        }
        int finalHeight = 0;

        if (pullRefreshing && height > headerViewHeight) {
            finalHeight = headerViewHeight;
        }
        scrollBack = SCROLL_BACK_HEADER;
        scroller.startScroll(0, height, 0, finalHeight - height, SCROLL_DURATION);
        invalidate();
    }

    private void updateFooterHeight(float delta) {
        int height = footerView.getBottomHeight() + (int) delta;
        int lvHeight = this.getHeight() / 5;
        if (height > lvHeight) {
            height = lvHeight;
        }

        if (enablePullLoad && !pullLoading) {
            if (height > PULL_LOAD_MORE_DELTA) {
                footerView.setState(PullListViewFooter.STATE_READY);
            } else {
                footerView.setState(PullListViewFooter.STATE_NORMAL);
            }
        }
        footerView.setBottomHeight(height);

    }

    private void resetFooterHeight() {
        int bottomMargin = footerView.getBottomHeight();
        if (bottomMargin > 0) {
            scrollBack = SCROLL_BACK_FOOTER;
            scroller.startScroll(0, bottomMargin, 0, -bottomMargin, SCROLL_DURATION);
            invalidate();
        }
    }

    private void startLoadMore() {
        pullLoading = true;
        footerView.setState(PullListViewFooter.STATE_LOADING);
        if (listViewListener != null) {
            listViewListener.onLoadMore();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        handleMotionEvent(ev);
        return super.onTouchEvent(ev);
    }

    /**
     * 处理收拾
     *
     * @param ev
     */
    private void handleMotionEvent(MotionEvent ev) {
        if (lastY == -1) {
            lastY = ev.getRawY();
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaY = ev.getRawY() - lastY;
                lastY = ev.getRawY();
                if (getFirstVisiblePosition() == 0
                        && (headerView.getVisibleHeight() > 0 || deltaY > 0)) {
                    updateHeaderHeight(deltaY / OFFSET_RADIO);
                    invokeOnScrolling();
                } else if (isBottonCanScoll()
                        && (footerView.getBottomHeight() > 0 || deltaY < 0)) {
                    updateFooterHeight(-deltaY / OFFSET_RADIO);
                }
                break;
            default:
                lastY = -1;
                if (getFirstVisiblePosition() == 0) {
                    if (enablePullRefresh && headerView.getVisibleHeight() > headerViewHeight) {
                        pullRefreshing = true;
                        headerView.setState(PullListViewHeader.STATE_REFRESHING);
                        if (listViewListener != null) {
                            listViewListener.onRefresh();
                        }
                    }
                    resetHeaderHeight();
                }
                if (getLastVisiblePosition() == allItemCount - 1) {
                    if (enablePullLoad && footerView.getBottomHeight() > PULL_LOAD_MORE_DELTA) {
                        startLoadMore();
                    }
                    resetFooterHeight();
                }
                break;
        }
    }


    /**
     * ListView底部是否可以滑动
     *
     * @return
     */
    private boolean isBottonCanScoll() {
        if (!scrollNoFull) {
            return getFirstVisiblePosition() != 0
                    && getLastVisiblePosition() == allItemCount - 1;
        } else {
            return getLastVisiblePosition() == allItemCount - 1;
        }
    }

    /**
     * 设置在ListView不全屏的情况下,是否可以滑动
     *
     * @param scrollNoFull
     */
    public void setCanScrollNoFull(boolean scrollNoFull) {
        this.scrollNoFull = scrollNoFull;
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            if (scrollBack == SCROLL_BACK_HEADER) {
                headerView.setHeight(scroller.getCurrY());
            } else {
                footerView.setBottomHeight(scroller.getCurrY());
            }
            postInvalidate();
            invokeOnScrolling();
        }
        super.computeScroll();
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        scrollListener = l;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollListener != null) {
            scrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        allItemCount = totalItemCount;
        if (scrollListener != null) {
            scrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }


    /**
     * 设置ListView监听
     *
     * @param l
     */
    public void setPullListViewListener(PullListViewListener l) {
        listViewListener = l;
    }

    /**
     * 回调监听
     *
     * @author ZhaoRuYang
     */
    public interface OnPullScrollListener extends OnScrollListener {
        public void onPullScrolling(View view);
    }

    public interface PullListViewListener {
        public void onRefresh();

        public void onLoadMore();
    }
}
