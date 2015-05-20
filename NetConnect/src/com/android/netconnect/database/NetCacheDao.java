package com.android.netconnect.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.netconnect.http.NetOptions;

/**
 * 网络缓存
 *
 * @author ----zhaoruyang----
 * @data: 2014/12/26
 */
public class NetCacheDao implements INetCacheDao{

    private static volatile NetCacheDao instance = null;
    private final CacheOpenHelper helper;

    /**
     * 需要在Application中初始化
     *
     * @param context
     *         全局Context
     */
    // private constructor suppresses
    private NetCacheDao(Context context) {

        helper = CacheOpenHelper.getInstance(context);

    }

    public static NetCacheDao getInstance(Context context) {
        // if already inited, no need to get lock everytime
        if (instance == null) {
            synchronized (NetCacheDao.class) {
                if (instance == null) {
                    instance = new NetCacheDao(context);
                }
            }
        }

        return instance;
    }

    /**
     * 保存缓存
     *
     * @param field
     *         界面代号
     * @param cache
     *         缓存
     */
    public synchronized void saveCache(int field, String cache, int cache_tag) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CacheOpenHelper.CACHE_NAME, field);
        contentValues.put(CacheOpenHelper.CACHE_INFO, cache);
        contentValues.put(CacheOpenHelper.CACHE_TAG, cache_tag);
        db.replace(CacheOpenHelper.APP_NET_CACHE, null, contentValues);
    }

    /**
     * 获取缓存
     *
     * @param field
     *         界面代号
     *
     * @return 缓存数据
     */
    public String getCacheStr(int field) {
        String rawData = "";
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select "
                + CacheOpenHelper.CACHE_INFO
                + " from "
                + CacheOpenHelper.APP_NET_CACHE
                + " where "
                + CacheOpenHelper.CACHE_NAME
                + "=?", new String[]{String.valueOf(field)});
        while (cursor.moveToNext()) {
            rawData = cursor.getString(0);
        }
        cursor.close();
        return rawData;
    }

    /**
     * 清除退出需要清除的缓存
     */
    public void clearCache() {
        SQLiteDatabase writableDatabase = helper.getWritableDatabase();
        String sql = "delete from "
                + CacheOpenHelper.APP_NET_CACHE
                + " where "
                + CacheOpenHelper.CACHE_TAG
                + " =="
                + NetOptions.CACHE_TAG_CLEAR;
        writableDatabase.execSQL(sql);
    }
}
