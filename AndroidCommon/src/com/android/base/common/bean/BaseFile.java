/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.android.base.common.bean;

/**
 * BaseFile
 *
 * @author ----zhaoruyang----
 * @data: 2014/11/12
 */
public class BaseFile {
    /*文件字节书*/
    private long size;
    /*文件全路径*/
    private String path;
    /*文件更新时间*/
    private long updateTime;
    /*文件类型*/
    private int type;
    /*文件名*/
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
