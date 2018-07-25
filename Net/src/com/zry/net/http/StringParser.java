package com.zry.net.http;

/**
 * 默认字符串解析
 * <p/>
 * ZhaoRuYang
 * 7/18/16 5:56 PM
 */
public class StringParser implements DataParser<String> {

    @Override
    public String parse(String s) {
        return s;
    }
}
