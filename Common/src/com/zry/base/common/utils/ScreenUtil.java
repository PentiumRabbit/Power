package com.zry.base.common.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.zry.base.common.value.ValueTAG;
import com.zry.base.common.value.ValueTAG;

import java.lang.reflect.Field;

/**
 * @author ----zhaoruyang----
 * @data: 2015/7/2
 */
public class ScreenUtil {
    private static final String TAG = ScreenUtil.class.getSimpleName();

    /**
     * 用于获取状态栏的高度。
     *
     * @return 返回状态栏高度的像素值。
     */
    public static int getStatusBarHeight(Activity activity) {


        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return activity.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            Logger.e(ValueTAG.EXCEPTION, "*****EXCEPTION*****\n", e);
        }

        return 0;


    }

    /**
     * 沉浸式布局
     *
     * @param view
     */
    public static void setImmerseLayout(View view) {
        if (view == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = ScreenUtil.getStatusBarHeight(view.getContext());
            view.setPadding(0, statusBarHeight, 0, 0);
        }
    }

    /**
     * 用于获取状态栏的高度。
     *  使用Resource对象获取（推荐这种方式）
     * @return 返回状态栏高度的像素值。
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /**
     * 获取标题栏高度
     *
     * @param activity
     *
     * @return
     */
    public static int getTitleBarHeight(Activity activity) {
        int contentTop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        return contentTop - getStatusBarHeight(activity);
    }


    /**
     * 在Activity中获取屏幕的高度和宽度
     *
     * @param activity
     *         在真机中，有时候会发现得到的尺寸不是很准确，需要在AndroidManifest中添加如下配置：
     *         <supports-screens
     *         android:smallScreens="true"
     *         android:normalScreens="true"
     *         android:largeScreens="true"
     *         android:resizeable="true"
     *         android:anyDensity="true" />
     */
    public static int[] getScreenSize(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return new int[]{point.x , point.y};
    }


    /**
     * 在非Activity中，通常会在Custom View时
     * <p/>
     * 目前不推荐使用（3.2及以下）
     *
     * @param context
     *         在真机中，有时候会发现得到的尺寸不是很准确，需要在AndroidManifest中添加如下配置：
     *         <supports-screens
     *         android:smallScreens="true"
     *         android:normalScreens="true"
     *         android:largeScreens="true"
     *         android:resizeable="true"
     *         android:anyDensity="true" />
     */
    public static int[] getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return new int[]{dm.widthPixels , dm.heightPixels};

    }



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
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        if (context == null) {
            return 0;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        if (context == null) {
            return 0;
        }
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        if (context == null) {
            return 0;
        }
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        if (context == null) {
            return 0;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
