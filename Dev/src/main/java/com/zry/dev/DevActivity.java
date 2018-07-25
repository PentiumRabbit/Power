package com.zry.dev;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zry.base.common.utils.SysInfoUtil;

public class DevActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_desc;

    public static void start(Context context) {
        context.startActivity(new Intent(context, DevActivity.class));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.devmodule_activity_dev);

        tv_desc = findViewById(R.id.tv_desc);
        tv_desc.setOnClickListener(this);
        StringBuilder builder = new StringBuilder()
                .append("git version : ").append(getString(R.string.git_version)).append("\r\n")
                //.append("app version : ").append(AppConstant.VERSION_NAME).append("\r\n")
                //.append("app version code : ").append(AppConstant.VERSION_CODE).append("\r\n")
                //.append("app channel : ").append(CommonUtils.getChannelName(getApplication())).append("\r\n")
                .append("build time : ").append(getString(R.string.build_time)).append("\r\n")
                .append("IMEI or did : ").append(SysInfoUtil.getIMEI(this)).append("\r\n")
                .append("System CPU_ABI : ").append(android.os.Build.CPU_ABI).append("\r\n")
                .append("System MODEL : ").append(android.os.Build.MODEL).append("\r\n")
                .append("System SDK : ").append(android.os.Build.VERSION.SDK).append("\r\n")
                .append("System VERSION.RELEASE        : ").append(android.os.Build.VERSION.RELEASE).append("\r\n");
        tv_desc.setText(builder);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_desc) {
            // 从API11开始android推荐使用android.content.ClipboardManager
            // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 将文本内容放到系统剪贴板里。
            cm.setText(tv_desc.getText());
            Toast.makeText(this, "已复制到剪切板", Toast.LENGTH_SHORT).show();

        }
    }
}
