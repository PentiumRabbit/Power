package com.storm.powerimprove.view;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 重写ImageView
 *
 * @Description: CropImageView
 * @Author: ZhaoRuYang
 * @Update: ZhaoRuYang(2015-11-18 18:17)
 */
public class CropImageView extends View {
    private static final String TAG = CropImageView.class.getSimpleName();

    private Uri mUri;
    private int mResource = 0;
    private Matrix mMatrix;
    private boolean mAdjustViewBounds = false;
    private int mMaxWidth = Integer.MAX_VALUE;
    private int mMaxHeight = Integer.MAX_VALUE;

    // these are applied to the drawable

    private Drawable mDrawable = null;

    private int mDrawableWidth;
    private int mDrawableHeight;
    private Matrix mDrawMatrix = null;

    private Context context;

    public CropImageView(Context context) {
        super(context);
        initImageView();
    }

    public CropImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initImageView();
    }

    public CropImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initImageView();
    }


    private void initImageView() {
        mMatrix = new Matrix();
        context = getContext();

    }

    @Override
    public void invalidateDrawable(Drawable dr) {
        if (dr == mDrawable) {
            /* we invalidate the whole view in this case because it's very
             * hard to know where the drawable actually is. This is made
             * complicated because of the offsets and transformations that
             * can be applied. In theory we could get the drawable's bounds
             * and run them through the transformation and offsets, but this
             * is probably not worth the effort.
             */
            invalidate();
        } else {
            super.invalidateDrawable(dr);
        }
    }

    public void setImageResource(int resId) {
        if (mUri != null || mResource != resId) {
            final int oldWidth = mDrawableWidth;
            final int oldHeight = mDrawableHeight;

            updateDrawable(null);
            mResource = resId;
            mUri = null;

            resolveUri();

            if (oldWidth != mDrawableWidth || oldHeight != mDrawableHeight) {
                requestLayout();
            }
            invalidate();
        }
    }

    public void setImageURI(Uri uri) {
        if (mResource != 0 ||
                (mUri != uri &&
                        (uri == null || mUri == null || !uri.equals(mUri)))) {
            updateDrawable(null);
            mResource = 0;
            mUri = uri;

            final int oldWidth = mDrawableWidth;
            final int oldHeight = mDrawableHeight;

            resolveUri();

            if (oldWidth != mDrawableWidth || oldHeight != mDrawableHeight) {
                requestLayout();
            }
            invalidate();
        }
    }

    public void setImageBitmap(Bitmap bm) {
        // if this is used frequently, may handle bitmaps explicitly
        // to reduce the intermediate drawable object
        setImageDrawable(new BitmapDrawable(context.getResources(), bm));
    }

    public void setImageDrawable(Drawable drawable) {
        if (mDrawable != drawable) {
            mResource = 0;
            mUri = null;

            final int oldWidth = mDrawableWidth;
            final int oldHeight = mDrawableHeight;


            updateDrawable(drawable);

            if (oldWidth != mDrawableWidth || oldHeight != mDrawableHeight) {
                requestLayout();
            }
            invalidate();
        }
    }


    private void resolveUri() {
        if (mDrawable != null) {
            return;
        }

        Resources rsrc = getResources();
        if (rsrc == null) {
            return;
        }

        Drawable d = null;

        if (mResource != 0) {
            try {
                d = context.getResources().getDrawable(mResource);
            } catch (Exception e) {
                Log.w("ImageView", "Unable to find resource: " + mResource, e);
                // Don't try again.
                mUri = null;
            }
        } else if (mUri != null) {
            String scheme = mUri.getScheme();
            if (ContentResolver.SCHEME_CONTENT.equals(scheme)
                    || ContentResolver.SCHEME_FILE.equals(scheme)) {
                InputStream stream = null;
                try {
                    stream = context.getContentResolver().openInputStream(mUri);
                    d = Drawable.createFromStream(stream, null);
                } catch (Exception e) {
                    Log.w("ImageView", "Unable to open content: " + mUri, e);
                } finally {
                    if (stream != null) {
                        try {
                            stream.close();
                        } catch (IOException e) {
                            Log.w("ImageView", "Unable to close content: " + mUri, e);
                        }
                    }
                }
            } else {
                d = Drawable.createFromPath(mUri.toString());
            }

            if (d == null) {
                // Don't try again.
                mUri = null;
            }
        } else {
            return;
        }

        updateDrawable(d);
    }

    private void updateDrawable(Drawable d) {
        if (mDrawable != null) {
            mDrawable.setCallback(null);
            unscheduleDrawable(mDrawable);
        }

        mDrawable = d;

        if (d != null) {
            d.setCallback(this);
            if (d.isStateful()) {
                d.setState(getDrawableState());
            }
            d.setVisible(getVisibility() == VISIBLE, true);
            mDrawableWidth = d.getIntrinsicWidth();
            mDrawableHeight = d.getIntrinsicHeight();
            mMatrix.reset();
            configureBounds();
        } else {
            mDrawableWidth = mDrawableHeight = -1;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        resolveUri();
        int w;
        int h;
        // Desired aspect ratio of the view's contents (not including padding)
        float desiredAspect = 0.0f;

        // We are allowed to change the view's width
        boolean resizeWidth = false;

        // We are allowed to change the view's height
        boolean resizeHeight = false;

        final int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        if (mDrawable == null) {
            // If no drawable, its intrinsic size is 0.
            mDrawableWidth = -1;
            mDrawableHeight = -1;
            w = h = 0;
        } else {
            w = mDrawableWidth;
            h = mDrawableHeight;
            if (w <= 0) w = 1;
            if (h <= 0) h = 1;

            // We are supposed to adjust view bounds to match the aspect
            // ratio of our drawable. See if that is possible.
            if (mAdjustViewBounds) {
                resizeWidth = widthSpecMode != MeasureSpec.EXACTLY;
                resizeHeight = heightSpecMode != MeasureSpec.EXACTLY;

                desiredAspect = (float) w / (float) h;
            }
        }

        int widthSize;
        int heightSize;

        if (resizeWidth || resizeHeight) {
            /* If we get here, it means we want to resize to match the
                drawables aspect ratio, and we have the freedom to change at
                least one dimension.
            */

            // Get the max possible width given our constraints
            widthSize = resolveAdjustedSize(w, mMaxWidth, widthMeasureSpec);

            // Get the max possible height given our constraints
            heightSize = resolveAdjustedSize(h, mMaxHeight, heightMeasureSpec);

            if (desiredAspect != 0.0f) {
                // See what our actual aspect ratio is
                float actualAspect = (float) (widthSize) /
                        (heightSize);

                if (Math.abs(actualAspect - desiredAspect) > 0.0000001) {

                    boolean done = false;

                    // Try adjusting width to be proportional to height
                    if (resizeWidth) {
                        int newWidth = (int) (desiredAspect * (heightSize));

                        // Allow the width to outgrow its original estimate if height is fixed.
                        if (!resizeHeight) {
                            widthSize = resolveAdjustedSize(newWidth, mMaxWidth, widthMeasureSpec);
                        }

                        if (newWidth <= widthSize) {
                            widthSize = newWidth;
                            done = true;
                        }
                    }

                    // Try adjusting height to be proportional to width
                    if (!done && resizeHeight) {
                        int newHeight = (int) ((widthSize) / desiredAspect);

                        // Allow the height to outgrow its original estimate if width is fixed.
                        if (!resizeWidth) {
                            heightSize = resolveAdjustedSize(newHeight, mMaxHeight,
                                    heightMeasureSpec);
                        }

                        if (newHeight <= heightSize) {
                            heightSize = newHeight;
                        }
                    }
                }
            }
        } else {


            w = Math.max(w, getSuggestedMinimumWidth());
            h = Math.max(h, getSuggestedMinimumHeight());

            widthSize = resolveSizeAndState(w, widthMeasureSpec, 0);
            heightSize = resolveSizeAndState(h, heightMeasureSpec, 0);
        }

        setMeasuredDimension(widthSize, heightSize);
    }

    private int resolveAdjustedSize(int desiredSize, int maxSize,
                                    int measureSpec) {
        int result = desiredSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                /* Parent says we can be as big as we want. Just don't be larger
                   than max size imposed on ourselves.
                */
                result = Math.min(desiredSize, maxSize);
                break;
            case MeasureSpec.AT_MOST:
                // Parent says we can be as big as we want, up to specSize.
                // Don't be larger than specSize, and don't be larger than
                // the max size imposed on ourselves.
                result = Math.min(Math.min(desiredSize, specSize), maxSize);
                break;
            case MeasureSpec.EXACTLY:
                // No choice. Do what we are told.
                result = specSize;
                break;
        }
        return result;
    }


    /**
     * 限定矩阵变化范围
     */
    private Matrix checkMatrix(Matrix matrix) {

        int width = getWidth();
        int height = getHeight();

        float baseScaleX = width * 1f / mDrawableWidth;
        float baseScaleY = height * 1f / mDrawableHeight;

        float baseScale = Math.max(baseScaleX, baseScaleY);


        float[] values = new float[9];
        matrix.getValues(values);

        float scaleX = values[0];
        float TranslateX = values[2];

        float scaleY = values[4];
        float translateY = values[5];
        float offY = mDrawableHeight * scaleY - height;
        float offX = mDrawableWidth * scaleX - width;

        if (offX <= 0 || offY <= 0) {
            values[0] = baseScale;
            values[4] = baseScale;
        }

        if (translateY <= -offY) {
            values[5] = -offY;
        }

        if (translateY >= 0) {
            values[5] = 0;
        }

        if (TranslateX <= -offX) {
            values[2] = -offX;
        }


        if (TranslateX >= 0) {
            values[2] = 0;
        }

        matrix.setValues(values);
        return matrix;

    }

    private void configureBounds() {
        if (mDrawable == null) {
            return;
        }
        int width = getWidth();
        int height = getHeight();

        mMatrix.set(checkMatrix(mMatrix));

        if (mDrawableWidth <= 0 || mDrawableWidth <= 0) {
            /* If the drawable has no intrinsic size, or we're told to
                scaletofit, then we just fill our entire view.
            */
            mDrawable.setBounds(0, 0, width, height);
            mDrawMatrix = null;
        } else {
            // We need to do the scaling ourself, so have the drawable
            // use its native size.
            mDrawable.setBounds(0, 0, mDrawableWidth, mDrawableHeight);
            mDrawMatrix = mMatrix;

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mDrawable == null) {
            return; // couldn't resolve the URI
        }

        if (mDrawableWidth == 0 || mDrawableHeight == 0) {
            return;     // nothing to draw (empty bounds)
        }

        if (mDrawMatrix == null) {
            mDrawable.draw(canvas);
        } else {
            int saveCount = canvas.getSaveCount();
            canvas.save();
            if (mDrawMatrix != null) {
                canvas.concat(mDrawMatrix);
            }
            mDrawable.draw(canvas);
            canvas.restoreToCount(saveCount);
        }
    }


    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (mDrawable != null) {
            mDrawable.setVisible(visibility == VISIBLE, false);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mDrawable != null) {
            mDrawable.setVisible(getVisibility() == VISIBLE, false);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mDrawable != null) {
            mDrawable.setVisible(false, false);
        }
    }

    /**
     * 记录是拖拉照片模式还是放大缩小照片模式
     */
    private int mode = 0;// 初始状态
    /**
     * 拖拉照片模式
     */
    private static final int MODE_DRAG = 1;
    /**
     * 放大缩小照片模式
     */
    private static final int MODE_ZOOM = 2;

    /**
     * 用于记录开始时候的坐标位置
     */
    private PointF startPoint = new PointF();
    /**
     * 用于记录拖拉图片移动的坐标位置
     */
    private Matrix matrix = new Matrix();
    /**
     * 用于记录图片要进行拖拉时候的坐标位置
     */
    private Matrix currentMatrix = new Matrix();

    /**
     * 两个手指的开始距离
     */
    private float startDis;
    /**
     * 两个手指的中间点
     */
    private PointF midPoint;


    /**
     * 保存显示区域图片(在异步线程中操作)
     *
     * @param path
     * @return
     */
    public boolean save(String path) {
//        Bitmap bitmap = ImageUtil.drawableToBitmap(getDrawable());
//        int width = getWidth() > bitmap.getWidth() ? bitmap.getWidth() : getWidth();
//        int height = getHeight() > bitmap.getHeight() ? bitmap.getHeight() : getWidth();
//        Logger.d(ValueTAG.NONE, "save : " + matrix.toShortString());
//
//        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//        RectF mBitmapRect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
//
//        bitmapShader.setLocalMatrix(matrix);
//        Paint paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setShader(bitmapShader);
        //压缩后图片的宽和高以及kB大小均会变化
//        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, getImageMatrix(), true);
//        Canvas canvas = new Canvas(bitmap);
//        Rect dstRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getWidth());
//        Rect srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getWidth());
//        canvas.drawBitmap(bitmap,matrix, paint);

        setDrawingCacheEnabled(true);
        Bitmap newBitmap = getDrawingCache();
        compressAndSaveBitmapToSDCard(newBitmap, path);
//        bitmap.recycle();
        newBitmap.recycle();
        setDrawingCacheEnabled(false);
        return true;
    }

    //压缩且保存图片到SDCard
    private boolean compressAndSaveBitmapToSDCard(Bitmap rawBitmap, String saveFilePath) {
        File saveFile = new File(saveFilePath);
        boolean success = false;

        if (rawBitmap == null || TextUtils.isEmpty(saveFilePath)) {
            return success;
        }

        if (saveFile.exists()) {
            saveFile.delete();
        }
        try {
            success = saveFile.createNewFile();
            if (!success) {
                return false;
            }
            int size =1024;

            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            rawBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//如果签名是png的话，则不管quality是多少，都不会进行质量的压缩
            int quality = 100;
            while (baos.toByteArray().length / 1024f > size) {
                quality = quality - 4;// 每次都减少4
                baos.reset();// 重置baos即清空baos
                if (quality <= 0) {
                    break;
                }
                rawBitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            }
            baos.writeTo(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            success = true;
        } catch (IOException e) {
            success = false;
        }
        return success;
    }


    public int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /** 通过与运算保留最后八位 MotionEvent.ACTION_MASK = 255 */
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mode = MODE_DRAG;
                currentMatrix.set(mMatrix);
                matrix.set(mMatrix);
                startPoint.set(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == MODE_DRAG) {
                    float dx = event.getX() - startPoint.x; // 得到x轴的移动距离
                    float dy = event.getY() - startPoint.y; // 得到x轴的移动距离
                    matrix.set(currentMatrix);
                    matrix.postTranslate(dx, dy);
                } else if (mode == MODE_ZOOM) {
                    float endDis = distance(event);// 结束距离
                    if (endDis > 10f) { // 两个手指并拢在一起的时候像素大于10
                        float scale = endDis / startDis;// 得到缩放倍数
                        matrix.set(currentMatrix);
                        matrix.postScale(scale, scale, midPoint.x, midPoint.y);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = 0;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mode = MODE_ZOOM;
                startDis = distance(event);
                if (startDis > 10f) {
                    midPoint = mid(event);
                    currentMatrix.set(mDrawMatrix);
                }
                break;
        }

        setImageMatrix(matrix);
        return true;
    }


    public void setImageMatrix(Matrix m) {

        if (m == null || m.equals(mMatrix)) {
            return;
        }

        mMatrix.set(m);
        configureBounds();
        invalidate();
    }


    /**
     * 将Drawable转化为Bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
                .getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }


    /**
     * 计算两个手指间的距离
     */
    private float distance(MotionEvent event) {
        float dx = event.getX(1) - event.getX(0);
        float dy = event.getY(1) - event.getY(0);
        /** 使用勾股定理返回两点之间的距离 */
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * 计算两个手指间的中间点
     */
    private PointF mid(MotionEvent event) {
        float midX = (event.getX(1) + event.getX(0)) / 2;
        float midY = (event.getY(1) + event.getY(0)) / 2;
        return new PointF(midX, midY);
    }

}
