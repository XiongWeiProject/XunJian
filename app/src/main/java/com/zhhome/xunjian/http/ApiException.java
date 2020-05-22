package com.zhhome.xunjian.http;

/**
 * Created by Norton on 2017/5/17.
 */

public class ApiException extends Exception {

    private String status;
    private String msg;

    public ApiException(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
