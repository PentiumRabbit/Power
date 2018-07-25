
package com.zry.base.common.view.BitmapDisplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;

import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 * 图片居中的圆角矩形,根据imageview大小从中心裁切获取最适合的图片布局
 * zhaoruyang
 */
public class RoundedBitmapDisplayer implements BitmapDisplayer {
    private static final String TAG = "RoundedDrawable";
    protected final int cornerRadius;
    protected final int margin;
    protected Context context;

    public RoundedBitmapDisplayer(int cornerRadiusPixels, Context context) {
        this(cornerRadiusPixels, 0, context);
    }

    public RoundedBitmapDisplayer(int cornerRadiusPixels, int marginPixels, Context context) {
        this.cornerRadius = cornerRadiusPixels;
        this.margin = marginPixels;
        this.context = context;
    }

    @Override
    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
        if (!(imageAware instanceof ImageViewAware)) {
            throw new IllegalArgumentException("ImageAware should wrap ImageView. ImageViewAware is expected.");
        }

        imageAware.setImageDrawable(new RoundedDrawable(bitmap, cornerRadius, margin, context));
    }

    public static class RoundedDrawable extends BitmapDrawable {


        protected final float cornerRadius;
        protected final int margin;

        protected final RectF mRect = new RectF(),
                mBitmapRect;
        protected final BitmapShader bitmapShader;
        protected final Paint paint;
        private final Bitmap bitmap;

        public RoundedDrawable(Bitmap bitmap, int cornerRadius, int margin, Context context) {
            super(context.getResources(), bitmap);
            this.cornerRadius = cornerRadius;
            this.margin = margin;
            this.bitmap = bitmap;
            bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mBitmapRect = new RectF(margin, margin, bitmap.getWidth() - margin, bitmap.getHeight() - margin);

            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(bitmapShader);
        }

        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);
            mRect.set(margin, margin, bounds.width() - margin, bounds.height() - margin);
            // Resize the original bitmap to fit the new bound

            Matrix shaderMatrix = new Matrix();
            float scaleW = mRect.width() * 1.0f / bitmap.getWidth();
            float scaleH = mRect.height() * 1.0f / bitmap.getHeight();

            float scale = Math.max(scaleW, scaleH);

            float w = bitmap.getWidth() * scale;
            float h = bitmap.getHeight() * scale;

            float translateW = -(w - bounds.width()) / 2;
            float translateH = -(h - bounds.height()) / 2;

            // 让你不好好学线性代数
            /*后调用的pre操作先执行，而后调用的post操作则后执行。*/
            shaderMatrix.preTranslate(translateW, translateH);
            shaderMatrix.preScale(scale, scale);

//            shaderMatrix.setRectToRect(mBitmapRect, mRect, Matrix.ScaleToFit.CENTER);
            bitmapShader.setLocalMatrix(shaderMatrix);

        }


        @Override
        public void draw(Canvas canvas) {
            canvas.drawRoundRect(mRect, cornerRadius, cornerRadius, paint);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

        @Override
        public void setAlpha(int alpha) {
            paint.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            paint.setColorFilter(cf);
        }
    }
}