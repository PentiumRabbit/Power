package com.android.netconnect.database;

/**
 * ZhaoRuYang
 * 7/12/16 1:35 PM
 */
public interface CacheConst {

    /**
     * 数据库名称
     */
    String DB_NAME = "cache.db";

    /**
     * 数据库版本号
     */
    int DB_VERSION = 1;

    interface NetCache {
        String TABLE_NAME = "NetCache";

        String KEY = "key";
        String TIME = "time";
        String INFO = "info";


        //建立缓存数据库
        String tableCreate = "CREATE TABLE " +
                TABLE_NAME + " (" +
                // 缓存名称
                KEY + " VARCHAR PRIMARY KEY ," +
                // cache
                INFO + " VARCHAR ," +
                // 时间戳
                TIME + " TimeStamp NOT NULL DEFAULT (datetime('now','localtime'))" +
                ")";

        String tableSelectByKey = "select * from " + TABLE_NAME //
                + " where "//
                + KEY//
                + "=?";

        // 插入或替换
        String insertOrReplace = "INSERT OR REPLACE INTO " +
                TABLE_NAME + " (" +
                KEY + "," +
                INFO + "," +
                TIME +
                ") VALUES (?, ?, ?);";

    }

}
