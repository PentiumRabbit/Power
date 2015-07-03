/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.android.base.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Locale;

/**
 * SystemMethodUtil
 *
 * @author ----zhaoruyang----
 * @version: V1.0
 * @data: 2014年11月7日 下午3:28:34
 */

public class SystemUtil {
    public static final String STATUS_BAR_SERVICE = "statusbar";

    /**
     * 使用反射关闭下拉的通知栏
     *
     * @param context
     *         the context used to fetch status bar manager
     */
    @SuppressWarnings("ResourceType")
    public static void collapseStatusBar(Context context) throws Exception {

        Object statusBarManager = context.getSystemService(STATUS_BAR_SERVICE);
        Method collapse;

        if (Build.VERSION.SDK_INT <= 16) {
            collapse = statusBarManager.getClass().getMethod("collapse");
        } else {
            collapse = statusBarManager.getClass().getMethod("collapsePanels");
        }
        collapse.invoke(statusBarManager);

    }


    /**
     * 修改文件权限
     *
     * @param permission
     * @param path
     *
     * @return boolean
     *
     * @throws IOException
     * @throws InterruptedException
     * @throw
     */
    public static boolean chmod(String permission, String path) throws IOException, InterruptedException {
        String command = "chmod " + permission + " " + path;
        Runtime runtime = Runtime.getRuntime();
        Process p = runtime.exec(command);
        int status = p.waitFor();
        // chmod succeed
// chmod failed
        return status == 0;
    }

    /**
     * 如果文件在自己包下,没有执行权限,需要先改变权限
     *
     * @param context
     * @param apkPath
     *
     * @throws InterruptedException
     * @throws IOException
     */
    public static void installApkChmod(Context context, String apkPath) throws IOException, InterruptedException {
        if ("".equals(apkPath) || apkPath == null) {
            return;
        }
        if (apkPath.contains(context.getCacheDir() + "/")) {
            // 如果在自己包下,给自己的文件添加执行权限
            if (chmod("755", apkPath)) {
                installApk(context, apkPath);
            } else {
                installApk(context, apkPath);
            }
        } else {
            installApk(context, apkPath);
        }

    }

    /**
     * 调用系统方法安装
     *
     * @param context
     * @param apkPath
     *
     * @return void
     *
     * @throw
     */
    public static void installApk(Context context, String apkPath) {
        Uri uri = Uri.fromFile(new File(apkPath));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 拥有权限静默安装
     *
     * @param context
     * @param path
     * @param option
     *         pm setInstallLocation [0/auto] [1/internal] [2/external]
     *
     * @return boolean
     *
     * @throws IOException
     * @throw
     */

    public static boolean installApkSilently(Context context, String path, int option) throws IOException {
        boolean result = false;
        Process process = null;
        OutputStream out = null;
        InputStream in = null;
        String state = null;
        String installOption = String.format(Locale.getDefault(), " setInstallLocation %d", option);

        process = Runtime.getRuntime().exec("su");
        out = process.getOutputStream();
        out.write(("pm install -r " + path + installOption + "\n").getBytes());
        in = process.getInputStream();
        int len = 0;
        byte[] bs = new byte[256];
        while (-1 != (len = in.read(bs))) {
            state = new String(bs, 0, len);
            if (state.equals("Success\n")) {
                result = true;
                break;
            }
        }
        if (out != null) {
            out.flush();
            out.close();
        }
        if (in != null) {
            in.close();
        }

        return result;
    }
}
