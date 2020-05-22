package com.zhhome.xunjian.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zhhome.xunjian.R;
import com.zhhome.xunjian.adapter.XunjianAdapter;
import com.zhhome.xunjian.http.HttpClient;
import com.zhhome.xunjian.http.HttpHelper;
import com.zhhome.xunjian.http.HttpUrl;
import com.zhhome.xunjian.http.MySubscriber;
import com.zhhome.xunjian.http.baserx.RxHelper;
import com.zhhome.xunjian.model.XunjianModel;
import com.zhhome.xunjian.utils.AlertUtils;
import com.zhhome.xunjian.utils.InterFacesCallBack;
import com.zhhome.xunjian.utils.LogUtil;
import com.zhhome.xunjian.utils.WeiboDialogUtils;
import com.zhhome.xunjian.view.ErrorDialog;
import com.zhhome.xunjian.view.RecyclerViewSpaceItem;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Xunjian extends AppCompatActivity implements InterFacesCallBack.guzhang, AMapLocationListener {

    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_juwei)
    TextView tvJuwei;
    @Bind(R.id.tv_bianhao)
    TextView tvBianhao;
    @Bind(R.id.tv_vision)
    TextView tvVision;
    @Bind(R.id.tv_anzhuang_time)
    TextView tvAnzhuangTime;
    @Bind(R.id.tv_shequ)
    TextView tvShequ;
    @Bind(R.id.tv_adress)
    TextView tvAdress;
    @Bind(R.id.xunjia_list)
    RecyclerView xunjiaList;
    @Bind(R.id.tv_ok_comnit)
    TextView tvOkComnit;
    int guzhangid = 0;
    String mobile, jiqima;
    @Bind(R.id.exit)
    TextView exit;
    @Bind(R.id.tv_uploude)
    TextView tvUploude;
    private boolean isfirst = true;
    private XunjianAdapter xunjianAdapter;
    private List<XunjianModel.InspectionBean> xunjianModels = new ArrayList<>();
    private XunjianModel xunjianModelss = new XunjianModel();
    public CompositeDisposable mDisposables = new CompositeDisposable(); //用于绑定Rxjava与activity的生命周期
    Map<String, Integer> map = new HashMap<String, Integer>();
    ;
    private Dialog mDialog;
    //========================= 高德地图==============================
    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private double mLat = -1;
    private boolean isFirstLoc = true; // 是否首次定位
    private double mLon = -1;
    private UiSettings mUiSettings;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xunjian);
        Intent intent = getIntent();
        mobile = intent.getStringExtra("mobile");
        jiqima = intent.getStringExtra("jiqima");
        ButterKnife.bind(this);
        initMap();
        initData();
    }

    private void initData() {
        xunjianAdapter = new XunjianAdapter(xunjianModels, Xunjian.this);
        xunjiaList.setLayoutManager(new LinearLayoutManager(this));
        xunjiaList.addItemDecoration(new RecyclerViewSpaceItem(20));
        xunjiaList.setAdapter(xunjianAdapter);
        xunjiaList.setNestedScrollingEnabled(false);
        View view = getLayoutInflater().inflate(R.layout.layout_not_data, (ViewGroup) getWindow().getDecorView(), false);
        xunjianAdapter.setEmptyView(view);
        xunjianAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        xunjianAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.xunjian_chuli:
                        Intent settings = new Intent(Xunjian.this, ProbleActvity.class);

                        settings.putExtra("serial_id", jiqima);
                        settings.putExtra("mobile", mobile);
                        settings.putExtra("isimage", "1");
                        settings.putExtra("action", xunjianModels.get(position).getAction());
                        startActivity(settings);
                        //GuZhangDailog.getInstance(Xunjian.this, position).show(getSupportFragmentManager(), "");
                        break;
                }
            }
        });
        getXunjianList();

    }

    private void initMap() {


        mlocationClient = new AMapLocationClient(this);
//初始化定位参数
        mLocationOption = new AMapLocationClientOption();
//设置定位监听
        mlocationClient.setLocationListener(this);
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
//设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
// 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
// 在定位结束后，在合适的生命周期调用onDestroy()方法
// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
//启动定位
        mlocationClient.startLocation();

    }

    private void getIndex() {
        if (mLat != -1) {
            mDialog = WeiboDialogUtils.createLoadingDialog(Xunjian.this, "正在提交..");
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            builder.addFormDataPart("serial_id", jiqima);
            builder.addFormDataPart("longitude", mLon + "");
            builder.addFormDataPart("latitude", mLat + "");
            MultipartBody requestBody = builder.build();
            //构建请求
            Request request = new Request.Builder()
                    .url(HttpUrl.getXunjianPOSITION)//地址
                    .post(requestBody)//添加请求体
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    LogUtil.e("上传失败", e.getMessage());
                    WeiboDialogUtils.closeDialog(mDialog);
                    ErrorDialog.getInstance(2, "设备位置上传失败！请重试").show(getSupportFragmentManager(), "");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    WeiboDialogUtils.closeDialog(mDialog);
                    LogUtil.e("日志", response.toString());
                    ErrorDialog.getInstance(3, "设备位置上传成功！").show(getSupportFragmentManager(), "");
                }
            });
        }
    }

    private void getXunjianList() {
        HttpHelper.getDefault()
                .getXunjianlist(HttpUrl.getXunjianList + "/androidCount/1/serial_id/" + jiqima + "/user_id/" + mobile)
                .compose(RxHelper.handleResult())
                .subscribe(new MySubscriber<String>(this, mDisposables) {
                    @Override
                    protected void _onError(String code, String msg) {
                        ErrorDialog.getInstance(4, msg).show(getSupportFragmentManager(), "");
                    }

                    @Override
                    protected void _onNext(String s) {
                        xunjianModelss = JSON.parseObject(s, XunjianModel.class);
                        xunjianModels = xunjianModelss.getInspection();
                        xunjianAdapter.addData(xunjianModels);
                        showData(xunjianModelss);
                        for (int i = 0; i < xunjianModels.size(); i++) {
                            map.put(xunjianModels.get(i).getAction(), xunjianModels.get(i).getStatus());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    //显示数据
    private void showData(XunjianModel xunjianModel) {
        tvTime.setText(xunjianModel.getDay());
        tvJuwei.setText("设备编号：" + xunjianModel.getName());
        tvBianhao.setText("设备编号：" + xunjianModel.getSequence());

        if (xunjianModel.getVersion() == 1) {
            tvVision.setText("系统版本：" + "最新");
        } else {
            tvVision.setText("系统版本：" + "可更新");
        }
        tvAnzhuangTime.setText("安装时间：" + xunjianModel.getIntime());
        tvShequ.setText("所在社区：" + xunjianModel.getSq() + "");
        tvAdress.setText("设备地址：" + xunjianModel.getAddress());


    }


    @Override
    public void guzhang(int type, int position) {
        map.put(xunjianModels.get(position).getAction(), type);
        Log.d("test", "map" + map + "type" + type);
    }

    @OnClick({R.id.exit, R.id.tv_ok_comnit,R.id.tv_uploude})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.exit:
                finish();
                break;
            case R.id.tv_ok_comnit:
                List<Integer> list = new ArrayList<>();
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    list.add(entry.getValue());
                }
                if (map.size() != xunjianModels.size() || list.contains(0)) {
                    Log.d("test", "(map.size()" + (map.size() + "xunjianModels.size()" + xunjianModels.size()) + "list" + list);
                    AlertUtils.showToast(Xunjian.this, "请将巡检信息补充完整");
                } else {
                    postxunjian(this, map, xunjianModels.size());
                }
                break;

            case R.id.tv_uploude:
                getIndex();
                break;
        }
    }

    /**
     * 提交巡检信息
     *
     * @param mContext
     * @param
     * @param
     */
    public void postxunjian(final Context mContext, Map<String, Integer> map, int size) {
        mDialog = WeiboDialogUtils.createLoadingDialog(Xunjian.this, "正在提交..");
        RequestParams params = new RequestParams();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            params.put(entry.getKey(), entry.getValue());
        }
        Log.d("test", "params" + params.toString() + "url" + HttpUrl.getXunjianResult + "serial_id/" + jiqima + "/user_id/" + mobile);
        HttpClient.getInstance().setTimeout(10 * 1000);
        HttpClient.getInstance().post(HttpUrl.getXunjianResult + "serial_id/" + jiqima + "/user_id/" + mobile, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                WeiboDialogUtils.closeDialog(mDialog);
                try {
                    JSONObject jsonObject = new JSONObject(new String(bytes));
                    int status = jsonObject.optInt("sta");
                    String remarks = jsonObject.optString("remarks");
                    if (status == 1) {
                        ErrorDialog.getInstance(3, "提交成功").show(getSupportFragmentManager(), "");
                    } else {
                        AlertUtils.showToast(mContext, remarks);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
                WeiboDialogUtils.closeDialog(mDialog);
                AlertUtils.showToast(mContext, "无法链接到服务器,请检查您的网络或稍后重试!");
            }
        });
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                mLat  = aMapLocation.getLatitude();//获取纬度
                mLon = aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);//定位时间
                Log.e("AmapError", "获取经度"
                        + aMapLocation.getLatitude()+ "获取经度"
                        + aMapLocation.getLongitude());
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }
}
