package com.zry.base.common.view.BitmapDisplayer;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 * @author ----zhaoruyang----
 * @data: 2015/3/9
 */
public class RectangleSizeDisplayer implements BitmapDisplayer {
    private static final String TAG = "RectangleSizeDisplayer";
    private int wide;
    private int height;

    public RectangleSizeDisplayer(int wide, int heigh) {
        this.wide = wide;
        this.height = heigh;
    }


    @Override
    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
        if (!(imageAware instanceof ImageViewAware)) {
            throw new IllegalArgumentException("ImageAware should wrap ImageView. ImageViewAware is expected.");
        }
//        Bitmap mBitmap = ThumbnailUtils.extractThumbnail(bitmap, wide,height);
////        Bitmap mBitmap = Bitmap.createScaledBitmap(bitmap, wide,height, true);
//        imageAware.setImageBitmap(mBitmap);
        imageAware.setImageDrawable(new RectangleDrawable(bitmap,wide,height));
    }
}
