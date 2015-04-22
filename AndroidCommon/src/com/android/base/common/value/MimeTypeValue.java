package com.android.base.common.value;

import java.util.HashMap;

/**
 * @author ----zhaoruyang----
 * @data: 2014/12/15
 */
public class MimeTypeValue {

    /*后缀名对应的type*/
    private static HashMap<String, String> mimeMap;

    static {
        mimeMap = new HashMap<>();
        mimeMap.put(".3gp", "video/3gpp");
        mimeMap.put(".apk", "application/vnd.android.package-archive");
        mimeMap.put(".asf", "video/x-ms-asf");
        mimeMap.put(".avi", "video/x-msvideo");
        mimeMap.put(".bin", "application/octet-stream");
        mimeMap.put(".bmp", "image/bmp");
        mimeMap.put(".c", "text/plain");
        mimeMap.put(".class", "application/octet-stream");
        mimeMap.put(".conf", "text/plain");
        mimeMap.put(".cpp", "text/plain");
        mimeMap.put(".doc", "application/msword");
        mimeMap.put(".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        mimeMap.put(".xls", "application/vnd.ms-excel");
        mimeMap.put(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        mimeMap.put(".exe", "application/octet-stream");
        mimeMap.put(".gif", "image/gif");
        mimeMap.put(".gtar", "application/x-gtar");
        mimeMap.put(".gz", "application/x-gzip");
        mimeMap.put(".h", "text/plain");
        mimeMap.put(".htm", "text/html");
        mimeMap.put(".html", "text/html");
        mimeMap.put(".jar", "application/java-archive");
        mimeMap.put(".java", "text/plain");
        mimeMap.put(".jpeg", "image/jpeg");
        mimeMap.put(".jpg", "image/jpeg");
        mimeMap.put(".js", "application/x-javascript");
        mimeMap.put(".log", "text/plain");
        mimeMap.put(".m3u", "audio/x-mpegurl");
        mimeMap.put(".m4a", "audio/mp4a-latm");
        mimeMap.put(".m4b", "audio/mp4a-latm");
        mimeMap.put(".m4p", "audio/mp4a-latm");
        mimeMap.put(".m4u", "video/vnd.mpegurl");
        mimeMap.put(".m4v", "video/x-m4v");
        mimeMap.put(".mov", "video/quicktime");
        mimeMap.put(".mp2", "audio/x-mpeg");
        mimeMap.put(".mp3", "audio/x-mpeg");
        mimeMap.put(".mp4", "video/mp4");
        mimeMap.put(".mpc", "application/vnd.mpohun.certificate");
        mimeMap.put(".mpe", "video/mpeg");
        mimeMap.put(".mpeg", "video/mpeg");
        mimeMap.put(".mpg", "video/mpeg");
        mimeMap.put(".mpg4", "video/mp4");
        mimeMap.put(".mpga", "audio/mpeg");
        mimeMap.put(".msg", "application/vnd.ms-outlook");
        mimeMap.put(".ogg", "audio/ogg");
        mimeMap.put(".pdf", "application/pdf");
        mimeMap.put(".png", "image/png");
        mimeMap.put(".pps", "application/vnd.ms-powerpoint");
        mimeMap.put(".ppt", "application/vnd.ms-powerpoint");
        mimeMap.put(".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        mimeMap.put(".prop", "text/plain");
        mimeMap.put(".rc", "text/plain");
        mimeMap.put(".rmvb", "audio/x-pn-realaudio");
        mimeMap.put(".rtf", "application/rtf");
        mimeMap.put(".sh", "text/plain");
        mimeMap.put(".tar", "application/x-tar");
        mimeMap.put(".tgz", "application/x-compressed");
        mimeMap.put(".txt", "text/plain");
        mimeMap.put(".wav", "audio/x-wav");
        mimeMap.put(".wma", "audio/x-ms-wma");
        mimeMap.put(".wmv", "audio/x-ms-wmv");
        mimeMap.put(".wps", "application/vnd.ms-works");
        mimeMap.put(".xml", "text/plain");
        mimeMap.put(".z", "application/x-compress");
        mimeMap.put(".zip", "application/x-zip-compressed");
        mimeMap.put("", "*/*");
    }

    /**
     * 获取文件的mime
     *
     * @param type
     *         文件的后缀
     *
     * @return mime type
     */
    public String getMimeByType(String type) {
        if (type == null)
            return "*/*";
        return mimeMap.get(type);
    }


}
