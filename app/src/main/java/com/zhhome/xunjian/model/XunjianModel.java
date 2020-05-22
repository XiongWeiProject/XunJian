package com.zhhome.xunjian.model;

import java.io.Serializable;
import java.util.List;

public class XunjianModel implements Serializable {

    /**
     * version : 1
     * name : 33号机-周市社区中心
     * inspection : [{"id":1,"name":"卫生情况","action":"health","status":0},{"id":2,"name":"电源电压","action":"supply","status":0},{"id":3,"name":"进水情况","action":"wather","status":0},{"id":4,"name":"屏幕情况","action":"screen","status":0},{"id":5,"name":"雨棚周围情况","action":"around","status":0}]
     * version_name : 9.2
     * address : 年家浜路341号（周市社区中心）
     * sequence : ML5RRHOTVC
     * day : 2018-12-25 星期二
     */

    private int version;
    private String name;
    private String version_name;
    private String address;
    private String sequence;
    private String day;
    private String Intime;//安装时间
    private String sq;//社区
    private List<InspectionBean> inspection;

    public String getIntime() {
        return Intime;
    }

    public void setIntime(String intime) {
        Intime = intime;
    }

    public String getSq() {
        return sq;
    }

    public void setSq(String sq) {
        this.sq = sq;
    }


    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<InspectionBean> getInspection() {
        return inspection;
    }

    public void setInspection(List<InspectionBean> inspection) {
        this.inspection = inspection;
    }

    public static class InspectionBean {
        /**
         * id : 1
         * name : 卫生情况
         * action : health
         * status : 0
         */

        private int id;
        private String name;
        private String action;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
