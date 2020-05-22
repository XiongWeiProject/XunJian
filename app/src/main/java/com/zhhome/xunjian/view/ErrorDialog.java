package com.zhhome.xunjian.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mylhyl.circledialog.BaseCircleDialog;
import com.mylhyl.circledialog.res.values.CircleDimen;
import com.squareup.picasso.Picasso;
import com.zhhome.xunjian.R;

/**
 * Created by h on 2017/8/20.
 */
@SuppressLint("ValidFragment")
public class ErrorDialog extends BaseCircleDialog {
    private View mView;
    private TextView i_wiring1,i_wiring2,saoma_login;
    private ImageView close,title_imag;
    private int typeid =0;
    private String code;
    public ErrorDialog(int type, String text) {
        typeid = type;
        code = text;
    }
    public static ErrorDialog getInstance(int type,String text) {
        ErrorDialog dialogFragment = new ErrorDialog(type,text);
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
            mView = inflater.inflate(R.layout.custom_adilog_feifa, container, false);
        }
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        close = (ImageView) mView.findViewById(R.id.close);
        title_imag = (ImageView) mView.findViewById(R.id.title_img);
        i_wiring1 = (TextView) mView.findViewById(R.id.warning1);
        i_wiring2 = (TextView) mView.findViewById(R.id.warning2);
        saoma_login = (TextView) mView.findViewById(R.id.saoma_login);
        if (typeid==0){
            Picasso.with(getActivity()).load(R.drawable.rigt).into(title_imag);
            i_wiring1.setText(code);
            i_wiring2.setText("");
        }else if (typeid==1){
            Picasso.with(getActivity()).load(R.drawable.error).into(title_imag);
            i_wiring1.setText(code);
            i_wiring2.setText("请输入正确的兑换码");
        }else if (typeid==2){
            Picasso.with(getActivity()).load(R.drawable.error).into(title_imag);
            i_wiring1.setText(code);
            i_wiring2.setText("");
        }else if (typeid==3){
            Picasso.with(getActivity()).load(R.drawable.rigt).into(title_imag);
            i_wiring1.setText(code);
            i_wiring2.setText("");
        }else if (typeid==4){
            Picasso.with(getActivity()).load(R.drawable.error).into(title_imag);
            i_wiring1.setText(code);
            i_wiring2.setText("");
        }
        saoma_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeid==3||typeid==4){
                    getActivity().finish();
                }else {
                    dismiss();
                }
            }
        });


    }

}
