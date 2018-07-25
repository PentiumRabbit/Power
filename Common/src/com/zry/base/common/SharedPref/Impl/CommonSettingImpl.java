/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.zry.base.common.SharedPref.Impl;

import android.content.Context;

import com.zry.base.ConstantValue;
import com.zry.base.common.SharedPref.ISettingsField;

/**
 * 共同拥有的属性配置 Created by Administrator on 2014/10/22.
 */
public enum CommonSettingImpl implements ISettingsField {
    FIRST_STRART, RECORD_WIFI_SSID;
    @Override
    public String getPreferenceName() {
        return ConstantValue.PREFERENCE_NAME_COMMON;
    }

    @Override
    public int getFileMode() {
        return Context.MODE_PRIVATE;
    }
}
