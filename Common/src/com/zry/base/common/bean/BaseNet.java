/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.zry.base.common.bean;

import com.google.gson.annotations.Expose;

/**
 * JSON
 *
 * @author ----zhaoruyang----
 * @data: 2014/11/12
 */
public class BaseNet<T> {
    @Expose
    private Integer status;
    @Expose
    private String msg;
    @Expose
    private Integer apiVersion;
    @Expose
    private String method;
    @Expose
    private String context;
    @Expose
    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(Integer apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
