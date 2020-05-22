package com.zhhome.xunjian.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.zhhome.xunjian.MainActivity;
import com.zhhome.xunjian.R;
import com.zhhome.xunjian.http.HttpHelper;
import com.zhhome.xunjian.http.HttpUrl;
import com.zhhome.xunjian.http.MySubscriber;
import com.zhhome.xunjian.http.baserx.RxHelper;
import com.zhhome.xunjian.utils.AlertUtils;
import com.zhhome.xunjian.utils.CropSquareTransformations;
import com.zhhome.xunjian.utils.SaveParam;
import com.zhhome.xunjian.utils.WeiboDialogUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePassword extends AppCompatActivity {

    @Bind(R.id.tv_back)
    TextView tvBack;
    @Bind(R.id.iv_head)
    ImageView ivHead;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.old_password)
    EditText oldPassword;
    @Bind(R.id.new_password)
    EditText newPassword;
    @Bind(R.id.new_ag_password)
    EditText newAgPassword;
    @Bind(R.id.chang_sub)
    TextView changSub;
    private Dialog mDialog;
    String name ,face ,mobie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        face = intent.getStringExtra("face");
        mobie = intent.getStringExtra("moble");
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        Picasso.with(ChangePassword.this).load(face).transform(new CropSquareTransformations()).into(ivHead);
        tvName.setText(name);
        tvPhone.setText(mobie);
    }

    @OnClick({R.id.tv_back, R.id.chang_sub})
    public void onViewClicked(View view) {
        String old = oldPassword.getText().toString().trim();
        String newpassword = newPassword.getText().toString().trim();
        String newage= newAgPassword.getText().toString().trim();
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.chang_sub:
                if ( TextUtils.isEmpty(old)|| TextUtils.isEmpty(newpassword)|| TextUtils.isEmpty(newage)){
                    AlertUtils.showToast(ChangePassword.this,"请将信息补充完整");
                    return;
                }
                changePassword(old,newpassword,newage);
                break;
        }
    }

    private void changePassword( String old,String newpassword, String newage) {
        mDialog = WeiboDialogUtils.createLoadingDialog(ChangePassword.this,"请稍等..");
        HttpHelper.getDefault()
                .changgepassword(HttpUrl.changepasswordUrl,
                        mobie,old,newpassword,newage)
                .compose(RxHelper.handleResult())
                .subscribe(new MySubscriber<String>(ChangePassword.this) {
                    @Override
                    protected void _onError(String code, String msg) {
                        AlertUtils.showToast(ChangePassword.this,msg);
                        WeiboDialogUtils.closeDialog(mDialog);
                    }

                    @Override
                    protected void _onNext(String s) {
                        SaveParam.getInstance().clearData(ChangePassword.this);
                        AlertUtils.showToast(ChangePassword.this,"已为您修改密码");
                        Intent intent = new Intent(ChangePassword.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onComplete() {
                        WeiboDialogUtils.closeDialog(mDialog);
                    }
                });
    }
}
