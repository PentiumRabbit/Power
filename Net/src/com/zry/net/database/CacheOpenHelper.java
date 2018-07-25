package com.zry.net.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 网络数据缓存
 *
 * @author ----zhaoruyang----
 * @data: 2014/12/26
 */
public class CacheOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = CacheOpenHelper.class.getSimpleName();
    private static AtomicInteger COUNTER = new AtomicInteger();

    public CacheOpenHelper(Context context) {
        super(context, CacheConst.DB_NAME, null, CacheConst.DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        onCreateAllTables(db);
    }

    private void onCreateAllTables(SQLiteDatabase db) {
        db.execSQL(CacheConst.NetCache.tableCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        COUNTER.incrementAndGet();
        return super.getReadableDatabase();
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        COUNTER.getAndIncrement();
        return super.getWritableDatabase();
    }


    @Override
    public synchronized void close() {
        int i = COUNTER.decrementAndGet();
        if (i > 0) {
            // 当打开链接数超过1时，不关闭
            return;
        }
        super.close();
    }
}
