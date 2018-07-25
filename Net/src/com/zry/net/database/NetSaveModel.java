package com.zry.net.database;

/**
 * 为什么用enum,而不用int,i因为枚举可以限定用户输入的参数,让用户更方便的找到和理解该参数
 * <p/>
 * (顺序不可变,与顺序关联)
 *
 * @author ----zhaoruyang----
 * @data: 2015/5/20
 */
public enum NetSaveModel {
    /* 不缓存 */
    no_cache,
    /* 退出清理 */
    exit_clear,
    /* 一直保留 */
    save_for_ever
}
