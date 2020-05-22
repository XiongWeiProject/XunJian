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
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.mylhyl.circledialog.BaseCircleDialog;
import com.mylhyl.circledialog.res.values.CircleDimen;
import com.zhhome.xunjian.R;
import com.zhhome.xunjian.http.HttpHelper;
import com.zhhome.xunjian.http.HttpUrl;
import com.zhhome.xunjian.http.MySubscriber;
import com.zhhome.xunjian.http.baserx.RxHelper;
import com.zhhome.xunjian.model.XunjianModel;

import java.util.Timer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by h on 2017/8/20.
 */
@SuppressLint("ValidFragment")
public class XunjianResultDialog extends BaseCircleDialog {

    @Bind(R.id.iv_cance)
    ImageView ivCance;
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
    @Bind(R.id.erweima_text)
    TextView erweimaText;
    @Bind(R.id.tv_ok)
    TextView tvOk;
    private View mView;
    private TextView i_know, i_wiring1, i_wiring2;
    private int typeid = 0;
    private String code;
    private int type;
    private static Timer Delaytimer;
    private XunjianModel xunjianModelss = new XunjianModel();
    private FragmentManager mContext;
    String jiqimas;
    public XunjianResultDialog(String jiqima,String moble) {
        jiqimas = jiqima;
        code =moble;
    }

    public static XunjianResultDialog getInstance(String jiqima,String moble) {
        XunjianResultDialog dialogFragment = new XunjianResultDialog(jiqima,moble);
        dialogFragment.setCanceledBack(true);
        dialogFragment.setCanceledOnTouchOutside(false);
        dialogFragment.setRadius(CircleDimen.RADIUS);
        dialogFragment.setWidth(0.6f);
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
        if (mView == null) {
            mView = inflater.inflate(R.layout.cumstom_xunjian_result, container, false);
        }
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getXunjianList();
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

    @OnClick({R.id.iv_cance, R.id.tv_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_cance:
                dismiss();
                break;
            case R.id.tv_ok:
                postDown();
                break;
        }
    }

    private void postDown() {
        HttpHelper.getDefault()
                .getXunjianlist(HttpUrl.getXunjianDown + "/androidCount/1/serial_id/" + jiqimas + "/mobile/" + code)
                .compose(RxHelper.handleResult())
                .subscribe(new MySubscriber<String>(getActivity()) {
                    @Override
                    protected void _onError(String code, String msg) {
                        dismiss();
                        ErrorDialog.getInstance(4, msg).show(mContext, "");
                    }

                    @Override
                    protected void _onNext(String s) {
                        dismiss();
                        ErrorDialog.getInstance(0, "提交成功").show(mContext, "");
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

    private void getXunjianList() {
        HttpHelper.getDefault()
                .getXunjianlist(HttpUrl.getXunjianList + "/androidCount/1/serial_id/" + jiqimas + "/user_id/" + code)
                .compose(RxHelper.handleResult())
                .subscribe(new MySubscriber<String>(getActivity()) {
                    @Override
                    protected void _onError(String code, String msg) {
                        dismiss();
                        ErrorDialog.getInstance(4, msg).show(mContext, "");
                    }

                    @Override
                    protected void _onNext(String s) {
                        xunjianModelss = JSON.parseObject(s, XunjianModel.class);
                        showData(xunjianModelss);
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }
    //显示数据
    private void showData(XunjianModel xunjianModel) {
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
}
