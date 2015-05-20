package com.android.netconnect.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.base.utils.LogUtil;

/**
 * 网络数据缓存
 *
 * @author ----zhaoruyang----
 * @data: 2014/12/26
 */
public class CacheOpenHelper extends SQLiteOpenHelper {
    public static final String APP_NET_CACHE = "cache_http";
    public static final String CACHE_NAME = "cache_name";
    public static final String CREATED_TIME = "created_time";
    public static final String DB_NAME = "appcache.db";
    public static final String CACHE_TAG = "cache_tag";
    public static final String CACHE_INFO = "cache_info";
    private static final String TAG = "CacheOpenHelper";

    private CacheOpenHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    private static volatile CacheOpenHelper instance = null;

    public static CacheOpenHelper getInstance(Context context) {
        // if already inited, no need to get lock everytime
        if (instance == null) {
            synchronized (CacheOpenHelper.class) {
                if (instance == null) {
                    instance = new CacheOpenHelper(context);
                }
            }
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //建立缓存数据库
        String create_cache_db = "CREATE TABLE " +
                APP_NET_CACHE + " (" +
                // 缓存界面名称
                CACHE_NAME + " INTEGER," +
                // cache
                CACHE_INFO + " VARCHAR(20000)," +
                // 缓存类型
                CACHE_TAG + " INTEGER," +
                // 时间戳
                CREATED_TIME + " TimeStamp NOT NULL DEFAULT (datetime('now','localtime'))" +
                ")";

        //建立索引表
        String index_cache = "CREATE UNIQUE INDEX " + APP_NET_CACHE + "_index ON " + APP_NET_CACHE + " (" +
                CACHE_NAME + ")";

        /**
         * 建立缓存表
         */
        db.execSQL(create_cache_db);

        /**
         * 建立
         */
        db.execSQL(index_cache);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + APP_NET_CACHE);
    }
}
