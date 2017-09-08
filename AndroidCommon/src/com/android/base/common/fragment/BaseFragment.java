package com.android.base.common.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.android.base.AppConstant;

/**
 * 新的基类,用于替换BaseFragment
 *
 * @author ----zhaoruyang----
 * @data: 2015/2/6
 */
public class BaseFragment extends Fragment implements IFragmentPro {
    private static final String TAG = "BaseNewFragment";
    private FragmentManager supportFragmentManager;
    private int fragmentStatus;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        supportFragmentManager = getActivity().getSupportFragmentManager();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragmentStatus(AppConstant.FragmentStatus.isCreated);
    }

    @Override
    public void onStart() {
        super.onStart();
        addFragmentStatus(AppConstant.FragmentStatus.isStarted);
    }

    @Override
    public void onStop() {
        removeFragmentStatus(AppConstant.FragmentStatus.isStarted);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        removeFragmentStatus(AppConstant.FragmentStatus.isCreated);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        addFragmentStatus(AppConstant.FragmentStatus.isResumed);
        super.onResume();

    }

    @Override
    public void onPause() {
        removeFragmentStatus(AppConstant.FragmentStatus.isResumed);
        super.onPause();

    }


    /****************************************************************************************************************************
     * Fragment 增强属性
     ****************************************************************************************************************************/

    @Override
    public int getFragmentUserStatus() {
        return AppConstant.FragmentUserStatus.NORMAL;
    }

    @Override
    public boolean hasFragmentStatus(int status) {
        return (fragmentStatus & status) == 1;
    }

    @Override
    public void addFragmentStatus(int status) {
        fragmentStatus |= status;
    }

    @Override
    public void removeFragmentStatus(int status) {
        fragmentStatus &= (~status);
    }


    @Override
    public void refreshFragment() {

    }

    @Override
    public void onFragmentPageShow() {

        addFragmentStatus(AppConstant.FragmentStatus.isShowed);
    }

    @Override
    public void onFragmentPageHide() {

        removeFragmentStatus(AppConstant.FragmentStatus.isShowed);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }


    /**
     * 替换Fragment
     *
     * @param layout_id 布局id
     * @param fragment  替换的fragment
     * @param needBack  是否添加到回退栈
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
