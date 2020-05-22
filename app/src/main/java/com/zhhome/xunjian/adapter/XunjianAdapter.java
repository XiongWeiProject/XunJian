package com.zhhome.xunjian.adapter;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhhome.xunjian.R;
import com.zhhome.xunjian.model.XunjianModel;
import com.zhhome.xunjian.utils.InterFacesCallBack;

import java.util.List;

public class XunjianAdapter extends BaseQuickAdapter<XunjianModel.InspectionBean,BaseViewHolder> {
    int type = 0;

    InterFacesCallBack.guzhang  datas ;//选中的按钮
    public XunjianAdapter(@Nullable List<XunjianModel.InspectionBean> data, InterFacesCallBack.guzhang guzhang) {
        super(R.layout.activity_xunjian_item, data);
        datas = guzhang;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void convert(final BaseViewHolder helper, XunjianModel.InspectionBean item) {
        helper.setText(R.id.xunjia_title,item.getName());
        final TextView yv = helper.getView(R.id.xunjian_chuli);
        final RadioButton zhengchang = helper.getView(R.id.xunajian_chengc);
        final RadioButton guzhang = helper.getView(R.id.xunjian_guzhang);
        helper.addOnClickListener(R.id.xunjian_chuli);
        helper.addOnClickListener(R.id.xunajian_chengc);
        helper.addOnClickListener(R.id.xunjian_guzhang);
        if (item.getStatus()==0){
            yv.setVisibility(View.GONE);
            zhengchang.setBackgroundResource(R.drawable.gray_stoke);
            guzhang.setBackgroundResource(R.drawable.gray_stoke);
        }else if (item.getStatus()==1){
            yv.setVisibility(View.GONE);
            zhengchang.setBackgroundResource(R.drawable.chuji_cri);
            zhengchang.setTextColor(mContext.getColor(R.color.text_white_color));
            guzhang.setBackgroundResource(R.drawable.gray_stoke);
            guzhang.setTextColor(mContext.getColor(R.color.title_color));
        }else if (item.getStatus()==2){
            yv.setVisibility(View.VISIBLE);
            zhengchang.setBackgroundResource(R.drawable.gray_stoke);
            zhengchang.setTextColor(mContext.getColor(R.color.title_color));
            guzhang.setBackgroundResource(R.drawable.guzhang_xunjian);
            guzhang.setTextColor(mContext.getColor(R.color.text_white_color));
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.xunjianok);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            guzhang.setCompoundDrawables(drawable, null, null, null);
        }else {
            yv.setVisibility(View.VISIBLE);
            yv.setText("已处理");
            yv.setEnabled(false);
            yv.setBackgroundResource(R.drawable.xunjian_chuli);
            yv.setTextColor(mContext.getColor(R.color.text_bg_color));
            zhengchang.setBackgroundResource(R.drawable.xunjian_zhengc);
            zhengchang.setTextColor(mContext.getColor(R.color.text_white_color));
            guzhang.setBackgroundResource(R.drawable.guzhang);
            guzhang.setTextColor(mContext.getColor(R.color.security_tab_red));
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.xunjianok);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            guzhang.setCompoundDrawables(drawable, null, null, null);
        }

        zhengchang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 1;
                yv.setVisibility(View.GONE);
                zhengchang.setBackgroundResource(R.drawable.chuji_cri);
                zhengchang.setTextColor(mContext.getColor(R.color.text_white_color));
                guzhang.setBackgroundResource(R.drawable.gray_stoke);
                guzhang.setTextColor(mContext.getColor(R.color.title_color));
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.guzhangs);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                Drawable drawable2 = mContext.getResources().getDrawable(R.drawable.zhengchangwhite);
                drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                guzhang.setCompoundDrawables(drawable, null, null, null);
                zhengchang.setCompoundDrawables(drawable2, null, null, null);
                datas.guzhang(type, helper.getLayoutPosition());
            }
        });
        guzhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 2;
                yv.setVisibility(View.VISIBLE);
                yv.setText("上传");
                yv.setEnabled(true);
                zhengchang.setBackgroundResource(R.drawable.gray_stoke);
                zhengchang.setTextColor(mContext.getColor(R.color.title_color));
                guzhang.setBackgroundResource(R.drawable.guzhang_xunjian);
                guzhang.setTextColor(mContext.getColor(R.color.text_white_color));
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.xunjianok);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                Drawable drawable2 = mContext.getResources().getDrawable(R.drawable.zhengcah);
                drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                guzhang.setCompoundDrawables(drawable, null, null, null);
                zhengchang.setCompoundDrawables(drawable2, null, null, null);
                datas.guzhang(type, helper.getLayoutPosition());

            }
        });
    }
}
