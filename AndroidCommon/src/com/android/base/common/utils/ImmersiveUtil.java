package com.android.base.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

/**
 * 沉浸式工具
 * <p/>
 * ZhaoRuYang
 * 6/2/16 11:05 AM
 */
public class ImmersiveUtil {
    private static final String TAG = "ImmersiveUtil";
    private SystemBarTintManager tintManager;

    /**
     * 切换到全屏模式，不显示statusBar 和控制栏
     *
     * @param isFullScreen 是否全屏
     * @param activity     响应的activity
     */
    public void changeToFullScreen(boolean isFullScreen, Activity activity) {
        if (isFullScreen) {
            WindowManager.LayoutParams params = activity.getWindow().getAttributes();
            params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            activity.getWindow().setAttributes(params);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams params = activity.getWindow().getAttributes();
            params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            activity.getWindow().setAttributes(params);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        toggleHideBar(isFullScreen, activity);
    }

    /**
     * 隐藏Bar
     *
     * @param hideBar  是否隐藏Bar
     * @param activity 对应的activity
     */
    public void toggleHideBar(boolean hideBar, Activity activity) {

        // The UI options currently enabled are represented by a bitfield.
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = activity.getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);

        // 同或处理，相同为真
        if (hideBar == isImmersiveModeEnabled) {
            return;
        }

        // Navigation bar hiding:  Backwards compatible to ICS.
        if (android.os.Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        if (android.os.Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        activity.getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }


    /**
     * 设置StatusBar颜色
     *
     * @param activity 对应的activity
     */
    public void setStatusBarColor(Activity activity) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }

        tintManager = new SystemBarTintManager(activity);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // set a custom tint color for all system bars
        tintManager.setTintColor(Color.parseColor("#333333"));
    }

    /**
     * 使该界面可用，变换状态栏样式
     */
    protected void setStatusBarColorEnable(boolean enable) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        if (tintManager == null) {
            return;
        }
        tintManager.setStatusBarTintEnabled(enable);
    }


}
