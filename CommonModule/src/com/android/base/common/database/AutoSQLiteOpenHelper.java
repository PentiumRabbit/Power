package com.android.base.common.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自动控制数据库关闭操作
 * <p/>
 * ZhaoRuYang
 * 7/11/16 10:51 AM
 */
public abstract class AutoSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = AutoSQLiteOpenHelper.class.getSimpleName();

    private AtomicInteger COUNTER = new AtomicInteger();

    public AutoSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public AutoSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        COUNTER.getAndIncrement();
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
