package com.android.base.common.utils;

import android.text.TextUtils;

import com.android.base.common.value.ValueTAG;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * json数据转换
 * <p/>
 * ZhaoRuYang
 * 6/22/16 12:12 PM
 */
public class JsonDataParser {

    private static final String TAG = "DataParse";

    public static JSONArray getJsonArr(JSONObject jo, String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        if (jo == null) {
            return null;
        }
        if (!jo.has(key)) {
            return null;
        }

        try {
            return jo.getJSONArray(key);
        } catch (JSONException ignored) {
            Logger.e(ValueTAG.EXCEPTION, ignored);
        }
        return null;
    }


    public static JSONObject getJsonObjFromArray(JSONArray ja, int position) {

        if (ja == null) {
            return null;
        }

        try {
            return ja.getJSONObject(position);
        } catch (JSONException ignored) {
            Logger.e(ValueTAG.EXCEPTION, ignored);
        }
        return null;
    }


    public static JSONObject getJsonObj(JSONObject jo, String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        if (jo == null) {
            return null;
        }
        if (!jo.has(key)) {
            return null;
        }

        try {
            return jo.getJSONObject(key);
        } catch (JSONException ignored) {
            Logger.e(ValueTAG.EXCEPTION, ignored);
        }
        return null;
    }

    public static String getJsonStr(JSONObject jo, String key) {
        String msg = "";
        if (TextUtils.isEmpty(key)) {
            return msg;
        }
        if (jo == null) {
            return msg;
        }
        if (!jo.has(key)) {
            return msg;
        }

        try {
            msg = jo.getString(key);
        } catch (JSONException ignored) {
            Logger.e(ValueTAG.EXCEPTION, ignored);
        }
        return msg;
    }

    public static int getJsonInt(JSONObject jo, String key) {
        int msg = 0;
        if (TextUtils.isEmpty(key)) {
            return msg;
        }
        if (jo == null) {
            return msg;
        }
        if (!jo.has(key)) {
            return msg;
        }

        try {
            msg = jo.getInt(key);
        } catch (JSONException e) {
            Logger.e(ValueTAG.EXCEPTION, e);
        }

        return msg;
    }

    protected static double getJsonDouble(JSONObject jo, String key) {
        double msg = -1;
        if (TextUtils.isEmpty(key)) {
            return msg;
        }
        if (jo == null) {
            return msg;
        }
        if (!jo.has(key)) {
            return msg;
        }


        try {
            msg = jo.getDouble(key);
        } catch (JSONException ignored) {
            Logger.e(ValueTAG.EXCEPTION, ignored);
        }

        return msg;
    }

    public static long getJsonLong(JSONObject jo, String key) {
        long msg = -1;
        if (TextUtils.isEmpty(key)) {
            return msg;
        }
        if (jo == null) {
            return msg;
        }
        if (!jo.has(key)) {
            return msg;
        }


        try {
            msg = jo.getLong(key);
        } catch (JSONException ignored) {
            Logger.e(ValueTAG.EXCEPTION, ignored);
        }

        return msg;
    }

    public static boolean getJsonBoolean(JSONObject jo, String key) {
        boolean msg = false;
        if (TextUtils.isEmpty(key)) {
            return msg;
        }
        if (jo == null) {
            return msg;
        }
        if (jo.has(key)) {
            return false;
        }


        try {
            msg = jo.getBoolean(key);
        } catch (JSONException ignored) {
            Logger.e(ValueTAG.EXCEPTION, ignored);
        }

        return msg;
    }

}
