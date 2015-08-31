package com.storm.powerimprove.fragment;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
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
import com.storm.powerimprove.activity.SettingsActivity;
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
public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
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
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        ButterKnife.inject(this, view);
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
        lvPacks.setOnItemClickListener(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.reset(this);
    }

    @Override
    public void onRefresh() {
        wrlRefresh.setRefreshing(false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), SettingsActivity.class);

            /*获取当前系统的android版本号*/
        int currentApiVersion=Build.VERSION.SDK_INT;
        if (currentApiVersion>= Build.VERSION_CODES.LOLLIPOP){
//
//            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,
//                    Pair.create(view1, "agreedName1"));
//
//            startActivity(intent,
//                    options.toBundle());
        }else {

        }

    }
}
