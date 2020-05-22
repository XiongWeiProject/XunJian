package com.zhhome.xunjian.model;

import java.io.Serializable;

/**
 * Created by cheng on 2018/12/5.
 */

public class ListQuanOneModel implements Serializable {

    /**
     * id : 109
     * denomination : 40
     * sta : 0
     * c_name : 40元社区服务券
     * user_name : 杨家富
     * user_mobile : 15618601371
     */

    private int id;
    private String denomination;
    private int sta;
    private String c_name;
    private String user_name;
    private String user_mobile;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public int getSta() {
        return sta;
    }

    public void setSta(int sta) {
        this.sta = sta;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }
}
