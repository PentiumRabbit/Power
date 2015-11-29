package com.android.base.common.SharedPref.Impl;

import com.android.base.common.SharedPref.ISettingsField;

/**
 * 注解替换枚举
 * <p>
 * ZhaoRuYang
 * 2015/11/29 10:13
 */
public class TestSettingImpl implements ISettingsField {
    private static final String TAG = TestSettingImpl.class.getSimpleName();

    @Override
    public String getPreferenceName() {
        return null;
    }

    @Override
    public int getFileMode() {
        return 0;
    }

    @Override
    public String name() {
        return null;
    }
}
