package com.zhhome.xunjian.model;

import java.io.Serializable;

/**
 * Created by cheng on 2018/12/5.
 */

public class ListBringThreeModel implements Serializable {


    /**
     * book_name : 精神现象学
     * user_id : 1
     * mobile : 13262736937
     * id : 41
     * borrow_date : 2017-12-12 12:26:12
     * sta : 1
     */

    private String book_name;
    private int user_id;
    private String mobile;
    private int id;
    private int cost;
    private String user_mobile;
    private String user_name;
    private String borrow_date;
    private int sta;


    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }



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

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBorrow_date() {
        return borrow_date;
    }

    public void setBorrow_date(String borrow_date) {
        this.borrow_date = borrow_date;
    }

    public int getSta() {
        return sta;
    }

    public void setSta(int sta) {
        this.sta = sta;
    }
}
