package com.android.base.common.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * 新的基类,用于替换BaseFragment
 *
 * @author ----zhaoruyang----
 * @data: 2015/2/6
 */
public class BaseFragment extends Fragment {
    private static final String TAG = "BaseNewFragment";
    private FragmentManager supportFragmentManager;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        supportFragmentManager = getActivity().getSupportFragmentManager();
    }

    /**
     * 替换Fragment
     *
     * @param layout_id
     *         布局id
     * @param fragment
     *         替换的fragment
     * @param needBack
     *         是否添加到回退栈
     */
    protected void replaceFragment(int layout_id, Fragment fragment, boolean needBack) {
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        beginTransaction.replace(layout_id, fragment);
        if (needBack) {
            beginTransaction.addToBackStack(fragment.getClass().getName());
        }
        beginTransaction.commitAllowingStateLoss();

    }
}
