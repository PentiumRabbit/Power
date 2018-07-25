package com.zry.net.database;

/**
 * 全局只有一处引用,没必要写成单例
 *
 * @author ----zhaoruyang----
 * @data: 2015/5/20
 */
public interface INetCacheDao {

    /**
     * 保存缓存
     * (涉及多线程操作,实现的时候最好同步)
     *
     * @param cache 缓存
     */
    void save(String key, String cache, NetSaveModel cache_tag);

    /**
     * 获取缓存
     *
     * @return 缓存数据
     */
    String get(String key);

    /**
     * 清除退出需要清除的缓存
     */
    void clear(NetSaveModel tag);

}
