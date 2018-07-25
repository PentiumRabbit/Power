package com.zry.base.common.value;

/**
 * @author ----zhaoruyang----
 * @data: 2015-9-21
 */
public interface ValueTAG {

    String ValueTAG = "ValueTAG.";

    /**
     * 异常信息
     */
    String EXCEPTION = ValueTAG + "exception";
    /**
     * OOM追踪
     */
    String OOM = ValueTAG + "oom";

    /**
     * 没有TAG
     */
    String NONE = ValueTAG + "none";

    /**
     * 对象为null
     */
    String NULL = ValueTAG + "null";
}
