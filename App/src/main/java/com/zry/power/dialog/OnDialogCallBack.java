package com.zry.power.dialog;

/**
 * 用于对话框回调
 *
 * @author ----zhaoruyang----
 * @data: 2015/5/28
 */
public interface OnDialogCallBack<T> {
    /**
     * dialog的回调
     *
     * @param type 回调的类型
     * @param msg  回调回去的消息
     * @param cls  标识来自哪一个类
     */
    void onDialogMsg(int type, T msg, Class cls);
}
