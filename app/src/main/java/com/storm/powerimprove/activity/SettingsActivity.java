package com.storm.powerimprove.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.base.common.base.BaseActivity;
import com.storm.powerimprove.R;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        updateTheme();
    }

    @Override
    protected void updateTheme() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDate() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item  activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
