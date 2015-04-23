package com.android.base.common.ScanEngine;

import java.io.File;

/**
 * @author ----zhaoruyang----
 * @data: 2015/4/23
 */
public interface IScanCallback {
    /*设置文件过滤限制*/
    boolean fileFilter(File file);

    /*设置文件夹过滤限制*/
    boolean dirFilter(String path);

    void scanStart();

    /*扫描到符合条件的文件*/
    void dealFileInfo(File file);

    /*扫描结束,返回用时*/
    void scanOver(long l);
}
