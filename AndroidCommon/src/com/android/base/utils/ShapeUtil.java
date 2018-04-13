package com.android.base.utils;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;

/**
 * ZhaoRuYang
 * time : 17-9-22
 */

public class ShapeUtil {

    public static void setBackground(View view) {
        int strokeWidth = 5; // 3dp 边框宽度
        int roundRadius = 15; // 8dp 圆角半径
        int strokeColor = Color.parseColor("#2E3135");//边框颜色
        int fillColor = Color.parseColor("#DFDFE0");//内部填充颜色

        // 但是如果想设置Gradient的渐变色该咋办呢
        int colors[] = {0xff255779, 0xff3e7492, 0xffa6c0cd};//分别为开始颜色，中间夜色，结束颜色

        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(gd);
        } else {
            view.setBackground(gd);
        }

    }

}
