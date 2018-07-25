package com.zry.net.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * ZhaoRuYang
 * 7/11/16 6:10 PM
 */
public class NetCacheDao implements INetCacheDao {
    private static final String TAG = NetCacheDao.class.getSimpleName();
    private CacheOpenHelper helper;


    public NetCacheDao(Context context) {
        helper = new CacheOpenHelper(context.getApplicationContext());

    }

    /**
     * 保存缓存
     *
     * @param key
     * @param cache
     */
    @Override
    public void save(String key, String cache, NetSaveModel cache_tag) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL(CacheConst.NetCache.insertOrReplace, new String[]{key, cache, String.valueOf(System.currentTimeMillis())});
        helper.close();
    }

    @Override
    public String get(String key) {
        String msg = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(CacheConst.NetCache.tableSelectByKey, new String[]{key});
        int msgIndex = cursor.getColumnIndex(CacheConst.NetCache.INFO);
        if (cursor.moveToNext()) {
            msg = cursor.getString(msgIndex);
        }
        cursor.close();
        helper.close();
        return msg;

    }

    @Override
    public void clear(NetSaveModel tag) {

    }
}
