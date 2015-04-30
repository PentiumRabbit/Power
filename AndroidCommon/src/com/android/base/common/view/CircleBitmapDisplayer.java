/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.android.base.common.view;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 * Created With Android Studio
 * zhaoruyang
 * Date 2014-07-27
 * Time 20:55
 * 显示原型图片的ImageLoader使用的显示器
 */
public class CircleBitmapDisplayer implements BitmapDisplayer {

    protected int margin;
    private Context context;

    public CircleBitmapDisplayer(Context context) {
        this(0, context);
    }

    public CircleBitmapDisplayer(int margin, Context context) {
        this.context = context;
        this.margin = margin;
    }

    @Override
    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
        if (!(imageAware instanceof ImageViewAware)) {
            throw new IllegalArgumentException("ImageAware should wrap ImageView. ImageViewAware is expected.");
        }

        imageAware.setImageDrawable(new CircleDrawable(bitmap, margin, context));
    }


}
