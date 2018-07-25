package com.zry.power.fragment;


import android.support.v4.app.FragmentManager;

import com.zry.base.common.base.BaseFragment;
import com.zry.power.dialog.NoFrameDialog;
import com.zry.power.dialog.OnDialogCallBack;

/**
 * @author ----zhaoruyang----
 * @data: 2015/6/26
 */
public abstract class LocalDialogFragment extends BaseFragment implements OnDialogCallBack {
    private static final String TAG = LocalDialogFragment.class.getSimpleName();
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

        if (getActivity() == null || !isAdded()) {
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

    @Override
    public void onDialogMsg(int id, Object msg, Class cls) {

    }
}
