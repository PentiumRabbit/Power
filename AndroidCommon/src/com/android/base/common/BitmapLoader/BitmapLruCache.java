/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.android.base.common.BitmapLoader;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

/**
 * @author ----zhaoruyang----
 * @data: 2014/12/12
 */
class BitmapLruCache {
    private LruCache<String, Bitmap> mMemoryCache;
    //软引用
    private static final int SOFT_CACHE_CAPACITY = 40;
    private final static LinkedHashMap<String, SoftReference<Bitmap>> softBitmapCache =
            new LinkedHashMap<String, SoftReference<Bitmap>>(SOFT_CACHE_CAPACITY, 0.75f, true) {
                @Override
                public SoftReference<Bitmap> put(String key, SoftReference<Bitmap> value) {
                    return super.put(key, value);
                }

                @Override
                protected boolean removeEldestEntry(Entry<String, SoftReference<Bitmap>> eldest) {

                    if (size() > SOFT_CACHE_CAPACITY) {
                        eldest.getValue().get().recycle();
                        return true;
                    } else {
                        return false;
                    }

                }
            };

    public BitmapLruCache(int memorySize) {


        // Use 1/8th of the available memory for this memory cache.
        int cacheSize = 1024 * 1024 * memorySize / 8;
        if (mMemoryCache == null)
            mMemoryCache = new LruCache<String, Bitmap>(
                    cacheSize / 8) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    // 重写此方法来衡量每张图片的大小，默认返回图片数量。
                    return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
                }

                @Override
                protected void entryRemoved(boolean evicted, String key,
                                            Bitmap oldValue, Bitmap newValue) {
                    softBitmapCache.put(key, new SoftReference<Bitmap>(oldValue));


                }
            };
    }

    public void clearCache() {
        if (mMemoryCache != null) {
            if (mMemoryCache.size() > 0) {
                mMemoryCache.evictAll();
            }
            mMemoryCache = null;
        }
        if (softBitmapCache.size() > 0) {
            softBitmapCache.clear();
        }
    }

    // 添加bitmap到缓存
    public synchronized void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (TextUtils.isEmpty(key)) {
            return;
        }

        if (bitmap == null) {
            return;
        }
        if (mMemoryCache.get(key) != null) {
            return;
        }
        mMemoryCache.put(key, bitmap);

    }

    public synchronized Bitmap getBitmapFromMemCache(String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        Bitmap bm = mMemoryCache.get(key);
        if (bm != null) {
            return bm;
        }

        SoftReference<Bitmap> bitmapSoftReference = softBitmapCache.get(key);
        Bitmap bitmap = bitmapSoftReference.get();
        if (bitmap == null) {
            return null;
        }
        mMemoryCache.put(key, bitmap);
        return bitmap;
    }

    /**
     * 移除缓存
     *
     * @param key
     */
    public synchronized void removeImageCache(String key) {
        if (key == null) {
            return;
        }
        if (mMemoryCache == null) {
            return;
        }
        Bitmap bm = mMemoryCache.remove(key);

        if (bm == null) {
            return;
        }
        bm.recycle();
        bm = null;
    }
}
