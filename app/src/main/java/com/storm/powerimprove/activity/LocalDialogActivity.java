package com.storm.powerimprove.activity;

import android.app.FragmentManager;

import com.storm.powerimprove.dialog.NoFrameDialog;
import com.storm.powerimprove.dialog.OnDialogCallBack;

/**
 * 含有对话框的activity
 *
 * @author ----zhaoruyang----
 * @data: 2015/6/12
 */
public abstract class LocalDialogActivity extends BaseActivity implements OnDialogCallBack {
    private static final String TAG = "LocalDialogActivity";
    private NoFrameDialog dialog;


    public NoFrameDialog getDialog() {
        return dialog;
    }

    /**
     * 显示对话框
     */
    protected synchronized void showDialog(NoFrameDialog newDialog) {
        if (newDialog == null) {
            return;
        }

        if (this.isFinishing()) {
            return;
        }

        FragmentManager fragmentManager = getFragmentManager();
        if (dialog != null && dialog.isVisible()) {
            dialog.dismiss();
        }
        dialog = newDialog;
        dialog.setOnDialogCallback(this);
        dialog.show(fragmentManager, newDialog.getClass().getName());
    }


}
