/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.android.base.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.Properties;

public class CrashHandler implements UncaughtExceptionHandler {

	private Thread.UncaughtExceptionHandler mDefaultHandler;
	private static CrashHandler INSTANCE;
	private Context mContext;

	private Properties mDeviceCrashInfo = new Properties();
	private static final String VERSION_NAME = "versionName";
	private static final String VERSION_CODE = "versionCode";
	private static final String STACK_TRACE = "STACK_TRACE";
	private static final String CRASH_REPORTER_FILE_NAME = "crash.txt";
	private static final String TAG = "CrashHandler";

	private CrashHandler(Context c) {
		mContext = c;
	}

	public static CrashHandler getInstance(Context c) {
		if (INSTANCE == null) {
			INSTANCE = new CrashHandler(c);
		}
		return INSTANCE;
	}

	public void init(Context ctx) {
		mContext = ctx;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		try {
			collectCrashDeviceInfo(mContext);
			saveCrashInfoToFile(ex);
			formatCrashInfoFile();

			// QualityLogger.getLogger().BengKui_CiShu +=1;
			// BaoFengBox.getInstance().saveLoggerMsg();
		} catch (Exception e) {
		}
		mDefaultHandler.uncaughtException(thread, ex);
	}

	private String saveCrashInfoToFile(Throwable ex) {
		Writer info = new StringWriter();
		PrintWriter printWriter = new PrintWriter(info);
		ex.printStackTrace(printWriter);

		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}

		String result = info.toString();
		printWriter.close();
		mDeviceCrashInfo.put(STACK_TRACE, result);
		try {
			String fileName = FileUtil.getFileCachePath(mContext) + CRASH_REPORTER_FILE_NAME;
			FileOutputStream trace = new FileOutputStream(fileName, false);

			String ylDevInfo = getDeviceInfo();
			trace.write(ylDevInfo.getBytes("utf-8"));
			mDeviceCrashInfo.store(trace, "");
			trace.flush();
			trace.close();
			return fileName;
		} catch (Exception e) {
			LogUtil.e(TAG, "saveCrashInfoToFile", e);
		}
		return null;
	}

	private void formatCrashInfoFile() {
		File file = new File(FileUtil.getFileCachePath(mContext) + CRASH_REPORTER_FILE_NAME);
		if (file.exists()) {
			CrashFileInfo fileInfo = readCreashInfo(file);
			if (fileInfo.isValidate()) {
				String crashInfo = fileInfo.fileContent;
				crashInfo = crashInfo.replace("\\n\\t", "\n");
				String fileName = CRASH_REPORTER_FILE_NAME;
				try {
					FileOutputStream trace = new FileOutputStream(FileUtil.getFileCachePath(mContext) + fileName, false);
					trace.write(crashInfo.getBytes());
					trace.flush();
					trace.close();
				} catch (Exception e) {
				}
			}
		}
	}

	private void collectCrashDeviceInfo(Context ctx) {
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				mDeviceCrashInfo.put(VERSION_NAME, pi.versionName == null ? "not set" : pi.versionName);
				mDeviceCrashInfo.put(VERSION_CODE, "" + pi.versionCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				mDeviceCrashInfo.put(field.getName(), "" + field.get(null));
			} catch (Exception e) {
				LogUtil.e(TAG, "collectCrashDeviceInfo", e);
			}
		}
	}

	private String getDeviceInfo() {
		final String SEP = "|";
		StringBuilder sb = new StringBuilder();
		sb.append(System.currentTimeMillis()).append("\n");

		sb.append(SEP).append(SystemInfoUtil.getIMEI(mContext));
		sb.append(SEP).append(SystemInfoUtil.getProductId());
		sb.append(SEP).append(SystemInfoUtil.getSDKVersion());
		sb.append(SEP).append(SystemInfoUtil.getSDKLevel());
		sb.append("\n");
		return sb.toString();
	}

	private CrashFileInfo readCreashInfo(File file) {
		CrashFileInfo info = new CrashFileInfo();

		StringBuilder crashInfoBuilder = new StringBuilder();

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String logTime = "0";
			String buildNum = "";
			String crashInfo = "";
			try {
				logTime = br.readLine();
				crashInfoBuilder.append(logTime).append("\n");
			} catch (Exception e) {
			}
			buildNum = br.readLine();
			crashInfoBuilder.append(buildNum).append("\n\n");

			String line = br.readLine();
			while (line != null) {
				crashInfoBuilder.append(line).append("\n");
				line = br.readLine();
			}
			crashInfo = crashInfoBuilder.toString();

			info.logTime = logTime;
			info.version = buildNum;
			info.fileContent = crashInfo;
		} catch (Exception e) {
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}

		return info;
	}

	private class CrashFileInfo {
		String logTime = "";
		String version = "";
		String fileContent = "";

		public boolean isValidate() {
			return !TextUtils.isEmpty(logTime) || !TextUtils.isEmpty(version) || !TextUtils.isEmpty(fileContent);
		}
	}

}
