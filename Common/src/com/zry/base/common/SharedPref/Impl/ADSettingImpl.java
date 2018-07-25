/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.zry.base.common.SharedPref.Impl;

import com.zry.base.ConstantValue;
import com.zry.base.common.SharedPref.ISettingsField;

import android.content.Context;

/**
 * 广告参数值的设置 Created by Administrator on 2014/10/22.
 */
public enum ADSettingImpl implements ISettingsField {

	AD_ESSENTIAL, AD_QUIT, ;

	@Override
	public String getPreferenceName() {
		return ConstantValue.PREFERENCE_NAME_AD;
	}

	@Override
	public int getFileMode() {
		return Context.MODE_PRIVATE;
	}
}
