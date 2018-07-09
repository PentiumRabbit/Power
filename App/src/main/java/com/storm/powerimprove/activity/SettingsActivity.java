package com.storm.powerimprove.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.base.common.base.BaseActivity;
import com.storm.powerimprove.Dev;
import com.storm.powerimprove.R;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView();
        initDate();
    }


    protected void initView() {
        View dev = findViewById(R.id.tv_dev);
        dev.setVisibility(View.VISIBLE);
        dev.setOnClickListener(this);
    }

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_dev:
                Dev.startDevUI(this);
                break;
        }
    }
}
