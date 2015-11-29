/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.android.base.common.SharedPref;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * SharedPreference
 * <p/>
 * 大数据时，不要使用
 *
 * @author ----zhaoruyang----
 * @version: V1.0
 * @data: 2014年11月7日 下午3:34:28
 */

public class SharedPref {

    public static boolean containSetting(Context context, ISettingsField field) {
        return context.getSharedPreferences(field.getPreferenceName(), field.getFileMode()).contains(field.name());
    }

    public static int getSettingInt(Context context, ISettingsField field, int defaultValue) {
        return context.getSharedPreferences(field.getPreferenceName(), field.getFileMode()).getInt(field.name(), defaultValue);
    }

    public static void setSettingInt(Context context, ISettingsField field, int value) {
        SharedPreferences.Editor settingEditor = context.getSharedPreferences(field.getPreferenceName(), field.getFileMode()).edit();
        settingEditor.putInt(field.name(), value);
        settingEditor.apply();
    }

    public static String getSettingString(Context context, ISettingsField field, String defaultValue) {
        return context.getSharedPreferences(field.getPreferenceName(), field.getFileMode()).getString(field.name(), defaultValue);
    }

    public static void setSettingString(Context context, ISettingsField field, String value) {
        SharedPreferences.Editor settingEditor = context.getSharedPreferences(field.getPreferenceName(), field.getFileMode()).edit();
        settingEditor.putString(field.name(), value);
        settingEditor.apply();
    }

    public static long getSettingLong(Context context, ISettingsField field, long defaultValue) {
        return context.getSharedPreferences(field.getPreferenceName(), field.getFileMode()).getLong(field.name(), defaultValue);
    }

    public static void setSettingLong(Context context, ISettingsField field, long value) {
        SharedPreferences.Editor settingEditor = context.getSharedPreferences(field.getPreferenceName(), field.getFileMode()).edit();
        settingEditor.putLong(field.name(), value);
        settingEditor.apply();
    }

    public static boolean getSettingBoolean(Context context, ISettingsField field, boolean defaultValue) {
        return context.getSharedPreferences(field.getPreferenceName(), field.getFileMode()).getBoolean(field.name(), defaultValue);
    }

    public static void setSettingBoolean(Context context, ISettingsField field, boolean value) {
        SharedPreferences.Editor settingEditor = context.getSharedPreferences(field.getPreferenceName(), field.getFileMode()).edit();
        settingEditor.putBoolean(field.name(), value);
        settingEditor.apply();
    }

    public static void setArrayInt(Context context, ISettingsField field, List<Integer> array) {
        if (array != null && array.size() != 0) {
            StringBuilder sBuilder = new StringBuilder();
            for (Integer item : array) {
                sBuilder.append(item);
                sBuilder.append(",");
            }
            SharedPreferences.Editor settingEditor = context.getSharedPreferences(field.getPreferenceName(), field.getFileMode()).edit();
            settingEditor.putString(field.name(), sBuilder.toString());
            settingEditor.apply();
        }
    }

    public static List<Integer> getArrayInt(Context context, ISettingsField field, String defaultValue) {
        List<Integer> array = new ArrayList<>();
        String s = context.getSharedPreferences(field.getPreferenceName(), field.getFileMode()).getString(field.name(), defaultValue);
        if (s != null) {
            String[] ids = s.split(",");
            for (String id : ids) {
                int val = Integer.parseInt(id);
                array.add(val);
            }
        }
        return array;
    }

    public static void remove(Context context, ISettingsField field) {
        SharedPreferences.Editor settingEditor = context.getSharedPreferences(field.getPreferenceName(), field.getFileMode()).edit();
        settingEditor.remove(field.name());
        settingEditor.apply();
    }

}
