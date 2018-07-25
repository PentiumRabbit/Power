/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.zry.base.common.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBCommonOpenHelper extends SQLiteOpenHelper {
	private final static String DB_NAME = "common.db";

	private DBCommonOpenHelper(Context context) {
		super(context, DB_NAME, null, 15);
	}

	private static volatile DBCommonOpenHelper instance = null;

	public static DBCommonOpenHelper getInstance(Context context) {
		// if already inited, no need to get lock everytime
		if (instance == null) {
			synchronized (DBCommonOpenHelper.class) {
				if (instance == null) {
					instance = new DBCommonOpenHelper(context);
				}
			}
		}

		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 盒子计数
		db.execSQL("CREATE TABLE boxcounting(" +
                "_id integer primary key autoincrement," +
                "log VARCHAR(1024))");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS boxcounting");
		onCreate(db);
	}
}
