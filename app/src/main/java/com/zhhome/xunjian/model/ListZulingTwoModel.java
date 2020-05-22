package com.zhhome.xunjian.model;

import java.io.Serializable;

/**
 * Created by cheng on 2018/12/5.
 */

public class ListZulingTwoModel implements Serializable {

    /**
     * goods_name : 螺丝工具箱2,螺丝工具箱1
     * date : 2018-01-25 17:13:57
     * sta : 0
     * cost : 100
     * user_id : 343
     * id : 14
     */

    private String goods_name;
    private String date;
    private int sta;
    private int cost;
    private int user_id;
    private int id;
    private String user_mobile;
    private String user_name;

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }


    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSta() {
        return sta;
    }

    public void setSta(int sta) {
        this.sta = sta;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
