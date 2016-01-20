package com.storm.powerimprove.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.base.utils.Logger;
import com.android.base.utils.ScreenUtil;
import com.storm.powerimprove.R;
import com.storm.powerimprove.dialog.ExitDialog;
import com.storm.powerimprove.fragment.HomeFragment;
import com.storm.powerimprove.fragment.LogRecordFragment;
import com.storm.powerimprove.fragment.MainFragment;
import com.storm.powerimprove.fragment.NavigationDrawerFragment;
import com.storm.powerimprove.fragment.NestedFragment;
import com.storm.powerimprove.fragment.PicDealFragment;


/**
 * 主界面
 */
public class MainActivity extends LocalDialogActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static final String TAG = MainActivity.class.getSimpleName();
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private long[] hits = new long[2];

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        showSystemUI();
    }

    @Override
    protected void updateTheme() {

    }

    @Override
    protected void initView() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            int statusBarHeight = ScreenUtil.getStatusBarHeight(this.getBaseContext());
            Logger.i(TAG, "statusBarHeight : " + statusBarHeight);
            findViewById(R.id.toolbar).setPadding(0, statusBarHeight, 0, 0);
        }
        Logger.d("开始");
    }

    @Override
    protected void initDate() {

    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window                     win       = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int                  bits      = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment        fragment        = MainFragment.newInstance(position + 1);
        if (position == R.string.menu_suggest) {
            fragment = HomeFragment.newInstance();
        } else if (position == R.string.menu_home) {

//            Intent intent = new Intent(this, SettingsActivity.class);
//
//            /*获取当前系统的android版本号*/
//            int currentApiVersion=Build.VERSION.SDK_INT;
//            if (currentApiVersion>= Build.VERSION_CODES.LOLLIPOP){
//
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,
//                        Pair.create(view1, "agreedName1"));
//
//                startActivity(intent,
//                        options.toBundle());
//            }else {
//
//            }

//            如果不想使用transition可以设置options bundle为null。当需要结束当前Activity并回退这个动画时调用Activity.finishAfterTransition()方法。

        } else if (position == R.string.menu_pic) {
            fragment = PicDealFragment.newInstance();
        } else if (position == R.string.menu_log) {
            fragment = LogRecordFragment.newInstance();
        } else if (position == R.string.menu_nested) {
            fragment = NestedFragment.newInstance();
        } else if (position == R.string.menu_set) {
            fragment = null;
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        if (fragment == null) {
            return;
        }

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        doubleClickListen();
    }

    private void doubleClickListen() {
        //实现数值的复制,查SE的api
        System.arraycopy(hits, 1, hits, 0, hits.length - 1);
        //获取系统开机时间
        hits[hits.length - 1] = SystemClock.uptimeMillis();
        if (hits[0] >= (SystemClock.uptimeMillis() - 500)) {
            showDialog(ExitDialog.instance());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.menu_home);
                break;
            case 2:
                mTitle = getString(R.string.menu_suggest);
                break;
            case 3:
                mTitle = getString(R.string.menu_set);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            return;
        }
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus)
//            showSystemUI();
//        else {
//            hideSystemUI();
//        }

    }

    /**
     * 沉浸模式 (Immersive)
     */
    // This snippet hides the system bars.
    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    /**
     * 黏性沉浸模式 (Sticky Immersive)
     */
    // This snippet shows the system bars. It does this by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        );
    }

    /**
     * 普通全屏模式 (Fullscreen)
     */
    private void showNormalSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN

        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogMsg(int id, Object msg, Class cls) {
        if (cls == ExitDialog.class) {
            finish();
        }
    }
}
