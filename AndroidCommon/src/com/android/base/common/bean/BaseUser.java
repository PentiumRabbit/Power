package com.android.base.common.bean;

import com.google.gson.annotations.Expose;

/**
 * 基本用户结构
 *
 * @Description: BaseUser
 * @Author: ZhaoRuYang
 * @Update: ZhaoRuYang(2015-08-31 18:51)
 */
public class BaseUser {
    @Expose
    private String name;
    @Expose
    private Long birthday;
    @Expose
    private Long id;
    @Expose
    private Integer sex;
    @Expose
    private Integer age;
    @Expose
    private String headIcon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }
}
