package com.zry.power.adapter;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zry.power.R;

import java.util.List;

/**
 * 包的适配器
 */
public class RecyclePackageAdapter extends RecyclerView.Adapter<RecyclePackageAdapter.Holder> {
    private static final String TAG = RecyclePackageAdapter.class.getSimpleName();

    private List<PackageInfo> packages;
    private Context context;

    public RecyclePackageAdapter(Context context) {
        this.context = context;
    }

    public void update(List<PackageInfo> packages) {
        this.packages = packages;
        notifyDataSetChanged();
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.item_list_home, parent,
                false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        PackageInfo packageInfo = packages.get(position);
        String name = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
        holder.tvName.setText(name);
    }

    @Override
    public int getItemCount() {
        if (packages == null) {
            return 0;
        }
        return packages.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView tvName;

        public Holder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

}
