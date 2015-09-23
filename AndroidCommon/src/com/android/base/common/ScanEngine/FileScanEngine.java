package com.android.base.common.ScanEngine;

import android.text.TextUtils;

import java.io.File;

/**
 * 用于文件扫描的引擎
 *
 * @author ----zhaoruyang----
 * @data: 2015/2/6
 */
public class FileScanEngine {
    private static final String TAG = "FileScanEngine";
    private String path;
    private IScanCallback callback;
    private long nanoTime;
    private boolean canScan = true;

    public FileScanEngine(String path, IScanCallback callback) {
        this.path = path;
        this.callback = callback;
    }


    /**
     * 开始扫描
     */
    public void start() {
        if (callback != null) {
            nanoTime = System.nanoTime();
            callback.scanStart();
        }
        if (!TextUtils.isEmpty(path)) {
            scanFile(new File(path));
        }

        if (callback != null) {
            callback.scanOver(System.nanoTime() - nanoTime);
        }
    }

    public void stop() {
        canScan = false;
    }

    /**
     * 扫描的文件夹
     *
     * @param file
     */
    public void scanFile(File file) {
        if (!canScan) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }

        if (callback != null && callback.dirFilter(file.getAbsolutePath() + File.separator)) {
            return;
        }

        File[] files = file.listFiles();

        if (files == null)
            return;

        for (File f : files) {
            if (!canScan) {
                break;
            }
            if (f.isDirectory()) {
                scanFile(f);
            } else {
                if (callback != null && callback.fileFilter(f)) {
                    callback.dealFileInfo(f);
                }
            }
        }


    }


}
