package com.android.base;

/**
 * ZhaoRuYang
 * time : 17-9-8
 */

public class AppConstant {
    /**
     * 用户使用状态
     */
    public interface FragmentUserStatus {
        int SHOW_REFRESH = 1;
        int REFRESHING = 2;
        int NORMAL = 3;
    }

    /**
     * 标记fragment 状态
     */
    public interface FragmentStatus {
        int isCreated = 1;
        int isStarted = 1 << 1;
        int isResumed = 1 << 2;
        int isShowed = 1 << 3;
    }
}
