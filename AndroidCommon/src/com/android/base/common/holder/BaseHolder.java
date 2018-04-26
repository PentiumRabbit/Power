package com.android.base.common.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.base.common.bean.From;
import com.android.common.R;

/**
 * ZhaoRuYang
 * time : 17-9-11
 */

public abstract class BaseHolder<DATA> extends RecyclerView.ViewHolder implements View.OnClickListener {

    private From from;
    private OnHolderClick onHolderClick;

    public BaseHolder(View itemView) {
        super(itemView);
        initView(itemView);
        itemView.setId(R.id.v_root);
        itemView.setOnClickListener(this);
    }

    public abstract void initView(View itemView);


    public void updateHolder(DataContainer container) {
        if (container == null) {
            return;
        }

        Object object = container.getData();

        if (object == null) {
            return;
        }

        update((DATA) object);
    }


    public abstract void update(DATA data);

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.v_root) {
            if (onHolderClick != null) {
                onHolderClick.onHolderClick();
            }
        }
    }
}
