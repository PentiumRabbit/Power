package com.zry.base.common.base;

import android.view.KeyEvent;

/**
 * ZhaoRuYang
 * 16-12-7 下午4:11
 * <p>
 * fragment 高级增强属性
 */
public interface IFragmentPro {

    /**
     * isRefreshing isLoadMore
     */
    int getFragmentUserStatus();

    /**
     * isCreated isStarted isResumed isPaused isStopped isDestroyed
     */
    boolean hasFragmentStatus(int status);

    void addFragmentStatus(int status);

    void removeFragmentStatus(int status);

    /**
     * 刷新该fragment
     */
    void refreshFragment();

    /**
     * Fragment 展示
     */
    void onFragmentPageShow();

    /**
     * Fragment 隐藏
     */
    void onFragmentPageHide();

    /**
     * 由于fragment中不具备事件监听，所以自己实现一套责任链
     * 此处处理界面事件，子类使用时，注意责任链，如果处理就放回true，不出来就放回false
     *
     * @param keyCode
     * @param event
     * @return
     */
    boolean onKeyDown(int keyCode, KeyEvent event);

}
