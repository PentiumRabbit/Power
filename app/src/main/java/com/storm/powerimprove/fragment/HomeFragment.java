package com.storm.powerimprove.fragment;

import android.annotation.TargetApi;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.base.utils.LogUtil;
import com.storm.powerimprove.R;
import com.storm.powerimprove.adapter.PackageAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 主页
 *
 * @author ----zhaoruyang----
 * @data: 2015/6/12
 */
public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "HomeFragment";
    @InjectView(R.id.lv_packs)
    ListView lvPacks;
    @InjectView(R.id.wrl_refresh)
    SwipeRefreshLayout wrlRefresh;
    private PackageAdapter packageAdapter;


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.d(TAG, "onViewCreated  ");
        ButterKnife.inject(this, view);

        wrlRefresh.setOnRefreshListener(this);
        wrlRefresh.setColorSchemeResources(
                R.color.Tomato,
                R.color.Yellow,
                R.color.Turquoise,
                R.color.Teal);

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDate();
    }

    private void initDate() {
        List<PackageInfo> packages = getActivity().getPackageManager().getInstalledPackages(0);
        packageAdapter = new PackageAdapter(getActivity());
        lvPacks.setAdapter(packageAdapter);
        packageAdapter.update(packages);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onRefresh() {
        wrlRefresh.setRefreshing(false);
    }
}
