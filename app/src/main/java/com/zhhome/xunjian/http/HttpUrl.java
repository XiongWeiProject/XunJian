package com.zhhome.xunjian.http;


/**
 * Created by h on 2017/8/15.
 */

public class HttpUrl {
    public final static String BASE_URL = "http://guanjia.zhhome.com";
    public final static String PRODUCT_URL = "http://guanjia.zhhome.com/android/";
    //序列号  androidCount = 8 表示手机端

    //public static String serialNumber = "/androidCount/8/serial_id/" + getSerialNumber() + "/Comrc_id/" +getID()+"/user_id/"+getUserID();

    public static String getLoginUrl = "control/checkLogin";//管理员登录
    public static String changepasswordUrl = "control/save";//修改密码
    public static String getXunjianList = "control/Inspection" ; //获取巡检列表
    public static String getXunjianDown = "control/setDown" ; //无法开机
    public static String getXunjianResult = PRODUCT_URL+"control/Inspection_set/" ; //提交巡检结果
    public static String getXunjianProblem = PRODUCT_URL+"control/questionDemo/" ; //提交巡检问题
    public static String getXunjianPOSITION = PRODUCT_URL+"control/set_coordinate/" ; //上传金纬毒
    public static String getAPPurlUrl = "apk/version/";//升级


}
