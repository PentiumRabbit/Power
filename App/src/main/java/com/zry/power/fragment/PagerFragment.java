package com.zry.power.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zry.base.common.base.BaseFragment;
import com.zry.power.R;




/**
 * @author ----zhaoruyang----
 * @data: 2015/6/16
 */
public class PagerFragment extends BaseFragment {
    private static final String TAG = "PagerFragment";

    TabLayout tlTitle;

    ViewPager vpPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager, null);
        vpPager = (ViewPager) view.findViewById(R.id.vp_pager);
        tlTitle = (TabLayout) view.findViewById(R.id.tl_title);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        ArrayList<TextView> tvs = new ArrayList<TextView>();
//        for (int i = 0; i < items.length; i++) {
//            TextView tv = new TextView(this);
//            tv.setText(items[i]);
//            LinearLayout.LayoutParams lp =
//                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            tv.setTextColor(Color.BLACK);
//            tv.setBackgroundColor(Color.WHITE);
//            tv.setGravity(Gravity.CENTER);
//            tv.setLayoutParams(lp);
//            tv.setTextSize(22);
//            tvs.add(tv);
//        }
//        tabLayout = (TabLayout) findViewById(R.id.tablayout);
//        tabLayout.setTabTextColors(Color.WHITE, Color.GRAY);//设置文本在选中和为选中时候的颜色
//        vp = (ViewPager) findViewById(R.id.vp);
//        adapter = new Adapter();
//        vp.setAdapter(adapter);
//        //用来设置tab的，同时也要覆写  PagerAdapter 的 CharSequence getPageTitle(int position) 方法，要不然 Tab 没有 title
//        tabLayout.setupWithViewPager(vp);
//        //关联 TabLayout viewpager
//        tabLayout.setTabsFromPagerAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
