
package com.storm.powerimprove.widget.PullListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.base.utils.FormatUtil;
import com.storm.powerimprove.R;

public class PullListViewHeader extends LinearLayout {
    private LinearLayout container;

    private TextView tvHint;

    private int pullState = STATE_NORMAL;

    public final static int STATE_NORMAL = 0;

    public final static int STATE_READY = 1;

    public final static int STATE_REFRESHING = 2;

    public PullListViewHeader(Context context) {
        super(context);
        initView(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public PullListViewHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        // 初始情况，设置下拉刷新view高度为0
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        container = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.view_pull_header,
                null);
        addView(container, lp);
        setGravity(Gravity.BOTTOM);

        tvHint = (TextView) findViewById(R.id.text);
        tvHint.setPadding(0, FormatUtil.dip2px(context, 10), 0, 0);

    }

    private void measureView(View view) {
        AbsListView.LayoutParams lp = (AbsListView.LayoutParams) view.getLayoutParams();
        if (lp == null) {
            lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    AbsListView.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = AbsListView.getChildMeasureSpec(0, 0, lp.width);
        int childHeightSpec;
        if (lp.height > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        view.measure(childWidthSpec, childHeightSpec);
    }

    public void setState(int state) {
        if (state == pullState)
            return;
        switch (state) {
            case STATE_NORMAL:
                tvHint.setText(R.string.app_support);
                break;
            case STATE_READY:
                if (pullState != STATE_READY) {
                    tvHint.setText(R.string.app_support);
                }
                break;
            case STATE_REFRESHING:
                tvHint.setText(R.string.app_support);
                break;
            default:
        }
        pullState = state;
    }

    public void setHeight(int height) {
        if (height < 0)
            height = 0;
        LayoutParams lp = (LayoutParams) container.getLayoutParams();
        lp.height = height;
        container.setLayoutParams(lp);
    }

    public int getVisibleHeight() {
        return container.getHeight();
    }

}
