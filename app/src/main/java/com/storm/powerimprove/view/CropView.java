package com.storm.powerimprove.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.android.base.common.value.ValueTAG;
import com.android.base.common.utils.ImageUtil;
import com.android.base.common.utils.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 可以放大缩小,平移,裁切的view
 *
 * @Description: CropView
 * @Author: ZhaoRuYang
 * @Update: ZhaoRuYang(2015-11-16 18:49)
 */
public class CropView extends ImageView {
    private static final String TAG = CropView.class.getSimpleName();
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
    private int dWidth;
    private int dHeight;

    public CropView(Context context) {
        super(context);
        init();
    }

    public CropView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CropView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    /**
     * 保存显示区域图片
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
        compressAndSaveBitmapToSDCard(newBitmap, path, 50);
//        bitmap.recycle();
        newBitmap.recycle();
        setDrawingCacheEnabled(false);
        return true;
    }

    //压缩且保存图片到SDCard
    private boolean compressAndSaveBitmapToSDCard(Bitmap rawBitmap, String saveFilePath, int quality) {
        Logger.i(ValueTAG.NONE, saveFilePath);
        File saveFile = new File(saveFilePath);

        boolean success = false;

        if (saveFile.exists()) {
            saveFile.delete();
        }

        try {

            success = saveFile.createNewFile();

            if (!success) {
                return false;
            }

            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);

            //imageBitmap.compress(format, quality, stream);
            //把位图的压缩信息写入到一个指定的输出流中
            //第一个参数format为压缩的格式
            //第二个参数quality为图像压缩比的值,0-100.0 意味着小尺寸压缩,100意味着高质量压缩
            //第三个参数stream为输出流
            rawBitmap.compress(Bitmap.CompressFormat.PNG, quality, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            success = true;
        } catch (IOException e) {
            success = false;
            Logger.e(TAG, e);
        }
        return success;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /** 通过与运算保留最后八位 MotionEvent.ACTION_MASK = 255 */
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            // 手指压下屏幕
            case MotionEvent.ACTION_DOWN:
                mode = MODE_DRAG;
                // 记录ImageView当前的移动位置
                currentMatrix.set(getImageMatrix());
                startPoint.set(event.getX(), event.getY());
                break;
            // 手指在屏幕上移动，改事件会被不断触发
            case MotionEvent.ACTION_MOVE:
                // 拖拉图片
                if (mode == MODE_DRAG) {
                    float dx = event.getX() - startPoint.x; // 得到x轴的移动距离
                    float dy = event.getY() - startPoint.y; // 得到x轴的移动距离
                    // 在没有移动之前的位置上进行移动
                    matrix.set(currentMatrix);
                    matrix.postTranslate(dx, dy);
                }
                // 放大缩小图片
                else if (mode == MODE_ZOOM) {
                    float endDis = distance(event);// 结束距离
                    if (endDis > 10f) { // 两个手指并拢在一起的时候像素大于10
                        float scale = endDis / startDis;// 得到缩放倍数
                        matrix.set(currentMatrix);
                        matrix.postScale(scale, scale, midPoint.x, midPoint.y);
                    }
                }
                break;
            // 手指离开屏幕
            case MotionEvent.ACTION_UP:
                // 当触点离开屏幕，但是屏幕上还有触点(手指)
            case MotionEvent.ACTION_POINTER_UP:
                mode = 0;
                break;
            // 当屏幕上已经有触点(手指)，再有一个触点压下屏幕
            case MotionEvent.ACTION_POINTER_DOWN:
                mode = MODE_ZOOM;
                /** 计算两个手指间的距离 */
                startDis = distance(event);
                /** 计算两个手指间的中间点 */
                if (startDis > 10f) { // 两个手指并拢在一起的时候像素大于10
                    midPoint = mid(event);
                    //记录当前ImageView的缩放倍数
                    currentMatrix.set(getImageMatrix());
                }
                break;
        }

        setImageMatrix(matrix);
        return true;
    }

    @Override
    public void setImageMatrix(Matrix matrix) {
        Matrix b = checkMatrix(matrix);

        super.setImageMatrix(b);


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Bitmap bitmap = ImageUtil.drawableToBitmap(getDrawable());

        dWidth = bitmap.getWidth();
        dHeight = bitmap.getHeight();


    }

    /**
     * 限定矩阵变化范围
     */
    private Matrix checkMatrix(Matrix matrix) {

        int width = getWidth();
        int height = getHeight();

        float baseScaleX = width*1f / dWidth;
        float baseScaleY = height*1f / dHeight;

        float baseScale = Math.max(baseScaleX, baseScaleY);



        float[] values = new float[9];
        matrix.getValues(values);

        float scaleX = values[0];
        float TranslateX = values[2];

        float scaleY = values[4];
        float translateY = values[5];
        float offY = dHeight * scaleY - height;
        float offX = dWidth * scaleX - width;

        Logger.d(ValueTAG.NONE, "offY : " + offY);
        Logger.d(ValueTAG.NONE, "offX : " + offX);
        Logger.d(ValueTAG.NONE, "TranslateX : " + TranslateX);
        Logger.d(ValueTAG.NONE, "translateY : " + translateY);

        if (offX < 0||offY < 0) {
            values[0] = baseScale;
            values[4] = baseScale;
        }
        if (offY < 0) {

        }

        if (translateY < -offY) {
            values[5] = -offY;
        }

        if (translateY > 0) {
            values[5] = 0;
        }

        if (TranslateX < -offX) {
            values[2] = -offX;
        }


        if (TranslateX > 0) {
            values[2] = 0;
        }

        matrix.setValues(values);
        return matrix;

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
