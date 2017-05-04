package com.android.base.common.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by PentiumRabbit on 2017/4/13.
 */

public class CommonHolder<ITEM extends ItemHandler, VIEW extends ViewHandler> extends RecyclerView.ViewHolder {

    //ViewHandler 初始化操作
    private ViewHandler viewHandler;
    public CommonHolder(View itemView) {
        super(itemView);
    }




    public void update(ITEM t) {
        t.update();
    }


}
