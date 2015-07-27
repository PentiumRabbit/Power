package com.storm.powerimprove.utils;

import com.android.base.utils.LogUtil;
import com.android.base.utils.ShellUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * 用于记录日志操作的类
 *
 * @Description: LogRecondUtil
 * @Author: ZhaoRuYang
 * @Update: ZhaoRuYang(2015-07-24 18:53)
 */
public class LogRecondUtil {
    private static final String TAG = LogRecondUtil.class.getSimpleName();

    public static void logOutput() throws IOException {

        Process process = Runtime.getRuntime().exec("logcat");
        BufferedReader successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String s;
        while ((s = successResult.readLine()) != null) {
            LogUtil.i(TAG,s);
        }

    }

}
