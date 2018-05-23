
package com.storm.powerimprove.widget.PullListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.base.common.utils.FormatUtil;
import com.storm.powerimprove.R;

public class PullListViewFooter extends LinearLayout {
    public final static int STATE_NORMAL = 0;

    public final static int STATE_READY = 1;

    public final static int STATE_LOADING = 2;

    private TextView tvHint;

    private LinearLayout moreView;

    public PullListViewFooter(Context context) {
        super(context);
        initView(context);
    }

    public PullListViewFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void setState(int state) {

    }

    public void setBottomHeight(int height) {
        if (height < 0)
            return;
        LayoutParams lp = (LayoutParams) moreView.getLayoutParams();
        lp.height = height;
        moreView.setLayoutParams(lp);
    }

    public int getBottomHeight() {
        LayoutParams lp = (LayoutParams) moreView.getLayoutParams();
        return lp.height;
    }

    public void normal() {
        tvHint.setVisibility(View.VISIBLE);
    }

    public void loading() {
        tvHint.setVisibility(View.GONE);
    }

    private void initView(Context context) {
        moreView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.view_pull_header,
                null);
        addView(moreView);
        moreView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0));

        tvHint = (TextView) moreView.findViewById(R.id.text);
        tvHint.setPadding(0, FormatUtil.dip2px(context, 10), 0, 0);
        tvHint.setText(R.string.app_support);
    }

}
