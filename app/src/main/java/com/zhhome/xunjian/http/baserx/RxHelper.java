package com.zhhome.xunjian.http.baserx;


/**
 * des:对服务器返回数据成功和失败处理
 */

import com.alibaba.fastjson.JSON;
import com.zhhome.xunjian.http.ApiException;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**************使用例子******************/
/*_apiService.login(mobile, verifyCode)
        .compose(RxSchedulersHelper.io_main())
        .compose(RxResultHelper.handleResult())
        .//省略*/

public class RxHelper {
    /**
     * 对服务器返回数据进行预处理
     *
     * @param
     * @return 只返回result
     */
    public static ObservableTransformer<ResponseBody, String> handleResult() {
        return new ObservableTransformer<ResponseBody, String>() {
            @Override
            public ObservableSource<String> apply(@NonNull Observable<ResponseBody> upstream) {
                return upstream.flatMap(new Function<ResponseBody, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull ResponseBody responseBody) throws Exception {
                        String body = "";
                        String msg="";
                        try {
                            body = responseBody.string();
                            String sta = JSON.parseObject(body).getString("sta");
                            msg = JSON.parseObject(body).getString("remarks");
                            if (sta.equals("1")) {
                                String result = JSON.parseObject(body).getString("response");
                                return createData(result != null ? result : "");
                            }else {
                                return Observable.error(new ApiException(sta,msg));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            return Observable.error(new ApiException("res_err","网络异常"));
                        }
                    }

                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };



    }



    /**
     * 创建成功的数据
     *
     * @param data
     * @param
     * @return
     */
    private static  Observable<String> createData(final String data) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                try {
                    e.onNext(data);
                    e.onComplete();
                } catch (Exception ex) {
                    e.onError(ex);
                }
            }
        });

    }

}
