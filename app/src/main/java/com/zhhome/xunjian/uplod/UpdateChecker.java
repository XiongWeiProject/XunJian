package com.zhhome.xunjian.uplod;

/**
 * Created by l on 2017/7/17.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.content.FileProvider;

import com.alibaba.fastjson.JSON;
import com.zhhome.xunjian.BuildConfig;
import com.zhhome.xunjian.http.HttpHelper;
import com.zhhome.xunjian.http.HttpUrl;
import com.zhhome.xunjian.http.MySubscriber;
import com.zhhome.xunjian.http.baserx.RxHelper;
import com.zhhome.xunjian.model.AppVersion;
import com.zhhome.xunjian.utils.AlertUtils;

import java.io.File;

public class UpdateChecker {
    private static final String TAG = "UpdateChecker";
    private Context mContext;
    //检查版本信息的线程
    private Thread mThread;

    //下载apk的对话框
    private ProgressDialog mProgressDialog;

    private File apkFile;
    private int visncode;
    private String downUrl;
    private String msgs;
    private String qiangzi;


    public UpdateChecker(Context context) {
        mContext = context;
        // instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("正在更新...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
        mProgressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // TODO Auto-generated method stub

            }
        });
    }
    public void  dissdalog(){
        mProgressDialog.dismiss();
    }

    public void checkForUpdates() {
        int versionCode = 0;
        try {
            versionCode = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
            getuploud(versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void getuploud(int versionCode) {
        HttpHelper.getDefault()
                .appuploud(HttpUrl.getAPPurlUrl,
                        9 + "",versionCode+"")
                .compose(RxHelper.handleResult())
                .subscribe(new MySubscriber<String>(mContext) {
                    @Override
                    protected void _onError(String code, String msg) {
                        AlertUtils.showToast(mContext,msg);
                    }

                    @Override
                    protected void _onNext(String s) {
                        AppVersion appVersion = JSON.parseObject(s, AppVersion.class);
                        visncode = appVersion.getVersion_code();
                        downUrl = appVersion.getApk_file_url();
                        try {
                            int versionCode = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
                            if (visncode > versionCode) {
                                downLoadApk();
                                // showForceUpdateDialog();
                            } else {
                                //Toast.makeText(mContext, "已经是最新版本", Toast.LENGTH_SHORT).show();
                            }
                        } catch (PackageManager.NameNotFoundException ignored) {
                            //
                        }


                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }


    private void downLoadApk() {
        String apkUrl = downUrl;
        String dir = mContext.getExternalFilesDir("apk").getAbsolutePath();
        File folder = Environment.getExternalStoragePublicDirectory(dir);
        if (folder.exists() && folder.isDirectory()) {
            //do nothing
        } else {
            folder.mkdirs();
        }
        String filename = apkUrl.substring(apkUrl.lastIndexOf("/"), apkUrl.length());
        String destinationFilePath = dir + "/" + filename;
        apkFile = new File(destinationFilePath);
        if ("true".equals(qiangzi)) {
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.show();
        Intent intent = new Intent(mContext, DownloadService.class);
        intent.putExtra("url", apkUrl);
        intent.putExtra("dest", destinationFilePath);
        intent.putExtra("receiver", new DownloadReceiver(new Handler()));
        mContext.startService(intent);
    }


    private class DownloadReceiver extends ResultReceiver {
        public DownloadReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == DownloadService.UPDATE_PROGRESS) {
                int progress = resultData.getInt("progress");
                mProgressDialog.setProgress(progress);
                if (progress == 100) {
                    mProgressDialog.dismiss();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    //判断是否是Android7.0以及更高的版本
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        Uri contentUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".fileprovider", apkFile);
                        intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                    }else{
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setDataAndType(Uri.parse("file://" + apkFile.getPath()), "application/vnd.android.package-archive");
                    }
                    mContext.startActivity(intent);


                }
            }
        }
    }
}