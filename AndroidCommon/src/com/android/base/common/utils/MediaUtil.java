/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.android.base.utils;

import android.text.TextUtils;

import java.io.File;

/**
 * 用于影音分类
 * Created by zhaoruyang on 2014/12/8.
 */
public class MediaUtil {

    /*未知*/
    public static final int TYPE_MEDIA_NONE = 100;
    /*文件夹*/
    public static final int TYPE_FILE_DIR = 1;
    /*视频*/
    public static final int TYPE_MEDIA_VIDEO = 2;
    /*音频*/
    public static final int TYPE_MEDIA_MUSIC = 3;
    /*图片*/
    public static final int TYPE_MEDIA_PIC = 4;
    /*APK*/
    public static final int TYPE_MEDIA_APK = 5;
    /*文件*/
    public static final int TYPE_MEDIA_TXT = 6;
    /*字幕类*/
    public static final int TYPE_MEDIA_SUBTITLE = 7;

    /**
     * 媒体分类
     *
     * @param path
     *         路径
     *
     * @return 文件类型
     */
    public static int classifyMedia(String path) {

        if (TextUtils.isEmpty(path)) {
            return TYPE_MEDIA_NONE;
        }

        if (new File(path).isDirectory()) {
            return TYPE_FILE_DIR;
        }
        int i = path.lastIndexOf(".");
        if (i <= 0)
            return TYPE_MEDIA_NONE;
        String pathName = new String(path.substring(i));
        /*音频类*/
        if (pathName.equalsIgnoreCase(".mp3") || pathName.equalsIgnoreCase(".wma")
                || pathName.equalsIgnoreCase(".ogg") || pathName.equalsIgnoreCase(".wav")
                || pathName.equalsIgnoreCase(".aac") || pathName.equalsIgnoreCase(".mid")
                || pathName.equalsIgnoreCase(".midi") || pathName.equalsIgnoreCase(".rmi")
                || pathName.equalsIgnoreCase(".au") || pathName.equalsIgnoreCase(".amr")
                || pathName.equalsIgnoreCase(".flac") || pathName.equalsIgnoreCase(".ape")
                || pathName.equalsIgnoreCase(".m4a")) {
            return TYPE_MEDIA_MUSIC;
        }
        /*视频类*/
        else if (pathName.equalsIgnoreCase(".3gp") || pathName.equalsIgnoreCase(".avi")
                || pathName.equalsIgnoreCase(".flv") || pathName.equalsIgnoreCase(".mp4")
                || pathName.equalsIgnoreCase(".rm") || pathName.equalsIgnoreCase(".rmvb")
                || pathName.equalsIgnoreCase(".bhd") || pathName.equalsIgnoreCase(".3g2")
                || pathName.equalsIgnoreCase(".f4v") || pathName.equalsIgnoreCase(".mkv")
                || pathName.equalsIgnoreCase(".mpeg") || pathName.equalsIgnoreCase(".mpg")
                || pathName.equalsIgnoreCase(".wmv") || pathName.equalsIgnoreCase(".divx")
                || pathName.equalsIgnoreCase(".m4v") || pathName.equalsIgnoreCase(".mov")
                || pathName.equalsIgnoreCase(".asf")
                ) {
            return TYPE_MEDIA_VIDEO;
        }
        /*图片类*/
        else if (pathName.equalsIgnoreCase(".jpg") || pathName.equalsIgnoreCase(".png")
                || pathName.equalsIgnoreCase(".gif") || pathName.equalsIgnoreCase(".bmp")
                || pathName.equalsIgnoreCase(".jpeg") || pathName.equalsIgnoreCase(".ico")
                ) {
            return TYPE_MEDIA_PIC;
        }
        /*文本类*/
        else if (pathName.equalsIgnoreCase(".xls") || pathName.equalsIgnoreCase(".doc")
                || pathName.equalsIgnoreCase(".txt") || pathName.equalsIgnoreCase(".pdf")
                || pathName.equalsIgnoreCase(".ppt") || pathName.equalsIgnoreCase(".html")
                || pathName.equalsIgnoreCase(".xlsx") || pathName.equalsIgnoreCase(".xlsm")
                || pathName.equalsIgnoreCase(".wps") || pathName.equalsIgnoreCase(".docx")
                || pathName.equalsIgnoreCase(".pps") || pathName.equalsIgnoreCase(".wri")
                || pathName.equalsIgnoreCase(".mht") || pathName.equalsIgnoreCase(".pot")
                || pathName.equalsIgnoreCase(".json") || pathName.equalsIgnoreCase(".c")
                || pathName.equalsIgnoreCase(".h") || pathName.equalsIgnoreCase(".cpp")
                || pathName.equalsIgnoreCase(".java") || pathName.equalsIgnoreCase(".log")
                ) {
            return TYPE_MEDIA_TXT;
        }
        /*安装包*/
        else if (pathName.equalsIgnoreCase(".apk")
                ) {
            return TYPE_MEDIA_APK;
        }

         /*字幕文件,目前只支持前三种*/
        else if (pathName.equalsIgnoreCase(".srt") || pathName.equalsIgnoreCase(".ass")
                || pathName.equalsIgnoreCase(".ssa")
                ) {
            return TYPE_MEDIA_SUBTITLE;
        }
        return TYPE_MEDIA_NONE;
    }
}
