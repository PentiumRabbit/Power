/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.android.base.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.android.base.ConstantValue;
import com.android.base.common.value.ValueTAG;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * SystemInfoUtil
 *
 * @author ----zhaoruyang----
 * @version: V1.0
 * @data: 2014年11月7日 下午3:25:49
 */

public class SysInfoUtil {

    /**
     * recommend default thread pool size according to system available processors, {@link #getDefaultThreadPoolSize()} *
     */
    public static final int DEFAULT_THREAD_POOL_SIZE = getDefaultThreadPoolSize();
    private static final String TAG = "SystemInfoUtil";

    /**
     * get recommend default thread pool size
     *
     * @return if 2 * availableProcessors + 1 less than 8, return it, else return 8;
     *
     * @see {@link #getDefaultThreadPoolSize(int)} max is 8
     */
    public static int getDefaultThreadPoolSize() {
        return getDefaultThreadPoolSize(8);
    }

    /**
     * get recommend default thread pool size
     *
     * @param max
     *
     * @return if 2 * availableProcessors + 1 less than max, return it, else return max;
     */
    public static int getDefaultThreadPoolSize(int max) {
        int availableProcessors = 2 * Runtime.getRuntime().availableProcessors() + 1;
        return availableProcessors > max ? max : availableProcessors;
    }

    public static String currentVersion(Context context)
            throws NameNotFoundException {
        String pkg = context.getApplicationContext().getPackageName();
        PackageManager pManager = context.getApplicationContext()
                .getPackageManager();
        PackageInfo info = pManager.getPackageInfo(pkg, 0);
        return info.versionName;

    }

    public static int currentVersionCode(Context context)
            throws NameNotFoundException {
        String pkg = context.getApplicationContext().getPackageName();
        PackageManager pManager = context.getApplicationContext()
                .getPackageManager();
        PackageInfo info = pManager.getPackageInfo(pkg, 0);
        return info.versionCode;

    }

    public static String currentGID(Context context)
            throws NameNotFoundException {
        String pkg = context.getApplicationContext().getPackageName();
        PackageManager pManager = context.getApplicationContext()
                .getPackageManager();
        return pManager.getApplicationInfo(pkg, PackageManager.GET_META_DATA).metaData
                .get("UMENG_CHANNEL").toString();
    }

    public static String getIMEI(Context context) {
        String imei = ((TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        if (TextUtils.isEmpty(imei)) {
            imei = "";
        }
        return imei;
    }

    public static String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.CHINA);
        return format.format(new Date());
    }

    /**
     * 获取android SDK版本信息
     *
     * @return 版本
     */
    public static int getAndroidSDKVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 获取系统中所有可用SD卡磁盘目录。 例如：/mnt/sdcard 。 /mnt/external_sd 。 /mnt/usb_storage 。
     *
     * @param context
     * @param isContainESD
     *         是否包括getExternalStorageDirectory()方法获得的路径
     *         例如：/mnt/sdcard
     *
     * @return result 如果list为空则没有SD卡，如果为null则出现异常
     */
    @SuppressLint("NewApi")
    public static ArrayList<String> getSdPathListByInvoke(Context context, boolean isContainESD) {
        StorageManager sm = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        // 获取sdcard的路径：外置和内置
        String[] paths;
        ArrayList<String> result = new ArrayList<String>();
        try {
            paths = (String[]) sm.getClass().getMethod("getVolumePaths", new Class[]{}).invoke(sm);
            String esd = Environment.getExternalStorageDirectory().getAbsolutePath();
            for (String path : paths) {
                if (!isContainESD && path.equals(esd)) {
                    continue;
                }
                File sdFile = new File(path);
                if (sdFile.canWrite() && !result.contains(path)) {
                    result.add(path);
                }
            }
        } catch (IllegalAccessException
                | InvocationTargetException
                | NoSuchMethodException e) {
            LogUtil.e(ValueTAG.EXCEPTION, "*****EXCEPTION*****\n", e);
        }


        return result;
    }

    /**
     * 获取系统中所有可用SD卡磁盘目录。 例如：/mnt/sdcard 。 /mnt/external_sd 。 /mnt/usb_storage 。
     *
     * @param isContainESD
     *         是否包括getExternalStorageDirectory()方法获得的路径
     *
     * @return result 如果list为空则没有SD卡，如果为null则出现异常
     */
    private static ArrayList<String> getSdpathListByCanWrite(boolean isContainESD) {
        ArrayList<String> result = new ArrayList<String>();
        result.clear();
        try {
            File sdfile = Environment.getExternalStorageDirectory().getAbsoluteFile();
            File parentFile = sdfile.getParentFile();
            File[] listFiles = parentFile.listFiles();

            for (File listFile : listFiles) {
                if (!isContainESD && listFile.equals(sdfile)) {
                    continue;
                }

                if (listFile.exists() && listFile.isDirectory() && listFile.canWrite()) {
                    String path = listFile.getAbsolutePath();
                    if (!result.contains(path)) {
                        result.add(path);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getProductId() {
        return Build.MODEL;
    }

    @SuppressLint("DefaultLocale")
    public static String getSDKVersion() {
        return String.format("%d", Build.VERSION.SDK_INT);
    }

    public static String getSDKLevel() {
        return Build.VERSION.RELEASE;
    }

    @SuppressWarnings("deprecation")
    public static long getSDCardAvailableSpace() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize / 1024;
        }
        return 0;
    }

    /**
     * 获取当前进程名
     *
     * @param context
     *
     * @return
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }

    /**
     * 获取最近运行的程序列表（近期任务），长按home键所示效果
     *
     * @param context
     *
     * @return
     */
    public static List<String> getTaskList(Context context) {

        List<String> appsPackageName = new ArrayList<>();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        PackageManager pm = context.getPackageManager();

        List<ActivityManager.RecentTaskInfo> list = am.getRecentTasks(10, 0);
        for (ActivityManager.RecentTaskInfo ti : list) {

            //无法获取最后使用时间
//            // 得到类对象
//            try {
//
//                Class userCla =  ActivityManager.RecentTaskInfo.class;
//                Field[] declaredFields = userCla.getDeclaredFields();
//                for (Field field:declaredFields)
//                {
//                    field.setAccessible(true);
//                    LogUtil.i(TAG,"field----"+field.getName());
//                }
//                Field lastActiveTime = userCla.getDeclaredField("lastActiveTime");
//                lastActiveTime.setAccessible(true);
//                long lastUsedTime = (long) lastActiveTime.get(ti);
//                LogUtil.i(TAG, "lastUsedTime----" + lastUsedTime);
//            } catch (NoSuchFieldException | IllegalAccessException e) {
//                LogUtil.e(ValueTAG.EXCEPTION, "*****EXCEPTION*****\n", e);
//            }


            Intent intent = ti.baseIntent;
            ResolveInfo resolveInfo = pm.resolveActivity(intent, 0);
            if (resolveInfo != null) {
                ComponentInfo ci = getComponentInfo(resolveInfo);
                String resolvePackageName = ci.packageName;
                LogUtil.i(TAG, "resolvePackageName----" + resolvePackageName);
                appsPackageName.add(resolvePackageName);
            }
        }
        return appsPackageName;

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static ComponentInfo getComponentInfo(ResolveInfo resolveInfo) {
        if (resolveInfo.activityInfo != null) return resolveInfo.activityInfo;
        if (resolveInfo.serviceInfo != null) return resolveInfo.serviceInfo;
        if (resolveInfo.providerInfo != null) return resolveInfo.providerInfo;
        throw new IllegalStateException("Missing ComponentInfo!");
    }

    /**
     * 判断自己的程序是否在前台
     *
     * @param context
     *
     * @return
     */
    public static boolean isRunningForeground(Context context) {
        String packageName = context.getPackageName();
        String topActivityClassName = getTopActivityName(context);
        return packageName != null && topActivityClassName != null && topActivityClassName.startsWith(packageName);
    }

    /**
     * 获取顶端activity名称
     *
     * @param context
     *
     * @return
     */
    public static String getTopActivityName(Context context) {
        String topActivityClassName = null;
        ActivityManager activityManager = (ActivityManager) (context.getSystemService(android.content.Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1);
        if (runningTaskInfos != null) {
            ComponentName f = runningTaskInfos.get(0).topActivity;
            topActivityClassName = f.getClassName();
        }
        return topActivityClassName;
    }

    /**
     * 获取Wifi名称即SSID
     *
     * @param context
     *
     * @return
     */
    public static String getWifiSSID(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getSSID();
    }
}
