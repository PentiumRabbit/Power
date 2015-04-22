/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.android.base.common.database.Dao;

import com.android.base.common.database.DBCommonOpenHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * 本地存储上报失败的计数
 *
 * @author qinwei
 * @version V1.0
 * @ClassName: BoxDao
 * @date 2014-5-4 下午6:45:24
 */
public class BoxDao {
    private static DBCommonOpenHelper helper;

    public BoxDao(Context context) {
        if (helper == null) {
            helper = DBCommonOpenHelper.getInstance(context);
        }
    }

    public synchronized void insert(String log) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("INSERT INTO boxcounting(log) VALUES(?)", new Object[]{log});
    }

    public void delete(String log) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("DELETE FROM boxcounting WHERE _id IN(SELECT _id FROM boxcounting WHERE log=? ORDER BY _id DESC LIMIT 0,1)",
                new Object[]{log});
    }

    public int queryCount() {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("select log FROM boxcounting", null);
        int count = c.getCount();
        c.close();
        db.close();
        return count;
    }

    public ArrayList<String> queryLog() {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT log FROM boxcounting", null);
        ArrayList<String> pathList = new ArrayList<String>();
        while (c.moveToNext())
            pathList.add(c.getString(0));
        c.close();
        return pathList;
    }

}
