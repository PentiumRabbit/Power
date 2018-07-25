/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.zry.base.common.bean;

/**
 * BaseFile
 *
 * @author ----zhaoruyang----
 * @data: 2014/11/12
 */
public class BaseFile {
    /*文件字节书*/
    private Long size;
    /*文件全路径*/
    private String path;
    /*文件更新时间*/
    private Long updateTime;
    /*文件类型*/
    private Integer type;
    /*文件名*/
    private String fileName;

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
