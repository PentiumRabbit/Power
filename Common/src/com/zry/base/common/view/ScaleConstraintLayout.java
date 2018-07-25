package com.zry.base.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;

import com.android.common.R;

/**
 * 比例宽高的控件
 * ZhaoRuYang
 * time : 17-9-19
 */

public class ScaleConstraintLayout extends ConstraintLayout {

    private int scaleW;
    private int scaleH;


    public ScaleConstraintLayout(Context context, AttributeSet attrs,
                                 int defStyle) {
        super(context, attrs, defStyle);
        obtainAttributes(context, attrs);
    }

    public ScaleConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaleConstraintLayout(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (scaleH != 0 && scaleW != 0) {
            int childWidthSize = getMeasuredWidth();
            // 期望固定宽高
            widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                    childWidthSize, View.MeasureSpec.EXACTLY);
            heightMeasureSpec = scaleH / scaleW * widthMeasureSpec;

        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }


    private void obtainAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ScaleLayout);
        scaleW = ta.getInt(R.styleable.ScaleLayout_scaleW, 0);
        scaleH = ta.getInt(R.styleable.ScaleLayout_scaleH, 0);
        ta.recycle();
    }

}