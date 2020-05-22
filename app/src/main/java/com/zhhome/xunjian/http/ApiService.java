package com.zhhome.xunjian.http;


import org.devio.takephoto.model.TImage;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;


/**
 * Created by Administrator on 2017/1/10.
 */

public interface ApiService {
    /**
     * 教育共享 机构列表
     * @param url
     * @param user_id
     * @return
     */
    @GET
    Observable<ResponseBody> quanList(@Url String url,
                                      @Query("user_id") String user_id);

    /**
     * 兑换，租赁，借书列表
     * @param url
     * @param userid
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<ResponseBody> quanLists(@Url String url,
                                       @Field("user_id") String userid);
    /**
     * 登录
     * @param user_name
     * @param user_pwd
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<ResponseBody> login(@Url String url,
                                   @Field("name") String user_name,
                                   @Field("pwd") String user_pwd
    );
    /**
     * 获取用户信息
     * @param type
     * @param flag
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<ResponseBody> getUserinfo(@Url String url,
                                         @Field("type") String type,
                                         @Field("flag") String flag
    );
    /**
     * 积分扣除
     * @param user_id
     * @param point
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<ResponseBody> kouPoint(@Url String url,
                                      @Field("id") String user_id,
                                      @Field("point") String point,
                                      @Field("do_id") String do_id
    );
    /**
     * 使用抵扣券
     * @param user_id
     * @param code
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<ResponseBody> userQuan(@Url String url,
                                      @Field("id") String id,
                                      @Field("user_id") String user_id,
                                      @Field("code") String code
    );

    /**
     * 租赁商品
     * @param user_id
     * @param name
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<ResponseBody> userZuling(@Url String url,
                                        @Field("user_id") String user_id,
                                        @Field("goods_name") String name
    );
    /**
     * 借书
     * @param user_id
     * @param name
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<ResponseBody> userBring(@Url String url,
                                       @Field("user_id") String user_id,
                                       @Field("book_name") String name
    );

    /**
     * 租赁商品
     * @param user_id
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<ResponseBody> userZulinglist(@Url String url,
                                            @Field("type") String type,
                                            @Field("user_id") String user_id,
                                            @Field("id") String id
    );
    /**
     * 积分扣除记录
     * @param do_id
     * @param start
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<ResponseBody> pointlist(@Url String url,
                                       @Field("do_id") String do_id,
                                       @Field("start") String start,
                                       @Field("end") String end
    );
    /**
     * APP升级
     * @param version
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<ResponseBody> appuploud(@Url String url,
                                       @Field("type") String type,
                                       @Field("version") String version
    );
    /**
     * 修改密码
     * @param mobile
     * @param oldPwd
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<ResponseBody> changgepassword(@Url String url,
                                       @Field("mobile") String mobile,
                                       @Field("oldPwd") String oldPwd,
                                       @Field("newPwd") String newPwd,
                                       @Field("repeat") String repeat
    );
    /**
     *  获取巡检列表
     * @param url
     * @return
     */
    @POST
    Observable<ResponseBody> getXunjianlist(@Url String url
    );
    /**
     * 提交巡检结果
     * @param url
     * @param body
     * @return
     */
    @POST
    Observable<ResponseBody> subProblem(@Url String url,
                                        //@Field("text") String text,
                                        @Body RequestBody body
//                                             @Field("video") List<File> video,
//                                             @Field("recording") List<File> recording
    );
 }
