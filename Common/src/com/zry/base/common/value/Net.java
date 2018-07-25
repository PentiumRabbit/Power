package com.zry.base.common.value;

/**
 * ZhaoRuYang
 * time : 17-9-12
 */

public interface Net {

    interface Error {
        // 异常
        int exception = 1;
        // 数据
        int empty = 3;
        // 网络请求异常
        int net = 4;
        // 数据异常
        int data = 5;


    }

}
