package com.zhhome.xunjian.http;

import android.content.Context;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;


/**
 * des:订阅封装
 * Created by norton
 * on 2016.09.10:16
 */


public abstract class MySubscriber<T> implements Observer<T> {

    private Context mContext;
    private CompositeDisposable mDisposables;

    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    //自定义错误状态码 CUR开头表示自定义状态码
    private static final String INTENT_ERROR = "CUR_45";
    private static final String PARSE_ERROR = "CUR_46";
    private static final String CONNECT_ERROR = "CUR_47";

    //UNK开头表示未知错误
    private static final String UNKNOW_ERROR = "UNK_1";

    //获取response出错
    private static final String RESPONSE_ERROR = "res_err";


    public MySubscriber(Context context) {
        this.mContext = context;
    }

    public MySubscriber(Context context, CompositeDisposable mDisposables) {
        this.mContext = context;
        this.mDisposables = mDisposables;

    }


    @Override
    public void onError(Throwable e) {

        //网络
        /*if (!NetWorkUtils.isNetConnected(App.getAppContext())) { //没有网络
            _onError("网络不可用");
        }else*/
        if (e instanceof HttpException) { //HTTP 错误
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    _onError(INTENT_ERROR,"网络错误");
                    break;
            }
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            _onError(PARSE_ERROR,"解析错误");
        } else if (e instanceof ConnectException) {
            _onError(CONNECT_ERROR,"连接失败");
        } else if (e instanceof ApiException) {

            ApiException serverException = (ApiException) e;
            switch (serverException.getStatus()) {
                case "0": //查询结果为空或者返回结果有误
                    _onError(serverException.getStatus(), serverException.getMsg());
                    break;
                case RESPONSE_ERROR: //response返回出错或者解析错误
                    _onError(RESPONSE_ERROR, serverException.getMsg());
                    break;
                default:
                    _onError(serverException.getStatus(), serverException.getMsg());
                    break;
            }


        } else {
//            ToastUitl.showLong("未知错误" + e.getMessage());
            _onError(UNKNOW_ERROR,"未知错误:" + e.getMessage());
        }

    }

    @Override
    public void onSubscribe(Disposable d) {
        if (mDisposables != null) {
            mDisposables.add(d);
        }
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }


    /**
     * 错误回调
     */
    protected abstract void _onError(String code, String msg);

    protected abstract void _onNext(T t);


}
