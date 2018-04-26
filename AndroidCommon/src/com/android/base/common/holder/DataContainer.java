package com.android.base.common.holder;

/**
 * ZhaoRuYang
 * time : 17-9-11
 * <p>
 * 使用数据承载器，是因为数据有可能是集合的概念
 */

public class DataContainer {

    // 对应的样式ID,建议使用Layout id
    public int typeID;
    private DataGroupContainer parent;
    private Object data;

    public DataContainer(int typeID) {
        this.typeID = typeID;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public int size() {
        return 1;
    }

    public DataContainer get(int position) {
        return this;
    }

    public DataGroupContainer getParent() {
        return parent;
    }

    public void setParent(DataGroupContainer parent) {
        this.parent = parent;
    }

}
