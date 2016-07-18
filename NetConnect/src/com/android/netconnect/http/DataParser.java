package com.android.netconnect.http;

/**
 * 数据转化器
 * <p/>
 * ZhaoRuYang
 * 7/18/16 5:53 PM
 */
public interface DataParser<T> {
    T parse(String s);
}
