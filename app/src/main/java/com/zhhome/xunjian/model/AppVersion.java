package com.zhhome.xunjian.model;

import java.io.Serializable;

/**
 * Created by cheng on 2018/4/28.
 */

public class AppVersion implements Serializable {


    /**
     * id : 1
     * apk_name : 123
     * version_code : 123
     * apk_file_url : http://guanjia.zhhome.com123
     * version_name : 123
     * date : 0000-00-00 00:00:00
     */

    private String id;
    private String apk_name;
    private int version_code;
    private String apk_file_url;
    private String version_name;
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApk_name() {
        return apk_name;
    }

    public void setApk_name(String apk_name) {
        this.apk_name = apk_name;
    }

    public int getVersion_code() {
        return version_code;
    }

    public void setVersion_code(int version_code) {
        this.version_code = version_code;
    }

    public String getApk_file_url() {
        return apk_file_url;
    }

    public void setApk_file_url(String apk_file_url) {
        this.apk_file_url = apk_file_url;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
