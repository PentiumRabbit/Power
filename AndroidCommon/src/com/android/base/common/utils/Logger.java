package com.android.base.utils;

import android.util.Log;

import com.android.base.common.value.ValueTAG;
import com.google.gson.Gson;

/**
 * Logger
 *
 * @author ----zhaoruyang----
 * @version: V1.0
 * @data: 2014年11月7日 下午3:49:47
 */

public class Logger {

    private static volatile boolean isDebug = false;

//    private static final Pattern ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$");

    public static boolean isDebug() {
        return isDebug;
    }

    public static void setDebug(boolean isDebug) {
        Logger.isDebug = isDebug;
    }

    public static void d(String tag, String msg) {
        if (!isDebug) {
            return;
        }

        Log.d(tag, msg);

    }

    public static void d(String tag, String msg, Throwable e) {
        if (!isDebug) {
            return;
        }
        Log.d(tag, msg, e);
    }

    public static void d(String msg) {
        if (!isDebug) {
            return;
        }
        Log.d(ValueTAG.NONE, getFormatStr(msg));
    }

    /**
     * 格式化Log
     *
     * @param msg
     *
     * @return
     */
    private static String getFormatStr(String msg) {

        return "------------------------------------------------------------------------------\n|   "
                + getFunctionName()
                + "\n|   "
                + msg
                + "\n------------------------------------------------------------------------------";
    }

    /**
     * 获取栈信息
     *
     * @return 返回：类名-方法名称-行号
     */
    private static String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts == null) {
            return null;
        }
        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }
            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }
            if (st.getClassName().equals(Logger.class.getName())) {
                continue;
            }
            return "[" + Thread.currentThread().getName() + ":" + st.getFileName() + "  " + st.getMethodName() + ":" + st.getLineNumber() + "]";
        }
//        String tag = element.getClassName();
//        Matcher m = ANONYMOUS_CLASS.matcher(tag);
//        if (m.find()) {
//            tag = m.replaceAll("");
//        }
//        return tag.substring(tag.lastIndexOf('.') + 1);
        return null;
    }

    public static void i(String tag, String msg) {
        if (!isDebug) {
            return;
        }
        Log.i(tag, msg);

    }

    public static void i(String tag, Object msg) {
        if (!isDebug) {
            return;
        }
        String msgTag = msg == null ? ValueTAG.NULL : msg.getClass().getSimpleName();
        Log.i(tag, msgTag + " json : " + new Gson().toJson(msg));

    }

    public static void i(Class c, String msg) {
        if (!isDebug) {
            return;
        }
        String msgTag = c == null ? ValueTAG.NULL : msg.getClass().getSimpleName();
        Log.i(msgTag, msg);

    }

    public static void i(String msg) {
        if (!isDebug) {
            return;
        }
        Log.i(ValueTAG.NONE, getFormatStr(msg));

    }

    public static void v(String tag, String msg) {
        if (!isDebug) {
            return;
        }
        Log.v(tag, msg);

    }

    public static void e(String tag, String msg) {
        if (!isDebug) {
            return;
        }
        Log.e(tag, msg);

    }

    public static void e(String tag, Throwable e) {
        if (!isDebug) {
            return;
        }
        Log.e(tag, getFunctionName(), e);

    }

    public static void e(String tag, String msg, Throwable e) {
        if (!isDebug) {
            return;
        }
        Log.e(tag, msg, e);

    }

    public static void e(String tag, Class cls, Throwable e) {
        if (!isDebug) {
            return;
        }
        String msgTag = cls == null ? ValueTAG.NULL : cls.getSimpleName();
        Log.e(tag, msgTag, e);
    }

    public static void w(String tag, String msg) {
        if (!isDebug) {
            return;
        }
        Log.w(tag, msg);

    }

    public static void w(String tag, String msg, Throwable e) {
        if (!isDebug) {
            return;
        }
        Log.w(tag, msg, e);

    }

}
