package com.android.netconnect.database;

/**
 * @author ----zhaoruyang----
 * @data: 2015/5/20
 */
public interface INetCacheDao {

    /**
     * 保存缓存
     *
     * @param field
     *         界面代号
     * @param cache
     *         缓存
     */
    void saveCache(int field, String cache, int cache_tag);

    /**
     * 获取缓存
     *
     * @param field
     *         界面代号
     *
     * @return 缓存数据
     */
    String getCacheStr(int field);

    /**
     * 清除退出需要清除的缓存
     */
    void clearCache();

}
