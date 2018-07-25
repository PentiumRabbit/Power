package com.zry.base.common.utils;

import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

/**
 * @author ----zhaoruyang----
 * @data: 2015/2/6
 */
public class JsonUtil {
    private static final String TAG = "JsonUtil";

    public static boolean isBadJson(String json) {
        return !isGoodJson(json);
    }

    public static boolean isGoodJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return false;
        }
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonParseException e) {
            return false;
        }
    }
}
