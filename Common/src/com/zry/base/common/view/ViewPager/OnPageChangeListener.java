package com.zry.base.common.view.ViewPager;

/**
 * 由于原生接口提供功能单一，不能满足定制需求，所以重新设计ViewPager 的接口回调，配合修改后的ViewPager使用
 * ZhaoRuYang
 * time : 17-9-12
 */

public interface OnPageChangeListener {


    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    /**
     * 动画之前选中
     *
     * @param position
     */
    public void onPageSelectedAnimPre(int position);

    /**
     * 动画之后选中
     *
     * @param position
     */
    public void onPageSelectedAnimAft(int position);

//    public void onPageUnSelectedAnimPre(int position);

    public void onPageUnSelectedAnimAft(int position);


    public void onPageScrollStateChanged(int state);
}
