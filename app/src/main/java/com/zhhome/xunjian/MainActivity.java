package com.zhhome.xunjian;

import android.Manifest;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.zhhome.xunjian.activity.Login;
import com.zhhome.xunjian.http.HttpHelper;
import com.zhhome.xunjian.http.HttpUrl;
import com.zhhome.xunjian.http.MySubscriber;
import com.zhhome.xunjian.http.baserx.RxHelper;
import com.zhhome.xunjian.uplod.UpdateChecker;
import com.zhhome.xunjian.utils.AlertUtils;
import com.zhhome.xunjian.utils.WeiboDialogUtils;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tv_username)
    EditText tvUsername;
    @Bind(R.id.tv_password)
    EditText tvPassword;
    @Bind(R.id.login)
    TextView login;
    private Dialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        updateVersion();
        initData();


    }

    private void initData() {

    }
    @OnClick(R.id.login)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.login:
                login();
                break;
        }
    }

    private void login() {
        String username = tvUsername.getText().toString().trim();
        String password = tvPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)||TextUtils.isEmpty(username)){
            AlertUtils.showToast(MainActivity.this,"用户名和密码不能为空！");
            return;
        }
        mDialog = WeiboDialogUtils.createLoadingDialog(MainActivity.this,"正在登录..");
        HttpHelper.getDefault()
                .login(HttpUrl.getLoginUrl,
                        username,password)
                .compose(RxHelper.handleResult())
                .subscribe(new MySubscriber<String>(MainActivity.this) {
                    @Override
                    protected void _onError(String code, String msg) {
                        AlertUtils.showToast(MainActivity.this,msg);
                        WeiboDialogUtils.closeDialog(mDialog);
                    }

                    @Override
                    protected void _onNext(String s) {
                        Intent intent = new Intent(MainActivity.this, Login.class);
                        intent.putExtra("mangerid",s);
                        startActivity(intent);
                    }

                    @Override
                    public void onComplete() {
                        WeiboDialogUtils.closeDialog(mDialog);
                    }
                });
    }
    //////版本更新
    private void updateVersion() {
        UpdateChecker updateChecker = new UpdateChecker(this);
        updateChecker.checkForUpdates();

    }
}
