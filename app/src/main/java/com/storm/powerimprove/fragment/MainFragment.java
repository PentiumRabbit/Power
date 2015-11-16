package com.storm.powerimprove.fragment;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.storm.powerimprove.R;
import com.storm.powerimprove.activity.MainActivity;
import com.storm.powerimprove.widget.MaterialProgress;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author ----zhaoruyang----
 * @data: 2015/6/11
 */
public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    @InjectView(R.id.section_label)
    TextView sectionLabel;
    @InjectView(R.id.til_name)
    TextInputLayout tilName;
    @InjectView(R.id.til_pwd)
    TextInputLayout tilPwd;
    @InjectView(R.id.fab_button)
    FloatingActionButton fabButton;
    @InjectView(R.id.switchcompat)
    SwitchCompat switchcompat;
    @InjectView(R.id.iv_loading)
    ImageView ivLoading;
    private int postion;


    // Default background for the progress spinner
    private static final int CIRCLE_BG_LIGHT = 0xFFFAFAFA;
    private MaterialProgress materialProgress;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MainFragment newInstance(int sectionNumber) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        postion = getArguments().getInt(ARG_SECTION_NUMBER);
        ((MainActivity) activity).onSectionAttached(
                postion);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<PackageInfo> packages = getActivity().getPackageManager().getInstalledPackages(0);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ButterKnife.inject(this, view);
        // On Lollipop, the action bar shadow is provided by default, so have to remove it explicitly
//            ((ActionBarActivity) getActivity()).getSupportActionBar().setElevation(0);
//            getActivity().supportInvalidateOptionsMenu();

        EditText et_content = tilName.getEditText();
        tilName.setHint("请输入用户名");
        tilName.setError("密码输入错啦！");
        tilName.setErrorEnabled(true);//当设置成false的时候 错误信息不显示 反之显示


        materialProgress = new MaterialProgress(getActivity(), ivLoading);
        materialProgress.setBackgroundColor(CIRCLE_BG_LIGHT);
        materialProgress.setColorSchemeColors(R.color.Tomato);
        ivLoading.setImageDrawable(materialProgress);
        materialProgress.setAlpha(255);
        materialProgress.start();

        switchcompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    materialProgress.start();
                } else {
                    materialProgress.stop();
                }
            }
        });

        fabButton.setRippleColor(Color.GRAY);//设置按下去的波纹颜色
        fabButton.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.ic_menu_add));//背景色


        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar
                        .make(view, "你好啊", Snackbar.LENGTH_LONG)
                        .setAction("delete", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainFragment.this.getActivity(), "delete", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (postion == 2) {
            inflater.inflate(R.menu.refresh, menu);
        } else if (postion == 3) {
            inflater.inflate(R.menu.issue_view, menu);
        }
//            super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.m_refresh:
                setRefreshActionButtonState(item, true);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    public void setRefreshActionButtonState(MenuItem item, boolean refreshing) {

        if (item != null) {
            if (refreshing) {
                MenuItemCompat.setActionView(item, R.layout.item_progress);
            } else {
                MenuItemCompat.setActionView(item, null);
            }
        }
    }

}
