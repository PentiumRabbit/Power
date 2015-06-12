package com.android.base.common.fragment;

import android.os.Bundle;

/**
 * @author ----zhaoruyang----
 * @data: 2015/2/6
 */
public class ExampleActivity extends BaseActivity implements FragmentCallback {
    private static final String TAG = "ExampleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

                /*替换新的fragment*/
        replaceFragment(11, ExampleFragment.newInstance(new Bundle()), true);

    }

    @Override
    public void onFragmentMessage(Bundle bundle) {

    }
}
