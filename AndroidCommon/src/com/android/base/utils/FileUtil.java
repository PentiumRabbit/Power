/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.android.base.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * FileUtil
 *
 * @author ----zhaoruyang----
 */

public class FileUtil {
	public static final File   sdcardPath                 = Environment.getExternalStorageDirectory();
	public static final String SD_FILE_DIR                = "/market/";
	public static final String FILE_CACHE_DIR             = "files/cache/";
	public static final String FILE_DOWNLOAD_DIR          = "files/download/";
	public static final String DATA_FILE_DOWNLOAD_DIR     = "/StormDownload/";
	public static final String USB_DRIVE                  = "/手机U盘/";
	/**
	 * 扫描垃圾文件清理apk程序icon地址
	 */
	public static final String MARKET_SCAN_FILE_ICON      = sdcardPath + "/market/scanIconFile/";
	/**
	 * 扫描垃圾文件icon zip名称
	 */
	public static final String MARKET_SCAN_FILE_ICON_NAME = "scanIconFile.zip";

	public static String getFileCachePath(Context context) {
		String  root;
		File    sdDir;
		boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);

		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();
			root = sdDir.toString() + SD_FILE_DIR + FILE_CACHE_DIR;
			File file = new File(root);
			if (!file.exists()) {
				file.mkdirs();
			}
		} else {
			root = context.getCacheDir().getAbsolutePath() + "/";
		}

		return root;
	}

	/**
	 * app系统目录
	 *
	 * @return
	 */
	public static String getStormMarketExternalStorageDir() {
		return Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/Android/data/com.storm.market/";
	}

	/**
	 * 获取U盘路径
	 *
	 * @return 路径
	 */
	public static String getUSBDevicePath() {
		boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);

		if (sdCardExist) {
			return Environment.getExternalStorageDirectory().getAbsolutePath() + USB_DRIVE;
		}
		return null;
	}

	/**
	 * 清空某个目录下的所有文件及文件夹
	 *
	 * @param filePath 文件路径
	 */
	public static boolean delDirPath(String filePath) {
		return delDirPath(new File(filePath));
	}

	/**
	 * 清空某个目录下的所有文件及文件夹
	 *
	 * @param file 文件
	 */
	public static boolean delDirPath(File file) {
		boolean delFlag = true;
		if (file != null && file.exists() && file.isDirectory()) {
			File[] fileList = file.listFiles();

			for (File aFileList : fileList) {
				if (aFileList.isFile()) {
					aFileList.delete();
				} else if (aFileList.isDirectory()) {
					delDirPath(aFileList);
				}
			}
			delFlag = file.delete();
		}
		return delFlag;
	}

	/**
	 * 删除文件
	 *
	 * @param path 路径
	 * @return 如果成功删除, 或者不存在, 返回true;
	 */
	public static boolean delFile(String path) {
		File file = new File(path);
		return !file.exists() || file.delete();
	}

	/**
	 * write file
	 *
	 * @param filePath
	 * @param content
	 * @param append   is append, if true, write to the end of file, else clear content of file and write into it
	 * @return return false if content is empty, true otherwise
	 * @throws RuntimeException if an error occurs while operator FileWriter
	 */
	public static boolean writeFile(String filePath, String content, boolean append) {
		if (TextUtils.isEmpty(content)) {
			return false;
		}

		FileWriter fileWriter = null;
		try {
			File file = new File(filePath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}

			fileWriter = new FileWriter(filePath, append);
			fileWriter.write(content);
			fileWriter.close();
			return true;
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	/**
	 * write file
	 *
	 * @param filePath
	 * @param contentList
	 * @param append      is append, if true, write to the end of file, else clear content of file and write into it
	 * @return return false if contentList is empty, true otherwise
	 * @throws RuntimeException if an error occurs while operator FileWriter
	 */
	public static boolean writeFile(String filePath, List<String> contentList, boolean append) {
		if (contentList == null || contentList.size() == 0)
			return false;

		FileWriter fileWriter = null;
		try {
			File file = new File(filePath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}


			fileWriter = new FileWriter(filePath, append);
			int i = 0;
			for (String line : contentList) {
				if (i++ > 0) {
					fileWriter.write("\r\n");
				}
				fileWriter.write(line);
			}
			fileWriter.close();
			return true;
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}


}
