package com.android.base.utils;

import android.util.Log;

import com.android.base.common.value.ValueTAG;
import com.google.gson.Gson;

/**
 * LogUtil
 *
 * @author ----zhaoruyang----
 * @version: V1.0
 * @data: 2014年11月7日 下午3:49:47
 */

public class LogUtil {

    private static volatile boolean isDebug = false;

    public static boolean isDebug() {
        return isDebug;
    }

    public static void setDebug(boolean isDebug) {
        LogUtil.isDebug = isDebug;
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable e) {
        if (isDebug) {
            Log.d(tag, msg, e);
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, msg);
        }
    }

    public static void i(String tag, Object msg) {
        if (isDebug) {
            String msgTag = msg == null ? ValueTAG.NULL : msg.getClass().getSimpleName();
            Log.i(tag, msgTag + " json : " + new Gson().toJson(msg));
        }
    }

    public static void i(Class c, String msg) {
        if (isDebug) {
            String msgTag = c == null ? ValueTAG.NULL : msg.getClass().getSimpleName();
            Log.i(msgTag, msg);
        }
    }

    public static void i(String msg) {
        if (isDebug) {
            Log.i(ValueTAG.NONE, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (isDebug) {
            Log.v(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable e) {
        if (isDebug) {
            Log.e(tag, msg, e);
        }
    }

    public static void w(String tag, String msg) {
        if (isDebug) {
            Log.w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable e) {
        if (isDebug) {
            Log.w(tag, msg, e);
        }
    }

}
