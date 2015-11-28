package com.storm.powerimprove.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.base.common.fragment.BaseFragment;
import com.android.base.common.value.ValueTAG;
import com.android.base.utils.Logger;
import com.storm.powerimprove.R;
import com.storm.powerimprove.view.CropView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 用于图片处理
 *
 * @Description: PicDealFragment
 * @Author: ZhaoRuYang
 * @Update: ZhaoRuYang(2015-11-16 19:12)
 */
public class PicDealFragment extends BaseFragment {
    private static final String TAG = PicDealFragment.class.getSimpleName();
    @InjectView(R.id.cv_pic)
    CropView cvPic;
    @InjectView(R.id.btn_save)
    Button btnSave;

    public static PicDealFragment newInstance() {
        return new PicDealFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pic_deal, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.btn_save)
    void clickPic(View view) {
        cvPic.save("");
        Snackbar.make(getView(), "保存图片", Snackbar.LENGTH_LONG).show();
        Logger.i(ValueTAG.NONE, "保存图片");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
