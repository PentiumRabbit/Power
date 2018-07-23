package com.android.base.common.view.group;

/**
 * @author : ZhaoRuYang
 * @date : 2018/7/17
 */
public class GroupInfo {
    private int type;
    private String color;

    public GroupInfo() {
    }

    public GroupInfo(int type) {
        this.type = type;
    }

    public GroupInfo(int type, String color) {
        this.type = type;
        this.color = color;
    }

    public int getType() {
        return type;
    }

    public GroupInfo setType(int type) {
        this.type = type;
        return this;
    }

    public String getColor() {
        return color;
    }

    public GroupInfo setColor(String color) {
        this.color = color;
        return this;
    }

    public interface Type {
        int all = 1;
        int top = 2;
        int mid = 3;
        int bottom = 4;
    }

}
