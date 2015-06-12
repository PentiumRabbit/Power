package com.storm.powerimprove.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.storm.powerimprove.R;


/**
 * 侧滑列表
 *
 * @author ----zhaoruyang----
 * @data: 2015/5/26
 */
public class MenuAdapter extends BaseAdapter {
    private static final String TAG = "MenuAdapter";
    private final String[] names;
    private final TypedArray images;
    private Context context;

    public MenuAdapter(Context context) {
        this.context = context;
        names = context.getResources().getStringArray(R.array.home_menu_name);
        images = context.getResources().obtainTypedArray(R.array.home_menu_icon);
    }


    @Override
    public int getCount() {
        if (names == null) {
            return 0;
        }
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
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

        int visible = position == names.length - 1 ? View.VISIBLE : View.GONE;

        Drawable drawable = images.getDrawable(position);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.tvName.setCompoundDrawables(drawable, null, null, null);
        }
        holder.tvName.setText(names[position]);

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
