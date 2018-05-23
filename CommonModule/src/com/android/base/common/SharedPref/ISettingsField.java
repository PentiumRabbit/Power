/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.android.base.common.SharedPref;

/**
 * ISettingsField
 * 
 * @author ----zhaoruyang----
 * @version: V1.0
 * @data: 2014年11月7日 下午3:36:09
 *
 */

public interface ISettingsField {
	/**
	 * @return 返回sp文件名
	 */
	String getPreferenceName();

	/**
	 * @return 返回文件模式
	 */
	int getFileMode();

	/**
	 * @return 获取枚举名称
	 */
	String name();

}
