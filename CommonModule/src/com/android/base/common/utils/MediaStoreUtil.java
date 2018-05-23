package com.android.base.common.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;

/**
 * @author ----zhaoruyang----
 * @data: 2015/3/19
 */
public class MediaStoreUtil {
    private static final String TAG = "MediaStoreUtil";
    private static String[] audioColumns = new String[]{
            MediaStore.Audio.Media._ID ,
            MediaStore.Audio.Media.DISPLAY_NAME ,
            MediaStore.Audio.Media.TITLE ,
            MediaStore.Audio.Media.DURATION ,
            MediaStore.Audio.Media.ARTIST ,
            MediaStore.Audio.Media.ALBUM ,
            MediaStore.Audio.Media.YEAR ,
            MediaStore.Audio.Media.MIME_TYPE ,
            MediaStore.Audio.Media.SIZE ,
            MediaStore.Audio.Media.DATA
    };


    /**
     * 获取sd卡所有的音乐文件
     *
     * @return
     *
     * @throws Exception
     */
    public static ArrayList getAllSongs(Context context) {

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                audioColumns,
                MediaStore.Audio.Media.MIME_TYPE + "=? or "
                        + MediaStore.Audio.Media.MIME_TYPE + "=?",
                new String[]{"audio/mpeg" , "audio/x-ms-wma"}, null);


        while (cursor.moveToNext()) {

        }
        cursor.close();
        return null;
    }

}
