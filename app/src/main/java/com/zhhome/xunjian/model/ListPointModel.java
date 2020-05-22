package com.zhhome.xunjian.model;

import java.io.Serializable;

public class ListPointModel implements Serializable {


    /**
     * type : app核销系统
     * desc : app核销系统直接扣除积分
     * date : 2018-12-06 17:10:29
     * point : -6
     * name : 熊 *
     * mobile : 136****9138
     */

    private String type;
    private String desc;
    private String date;
    private int point;
    private String name;
    private String mobile;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
