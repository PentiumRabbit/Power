package com.storm.powerimprove.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.storm.powerimprove.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author ----zhaoruyang----
 * @data: 2015/6/15
 */
public class PackageAdapter extends BaseAdapter {
    private static final String TAG = "PackageAdapter";

    private List<PackageInfo> packages;
    private Context context;

    public PackageAdapter(Context context) {
        this.context = context;
    }

    public void update(List<PackageInfo> packages) {
        this.packages = packages;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        if (packages == null) {
            return 0;
        }

        return packages.size();
    }

    @Override
    public Object getItem(int position) {
        return packages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_list_home, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PackageInfo packageInfo = packages.get(position);
        String name = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
        holder.tvName.setText(name);

        return convertView;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_list_home.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny , plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @InjectView(R.id.tv_name)
        TextView tvName;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
