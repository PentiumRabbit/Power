package com.storm.powerimprove.engine;

import com.android.base.utils.LogUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Log输出类
 *
 * @author ----zhaoruyang----
 * @data: 2015-7-25
 */
public class LogShell {

    private static final String TAG = LogShell.class.getName();
    private static volatile LogShell instance = null;

    // private constructor suppresses
    private LogShell() {
    }

    public static LogShell getInstance() {
        // if already inited, no need to get lock everytime
        if (instance == null) {
            synchronized (LogShell.class) {
                if (instance == null) {
                    instance = new LogShell();
                }
            }
        }

        return instance;
    }

    /**
     * log 输出
     *
     * @throws IOException
     */
    public void logOutput() throws IOException {
        Process process = Runtime.getRuntime().exec("logcat");
        BufferedReader successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String s;
        while ((s = successResult.readLine()) != null) {
            LogUtil.i(TAG, s);
        }
    }


    /**
     * log 输出重定向
     *
     * @throws IOException
     */
    public void logOutputLocal() throws IOException {
        Process process = Runtime.getRuntime().exec("logcat");
        BufferedReader successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String s;
        while ((s = successResult.readLine()) != null) {
            LogUtil.i(TAG, s);
        }
    }


}