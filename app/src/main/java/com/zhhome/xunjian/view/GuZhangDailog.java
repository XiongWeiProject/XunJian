package com.zhhome.xunjian.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylhyl.circledialog.BaseCircleDialog;
import com.mylhyl.circledialog.res.values.CircleDimen;
import com.zhhome.xunjian.R;
import com.zhhome.xunjian.utils.InterFacesCallBack;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 自定义第一次进入应用列表对话框
 * Created by h on 2017/8/10.
 */
@SuppressLint("ValidFragment")
public class GuZhangDailog extends BaseCircleDialog implements View.OnClickListener {


    @Bind(R.id.iv_cance)
    ImageView ivCance;
    @Bind(R.id.id_card_chuli)
    TextView idCardChuli;
    @Bind(R.id.ll_card_chuli)
    LinearLayout llCardChuli;
    @Bind(R.id.bar_code_yingjian)
    TextView barCodeYingjian;
    @Bind(R.id.ll_cord_yingjian)
    LinearLayout llCordYingjian;
    @Bind(R.id.bar_ruanjian)
    TextView barRuanjian;
    @Bind(R.id.ll_cord_ruanjia)
    LinearLayout llCordRuanjia;
    @Bind(R.id.bar_qita)
    TextView barQita;
    @Bind(R.id.ll_cord_qita)
    LinearLayout llCordQita;
    @Bind(R.id.tv_ok)
    TextView tvOk;
    private Context context;
    private List<String> param;
    int type = 2;
    //z传入的activity
    private FragmentManager mContext;
    private InterFacesCallBack.guzhang guzhangs;
    int positions;
    public GuZhangDailog(InterFacesCallBack.guzhang guzhang, int position) {
        guzhangs = guzhang;
        positions =position;
        context = getActivity();

    }

    public static GuZhangDailog getInstance(InterFacesCallBack.guzhang guzhang,int position) {
        GuZhangDailog dialogFragment = new GuZhangDailog(guzhang,position);
        dialogFragment.setCanceledBack(true);
        dialogFragment.setCanceledOnTouchOutside(false);
        dialogFragment.setRadius(CircleDimen.RADIUS);
        dialogFragment.setWidth(0.8f);
        dialogFragment.setGravity(Gravity.CENTER);
        dialogFragment.setBackgroundColor(Color.WHITE);
        return dialogFragment;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
        this.mContext = manager;
    }

    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup container) {
        View custom_adilog_login = inflater.inflate(R.layout.custom_adilog_guzhang, container, false);
        return custom_adilog_login;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ivCance.setOnClickListener(this);
        llCardChuli.setOnClickListener(this);
        llCordRuanjia.setOnClickListener(this);
        llCordQita.setOnClickListener(this);
        llCordYingjian.setOnClickListener(this);
        tvOk.setOnClickListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_cance:
                type = 2;
                dismiss();
                break;
            case R.id.ll_card_chuli:
                type = 3;
                idCardChuli.setTextColor(getResources().getColor(R.color.text_bg_color));
                barCodeYingjian.setTextColor(getResources().getColor(R.color.text_color));
                barRuanjian.setTextColor(getResources().getColor(R.color.text_color));
                barQita.setTextColor(getResources().getColor(R.color.text_color));
                llCardChuli.setBackgroundResource(R.drawable.xunjian_chuli2);
                llCordRuanjia.setBackgroundResource(R.drawable.xunjian_chose);
                llCordQita.setBackgroundResource(R.drawable.xunjian_chose);
                llCordYingjian.setBackgroundResource(R.drawable.xunjian_chose);
                break;
            case R.id.ll_cord_ruanjia:
                type = 5;
                idCardChuli.setTextColor(getResources().getColor(R.color.text_color));
                barCodeYingjian.setTextColor(getResources().getColor(R.color.text_color));
                barRuanjian.setTextColor(getResources().getColor(R.color.text_bg_color));
                barQita.setTextColor(getResources().getColor(R.color.text_color));
                llCardChuli.setBackgroundResource(R.drawable.xunjian_chose);
                llCordRuanjia.setBackgroundResource(R.drawable.xunjian_chuli2);
                llCordQita.setBackgroundResource(R.drawable.xunjian_chose);
                llCordYingjian.setBackgroundResource(R.drawable.xunjian_chose);
                break;
            case R.id.ll_cord_yingjian:
                type = 4;
                idCardChuli.setTextColor(getResources().getColor(R.color.text_color));
                barCodeYingjian.setTextColor(getResources().getColor(R.color.text_bg_color));
                barRuanjian.setTextColor(getResources().getColor(R.color.text_color));
                barQita.setTextColor(getResources().getColor(R.color.text_color));
                llCardChuli.setBackgroundResource(R.drawable.xunjian_chose);
                llCordRuanjia.setBackgroundResource(R.drawable.xunjian_chose);
                llCordQita.setBackgroundResource(R.drawable.xunjian_chose);
                llCordYingjian.setBackgroundResource(R.drawable.xunjian_chuli2);
                break;
            case R.id.ll_cord_qita:
                type = 6;
                idCardChuli.setTextColor(getResources().getColor(R.color.text_color));
                barCodeYingjian.setTextColor(getResources().getColor(R.color.text_color));
                barRuanjian.setTextColor(getResources().getColor(R.color.text_color));
                barQita.setTextColor(getResources().getColor(R.color.text_bg_color));
                llCardChuli.setBackgroundResource(R.drawable.xunjian_chose);
                llCordRuanjia.setBackgroundResource(R.drawable.xunjian_chose);
                llCordQita.setBackgroundResource(R.drawable.xunjian_chuli2);
                llCordYingjian.setBackgroundResource(R.drawable.xunjian_chose);
                break;
            case R.id.tv_ok:
                guzhangs.guzhang(type,positions);
                dismiss();
                break;

        }

    }


}
