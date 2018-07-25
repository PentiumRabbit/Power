package com.zry.power.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zry.power.R;


/**
 * 侧滑列表
 *
 * @author ----zhaoruyang----
 * @data: 2015/5/26
 */
public class MenuAdapter extends BaseAdapter {
    private static final String TAG = "MenuAdapter";
    private int menuArray[][];
    private Context context;

    public MenuAdapter(Context context) {
        this.context = context;
        menuArray = new int[][]{
                // 首页
                {R.string.menu_home, R.drawable.icon_menu_video},
                // 日志工具
                {R.string.menu_log, R.drawable.icon_menu_video},
                // 用于图片处理
                {R.string.menu_pic, R.drawable.icon_menu_video},
                // 建议
                {R.string.menu_suggest, R.drawable.icon_menu_feedback},
                // 建议
                {R.string.menu_nested, R.drawable.icon_menu_feedback},
                {R.string.menu_elastic_tray, R.drawable.icon_menu_feedback},
                // 自定义进度条
                {R.string.menu_progress, R.drawable.icon_menu_feedback},
                // 选项
                {R.string.menu_set, R.drawable.icon_menu_set}
        };


    }


    @Override
    public int getCount() {
        if (menuArray == null) {
            return 0;
        }
        return menuArray.length;
    }

    @Override
    public Object getItem(int position) {
        return menuArray[position];
    }

    @Override
    public long getItemId(int position) {
        return menuArray[position][0];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_list_home_menu, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        int visible = position == menuArray.length - 1 ? View.VISIBLE : View.GONE;

        Drawable drawable = context.getResources().getDrawable(menuArray[position][1]);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.tvName.setCompoundDrawables(drawable, null, null, null);
        }
        holder.tvName.setText(menuArray[position][0]);

        return convertView;
    }


    static class ViewHolder {
        private TextView tvName;

        ViewHolder(View view) {
            initViews(view);
        }

        private void initViews(View root) {
            tvName = (TextView) root.findViewById(R.id.tv_name);
        }
    }
}
