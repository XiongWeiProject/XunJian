package com.zhhome.xunjian.activity;

import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.maps.AMap;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zhhome.xunjian.R;
import com.zhhome.xunjian.http.HttpUrl;
import com.zhhome.xunjian.utils.AlertUtils;
import com.zhhome.xunjian.utils.CheckPermissionUtils;
import com.zhhome.xunjian.utils.LogUtil;
import com.zhhome.xunjian.utils.WeiboDialogUtils;
import com.zhhome.xunjian.view.ChangeDialog;
import com.zhhome.xunjian.view.ErrorDialog;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

public class Login extends AppCompatActivity {

    //    @Bind(R.id.bmapView)
//    MapView bmapView;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.xunjian_jindu)
    TextView xunjianJindu;
    @Bind(R.id.saoma_login)
    TextView saomaLogin;
    @Bind(R.id.jiqi_login)
    TextView jiqiLogin;
    String LoginJson = "";
    @Bind(R.id.setting)
    ImageView setting;
    String result;
    String name, face, mobile;
    public static final int REQUEST_CODE = 111;
    @Bind(R.id.exit)
    TextView exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        LoginJson = intent.getStringExtra("mangerid");
        initPermission();
        initData();
    }

    private void initData() {
        JSONObject jsonObject = JSONObject.parseObject(LoginJson);
        name = jsonObject.getString("name");
        face = jsonObject.getString("face");
        mobile = jsonObject.getString("mobile");
        tvTime.setText(jsonObject.getString("date"));
        int cont = jsonObject.getInteger("count");
        int checkNum = jsonObject.getInteger("checkNum");
        xunjianJindu.setText(checkNum + "/" + cont);
    }

    @OnClick({R.id.saoma_login, R.id.jiqi_login, R.id.setting, R.id.exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.saoma_login:
                Intent intent = new Intent(getApplication(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                if (TextUtils.isEmpty(result)) {
                    AlertUtils.showToast(Login.this, "请扫码您的二维码");
                    return;
                }
                break;
            case R.id.jiqi_login:
                ChangeDialog.getInstance(1, "", mobile).show(getSupportFragmentManager(), "");
                break;
            case R.id.setting:
                Intent setting = new Intent(Login.this, ChangePassword.class);
                setting.putExtra("name", name);
                setting.putExtra("face", face);
                setting.putExtra("moble", mobile);
                startActivity(setting);
                break;
            case R.id.exit:
                finish();
                break;

            case R.id.tv_uploude:

                break;
        }
    }


    private void initPermission() {
        //检查权限
        String[] permissions = CheckPermissionUtils.checkPermission(this);
        if (permissions.length == 0) {
            //权限都申请了
            //是否登录
        } else {
            //申请权限
            ActivityCompat.requestPermissions(this, permissions, 100);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    result = bundle.getString(CodeUtils.RESULT_STRING);
                    saomaLogins(result);
                    //Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(Login.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void saomaLogins(String result) {
        //扫码登录
        Intent setting = new Intent(Login.this, Xunjian.class);
        setting.putExtra("mobile", mobile);
        setting.putExtra("jiqima", result);
        startActivity(setting);
        //Toast.makeText(Login.this, "你扫描的二维码" + result, Toast.LENGTH_LONG).show();
    }
}
