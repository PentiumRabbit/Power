package com.zry.power.fragment;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zry.power.R;
import com.zry.power.adapter.RecyclePackageAdapter;
import com.zry.power.view.NestedScrollImageContent;

import java.util.List;


public class NestedFragment extends LocalDialogFragment {


    RecyclerView viewList;

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

    }
}
