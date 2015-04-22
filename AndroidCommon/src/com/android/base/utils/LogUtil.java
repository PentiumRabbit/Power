/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.android.base.utils;

import com.android.base.ConstantValue;

import android.util.Log;

/**
 * LogUtil
 * 
 * @author ----zhaoruyang----
 * @version: V1.0
 * @data: 2014年11月7日 下午3:49:47
 *
 */

public class LogUtil {

	private static final boolean IS_DEBUG = ConstantValue.IS_DEBUG;

	public static void d(String tag, String msg) {
		if (IS_DEBUG) {
			Log.d(tag, msg);
		}
	}

	public static void d(String tag, String msg, Throwable e) {
		if (IS_DEBUG) {
			Log.d(tag, msg, e);
		}
	}

	public static void i(String tag, String msg) {
		if (IS_DEBUG) {
			Log.i(tag, msg);
		}
	}

	public static void v(String tag, String msg) {
		if (IS_DEBUG) {
			Log.v(tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (IS_DEBUG) {
			Log.e(tag, msg);
		}
	}

	public static void e(String tag, String msg, Throwable e) {
		if (IS_DEBUG) {
			Log.e(tag, msg, e);
		}
	}

	public static void w(String tag, String msg) {
		if (IS_DEBUG) {
			Log.w(tag, msg);
		}
	}

	public static void w(String tag, String msg, Throwable e) {
		if (IS_DEBUG) {
			Log.w(tag, msg, e);
		}
	}

}
