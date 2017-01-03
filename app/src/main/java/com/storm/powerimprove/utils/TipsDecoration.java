package com.storm.powerimprove.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.widget.ScrollerCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * tips显示
 * <p>
 * ZhaoRuYang
 * 10/13/16 10:39 AM
 */
public class TipsDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = TipsDecoration.class.getSimpleName();
    private ScrollerCompat scrollerCompat;
    private Paint rectPaint;
    private Paint textPaint;
    private boolean startAnim = true;
    private String text = "更新完毕";
    private int position = 1;

    public TipsDecoration(Context context) {
        scrollerCompat = ScrollerCompat.create(context, new OvershootInterpolator());
        initRectPaint();
        initTextPaint();
    }


    public TipsDecoration setText(String text) {
        this.text = text;
        return this;
    }

    public TipsDecoration setPosition(int position) {
        this.position = position;
        return this;
    }

    /**
     * 初始化文字画笔
     */
    private void initTextPaint() {
        textPaint = new Paint();
        textPaint.setColor(Color.parseColor("#1e5dab"));
        textPaint.setStrokeWidth(3);
        // 水平居中
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(56);
    }

    /**
     * 初始化边框画笔
     */
    private void initRectPaint() {
        rectPaint = new Paint();
        rectPaint.setColor(Color.parseColor("#e6c6daeb"));
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

    }

    @Override
    public void onDrawOver(Canvas canvas, final RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);

        if (startAnim) {
            scrollerCompat.startScroll(0, 1, 0, 1000, 500);
            startAnim = false;
            parent.postDelayed(new Runnable() {
                @Override
                public void run() {
                    parent.removeItemDecoration(TipsDecoration.this);
                }
            }, 3000);
        }

        boolean canScroll = scrollerCompat.computeScrollOffset();

        float scale = 1;
        if (canScroll) {
            int currY = scrollerCompat.getCurrY();
            scale = currY / 1000f;
        }

        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            if (parent.getChildAdapterPosition(view) == position) {
                Rect targetRect = new Rect(parent.getLeft(), view.getTop(), parent.getRight(), view.getTop() + 128);
                int saveCount = canvas.save();
                canvas.drawRect(targetRect, rectPaint);
                canvas.restoreToCount(saveCount);
                saveCount = canvas.save();
                canvas.scale(scale, scale, targetRect.centerX(), targetRect.centerY());
                Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
                int baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
                canvas.drawText(text, targetRect.centerX(), baseline, textPaint);
                canvas.restoreToCount(saveCount);
                break;
            }
        }

        if (canScroll) {
            parent.postInvalidateDelayed(30);
        }
    }
}
