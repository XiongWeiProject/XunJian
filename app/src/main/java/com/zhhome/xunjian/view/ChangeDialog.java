package com.zhhome.xunjian.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mylhyl.circledialog.BaseCircleDialog;
import com.mylhyl.circledialog.res.values.CircleDimen;
import com.zhhome.xunjian.R;
import com.zhhome.xunjian.activity.ChangePassword;
import com.zhhome.xunjian.activity.Xunjian;
import com.zhhome.xunjian.utils.AlertUtils;

/**
 * Created by h on 2017/8/20.
 */
@SuppressLint("ValidFragment")
public class ChangeDialog extends BaseCircleDialog {
    private View mView;
    private TextView i_wiring1, i_wiring2, title_imag, tv_i_ok;
    private EditText duihauma;
    private ImageView close;
    private String userid;
    private String code;
    //1表示使用兑换码，2表示租赁商品，3表示借书
    private int type;
    String quan;
    private FragmentManager mContext;

    public ChangeDialog(int types, String userids, String text) {
        type = types;
        userid = userids;
        code = text;
    }

    public static ChangeDialog getInstance(int type, String userids, String text) {
        ChangeDialog dialogFragment = new ChangeDialog(type, userids, text);
        dialogFragment.setCanceledBack(true);
        dialogFragment.setCanceledOnTouchOutside(false);
        dialogFragment.setRadius(CircleDimen.RADIUS);
        dialogFragment.setWidth(0.6f);
        dialogFragment.setGravity(Gravity.CENTER);
        dialogFragment.setBackgroundColor(Color.WHITE);
        return dialogFragment;
    }

    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup container) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.custom_adilog_kouchu, container, false);
        }
        close = (ImageView) mView.findViewById(R.id.close);
        title_imag = (TextView) mView.findViewById(R.id.title);
        tv_i_ok = (TextView) mView.findViewById(R.id.tv_i_ok);
        duihauma = (EditText) mView.findViewById(R.id.duihauma);
        return mView;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
        this.mContext = manager;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tv_i_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quan = duihauma.getText().toString().trim();
                if (TextUtils.isEmpty(quan)) {
                    AlertUtils.showToast(getActivity(), "请填写机器码！");
                    return;
                }
                dismiss();
                XunjianResultDialog.getInstance(quan,code).show(mContext,"");
//                Intent setting = new Intent(getActivity(), Xunjian.class);
//                setting.putExtra("mobile",code);
//                setting.putExtra("jiqima",quan);
//                startActivity(setting);

            }


        });
    }
}

