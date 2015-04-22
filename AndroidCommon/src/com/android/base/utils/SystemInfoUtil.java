/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.android.base.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.android.base.ConstantValue;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * SystemInfoUtil
 *
 * @author ----zhaoruyang----
 * @version: V1.0
 * @data: 2014年11月7日 下午3:25:49
 */

public class SystemInfoUtil {

    /**
     * recommend default thread pool size according to system available processors, {@link #getDefaultThreadPoolSize()} *
     */
    public static final int DEFAULT_THREAD_POOL_SIZE = getDefaultThreadPoolSize();

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

    public static String getScreenType(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        if (screenWidth <= 320) {
            return "s";
        } else if (screenWidth >= 600) {
            return "l";
        }
        return "m";
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
            paths = (String[]) sm.getClass().getMethod("getVolumePaths", new Class[]{}).invoke(sm, new Object[]{});
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
            LogUtil.e(ConstantValue.TAG_EXCEPTION, "*****EXCEPTION*****\n", e);
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
    private static String getCurProcessName(Context context) {
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
}
