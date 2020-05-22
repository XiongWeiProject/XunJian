package com.zhhome.xunjian.model;

import java.io.Serializable;

/**
 * Created by cheng on 2018/12/6.
 */

public class UserInfoModel implements Serializable {


    /**
     * id : 4135
     * name : 张欣虎
     * mobile : 18621544423
     * id_code : 130627199805053859
     * point : 0.00
     * amount_point : 0.00
     */

    private String id;
    private String name;
    private String mobile;
    private String id_code;
    private String point;
    private String amount_point;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getId_code() {
        return id_code;
    }

    public void setId_code(String id_code) {
        this.id_code = id_code;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getAmount_point() {
        return amount_point;
    }

    public void setAmount_point(String amount_point) {
        this.amount_point = amount_point;
    }
}
