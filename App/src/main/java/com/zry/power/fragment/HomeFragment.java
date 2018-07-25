package com.zry.power.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zry.base.common.base.BaseFragment;
import com.zry.base.common.handler.CommonHandler;
import com.zry.base.common.handler.IHandlerMessage;
import com.zry.base.common.utils.Logger;
import com.zry.power.R;
import com.zry.power.activity.SettingsActivity;
import com.zry.power.adapter.PackageAdapter;

import java.util.List;


/**
 * 主页
 *
 * @author ----zhaoruyang----
 * @data: 2015/6/12
 */
public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, IHandlerMessage, AdapterView.OnItemClickListener {
    private static final String TAG = "HomeFragment";

    ListView lvPacks;

    SwipeRefreshLayout wrlRefresh;
    private PackageAdapter packageAdapter;
    private CommonHandler<HomeFragment> handler;


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
        wrlRefresh = (SwipeRefreshLayout) view.findViewById(R.id.wrl_refresh);
        lvPacks = (ListView) view.findViewById(R.id.lv_packs);

        handler = new CommonHandler<>(this);
        return view;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.d(TAG, "onViewCreated  ");


        wrlRefresh.setOnRefreshListener(this);
        wrlRefresh.setColorSchemeResources(
                R.color.Tomato,
                R.color.Yellow,
                R.color.Turquoise,
                R.color.Teal);

        handler.sendEmptyMessage(2222);
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
        int currentApiVersion = Build.VERSION.SDK_INT;
        if (currentApiVersion >= Build.VERSION_CODES.LOLLIPOP) {
//
//            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,
//                    Pair.create(view1, "agreedName1"));
//
//            startActivity(intent,
//                    options.toBundle());
        } else {

        }

    }

    @Override
    public void handlerCallback(Message msg) {

    }
}
