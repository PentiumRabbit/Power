/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.android.base.common.utils;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.provider.MediaStore;

import com.android.base.common.value.ValueTAG;

/**
 * 缩略图工具
 * Created by zhaoruyang on 2014/12/5.
 */
public class ThumbUtil {
    /**
     * @param context
     *         上下文
     * @param cr
     *         内容提供者
     * @param Videopath
     *         视频路径
     *
     * @return Bitmap
     */
    public static Bitmap getVideoThumbnail(Context context, ContentResolver cr, String Videopath) {
        ContentResolver testcr = context.getContentResolver();
        String[] projection = {MediaStore.Video.Media.DATA, MediaStore.Video.Media._ID,};
        String whereClause = MediaStore.Video.Media.DATA + " = '" + Videopath + "'";
        Cursor cursor = testcr.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, whereClause,
                null, null);
        int _id = 0;
        String videoPath = "";
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }
        if (cursor.moveToFirst()) {

            int _idColumn = cursor.getColumnIndex(MediaStore.Video.Media._ID);
            int _dataColumn = cursor.getColumnIndex(MediaStore.Video.Media.DATA);
            do {
                _id = cursor.getInt(_idColumn);
                videoPath = cursor.getString(_dataColumn);
            } while (cursor.moveToNext());
        }
        cursor.close();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return MediaStore.Video.Thumbnails.getThumbnail(cr, _id, MediaStore.Images.Thumbnails.MINI_KIND,
                options);
    }


    /**
     * 系统方法获取图片缩略图
     *
     * @param context
     *         上下文
     * @param cr
     *         内容提供者
     * @param imagePath
     *         图片路径
     *
     * @return Bitmap
     */
    public static Bitmap getImageThumbnail(Context context, ContentResolver cr, String imagePath) {
        ContentResolver testcr = context.getContentResolver();
        String[] projection = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID,};
        String whereClause = MediaStore.Images.Media.DATA + " = '" + imagePath + "'";
        Cursor cursor = testcr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, whereClause,
                null, null);
        int _id = 0;
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }
        if (cursor.moveToFirst()) {

            int _idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID);
            int _dataColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA);

            do {
                _id = cursor.getInt(_idColumn);
                imagePath = cursor.getString(_dataColumn);
            } while (cursor.moveToNext());
        }
        cursor.close();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        return MediaStore.Images.Thumbnails.getThumbnail(cr, _id, MediaStore.Images.Thumbnails.MINI_KIND,
                options);
    }

    /**
     * 根据指定的图像路径和大小来获取缩略图
     * 此方法有两点好处：
     * 1. 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
     * 第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图。
     * 2. 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本的新工具ThumbnailUtils，使
     * 用这个工具生成的图像不会被拉伸。
     *
     * @param imagePath
     *         图像的路径
     * @param width
     *         指定输出图像的宽度
     * @param height
     *         指定输出图像的高度
     *
     * @return 生成的缩略图
     */
    private static Bitmap getImageThumbnail(String imagePath, int width, int height) {
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高，注意此处的bitmap为null
        BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false; // 设为 false
        // 计算缩放比
        int h = options.outHeight;
        int w = options.outWidth;
        int beWidth = w / width;
        int beHeight = h / height;
        int be;
        if (beWidth < beHeight) {
            be = beWidth;
        } else {
            be = beHeight;
        }
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        // 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    /**
     * 获取视频的缩略图
     * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
     * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
     *
     * @param videoPath
     *         视频的路径
     * @param width
     *         指定输出视频缩略图的宽度
     * @param height
     *         指定输出视频缩略图的高度度
     * @param kind
     *         参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
     *         其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
     *
     * @return 指定大小的视频缩略图
     */
    private static Bitmap getVideoThumbnail(String videoPath, int width, int height,
                                            int kind) {
        Bitmap bitmap;
        // 获取视频的缩略图
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    private static Bitmap createAlbumThumbnail(String filePath, int width, int height) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {

            retriever.setDataSource(filePath);
            byte[] art = retriever.getEmbeddedPicture();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            bitmap = BitmapFactory.decodeByteArray(art, 0, art.length, options);

            options.inJustDecodeBounds = false; // 设为 false
            // 计算缩放比
            int h = options.outHeight;
            int w = options.outWidth;
            int beWidth = w / width;
            int beHeight = h / height;
            int be;
            if (beWidth < beHeight) {
                be = beWidth;
            } else {
                be = beHeight;
            }
            if (be <= 0) {
                be = 1;
            }
            options.inSampleSize = be;
            // 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
            bitmap = BitmapFactory.decodeByteArray(art, 0, art.length, options);
            // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        } catch (RuntimeException ex) {
            Logger.e(ValueTAG.EXCEPTION, "", ex);
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        return bitmap;
    }

    public static Bitmap getThumbnailApk(String absPath, int width, int height) {
//        PackageManager pm = context.getPackageManager();
//        PackageInfo pkgInfo = pm.getPackageArchiveInfo(absPath,
//                PackageManager.GET_ACTIVITIES);
//        if (pkgInfo != null) {
//            ApplicationInfo appInfo = pkgInfo.applicationInfo;
//            /* 必须加这两句，不然下面icon获取是default icon而不是应用包的icon */
//            appInfo.sourceDir = absPath;
//            appInfo.publicSourceDir = absPath;
//            BitmapDrawable bd = (BitmapDrawable) pm.getApplicationIcon(appInfo);
//            return ThumbnailUtils.extractThumbnail(bd.getBitmap(), width, height,
//                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
//
//
//        }
        return null;
    }

    public static Bitmap getThumb(String path, int width, int height, int kind) {
        int i = MediaUtil.classifyMedia(path);
        if (i == MediaUtil.TYPE_MEDIA_PIC) {
            return getImageThumbnail(path, width, height);
        } else if (i == MediaUtil.TYPE_MEDIA_VIDEO) {
            return getVideoThumbnail(path, width, height, kind);
        } else if (i == MediaUtil.TYPE_MEDIA_MUSIC) {
            return createAlbumThumbnail(path, width, height);
        } else if (i == MediaUtil.TYPE_MEDIA_APK) {
            return getThumbnailApk(path, width, height);
        }

        return null;
    }

}
