package com.storm.powerimprove.fragment;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.storm.powerimprove.R;
import com.storm.powerimprove.adapter.RecyclePackageAdapter;
import com.storm.powerimprove.view.NestedScrollImageContent;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NestedFragment extends LocalDialogFragment {


    @InjectView(R.id.view_list)
    RecyclerView viewList;
    @InjectView(R.id.v_root)
    NestedScrollImageContent vRoot;

    public NestedFragment() {
    }

    public static NestedFragment newInstance() {
        return new NestedFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nested, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclePackageAdapter adapter = new RecyclePackageAdapter(getContext());
        viewList.setAdapter(adapter);
        viewList.setLayoutManager(new LinearLayoutManager(getContext()));
        viewList.setHasFixedSize(true);
//        vRoot.setRecyclerView(viewList);
        List<PackageInfo> packages = getActivity().getPackageManager().getInstalledPackages(0);
        adapter.update(packages);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
