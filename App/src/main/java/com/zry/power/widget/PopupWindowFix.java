package com.zry.power.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.widget.PopupWindow;

/**
 * @author : ZhaoRuYang
 * @date : 2018/5/17
 */
public class PopupWindowFix extends PopupWindow {


    public PopupWindowFix(Context context) {
        super(context);
    }

    @Override
    public void showAsDropDown(View anchor) {

        // PopupWindow之踩坑(2)showAtLocation与showAsDropDown问题(https://www.jianshu.com/p/12f53c931eda)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);// 以屏幕 左上角 为参考系的
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;  //屏幕高度减去 anchor 的 bottom
            setHeight(h);// 重新设置PopupWindow高度
        }
        super.showAsDropDown(anchor);

    }

}
