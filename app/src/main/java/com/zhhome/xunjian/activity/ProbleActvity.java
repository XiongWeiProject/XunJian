package com.zhhome.xunjian.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigButton;
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.zhhome.xunjian.R;
import com.zhhome.xunjian.adapter.MyItemTouchHelperCallback;
import com.zhhome.xunjian.adapter.SendImageAdapter;
import com.zhhome.xunjian.http.HttpUrl;
import com.zhhome.xunjian.manager.MediaManager;
import com.zhhome.xunjian.model.EventBusModel;
import com.zhhome.xunjian.model.VideoJumpModel;
import com.zhhome.xunjian.utils.AlertUtils;
import com.zhhome.xunjian.utils.LogUtil;
import com.zhhome.xunjian.utils.WeiboDialogUtils;
import com.zhhome.xunjian.view.AudioRecorderButton;
import com.zhhome.xunjian.view.CommonEditText;
import com.zhhome.xunjian.view.ErrorDialog;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoActivity;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.TImage;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.model.TakePhotoOptions;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProbleActvity extends TakePhotoActivity implements SendImageAdapter.OnClickListener {


    @Bind(R.id.exit)
    TextView exit;
    @Bind(R.id.shangchauan_sub)
    TextView shangchauanSub;
    @Bind(R.id.id_common_et)
    CommonEditText idCommonEt;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.id_tv_max_num)
    TextView idTvMaxNum;
    @Bind(R.id.id_ll_edit)
    LinearLayout idLlEdit;
    @Bind(R.id.id_recycleview)
    RecyclerView idRecycleview;
    @Bind(R.id.iv_add)
    ImageView ivAdd;
    @Bind(R.id.v_anime)
    View vAnime;
    @Bind(R.id.fl)
    FrameLayout fl;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.recode)
    TextView recode;
    @Bind(R.id.tvYuyin)
    AudioRecorderButton tvYuyin;
    @Bind(R.id.ll_recede)
    LinearLayout llRecede;
    @Bind(R.id.iv_video)
    ImageView ivVideo;
    @Bind(R.id.im_deletes)
    ImageView imDeletes;
    @Bind(R.id.id_rl_vodeo)
    FrameLayout idRlVodeo;
    @Bind(R.id.tv_uploude)
    TextView tvUploude;
    private SendImageAdapter mPhotoAdapter;
    public static final int maxPhoto = 9; //最大选择几张照片
    private List<TImage> images = new ArrayList<>();
    private List<TImage> imagess;
    TakePhoto takePhoto = getTakePhoto();
    List<TImage> photos = null;
    private Dialog mDialog;
    private int min_length;
    private int max_length;
    private View mAnimView;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final MediaType FROM_DATA = MediaType.parse("multipart/form-data");
    String filePaths = "";
    String serial_id, mobile, action, filevideo, isimage, isnull;
    private final OkHttpClient client = new OkHttpClient();
    private double mLat = -1;
    private boolean isFirstLoc = true; // 是否首次定位
    private double mLon = -1;
    private UiSettings mUiSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proble_actvity);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        serial_id = intent.getStringExtra("serial_id");
        mobile = intent.getStringExtra("mobile");
        action = intent.getStringExtra("action");
        filevideo = intent.getStringExtra("filevideo");
        isimage = intent.getStringExtra("isimage");
        isnull = intent.getStringExtra("null");
        if ("2".equals(isimage) && !"null".equals(isnull)) {
            images = (List<TImage>) intent.getSerializableExtra("images");
            LogUtil.e("imagess", imagess + "imagess");
            if (imagess != null)
                images.addAll(images.size(), images);
        }
        initView();
        initData();
        initLister();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    /**
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void videoCallback(EventBusModel<VideoJumpModel> event) {
        if (event.getTag().equals("video")) {
            String filevideoResult = event.getParams().getFilevideo();
            LogUtil.e("filevideoResult", filevideoResult);
            setAdapterVideoNewData(filevideoResult);
        }
    }

    private void initView() {
        idCommonEt.setHint("描述您发现的问题......");
        tvNum.setText("0");
        idTvMaxNum.setText("/188");
        idCommonEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(188)});
        //initMap();
    }

    private void initData() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        min_length = (int) (metrics.widthPixels * 0.15f);
        max_length = (int) (metrics.widthPixels * 0.72f);
        idRecycleview.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mPhotoAdapter = new SendImageAdapter(ProbleActvity.this, images);
        idRecycleview.setAdapter(mPhotoAdapter);
        mPhotoAdapter.setOnClickListener(this);
        if (!TextUtils.isEmpty(filevideo)) {
            TImage tImage = new TImage();
            tImage.setCompressPath("1");
            images.add(tImage);
            mPhotoAdapter.notifyDataSetChanged();
            //idRlVodeo.setVisibility(View.VISIBLE);
        }
        MyItemTouchHelperCallback callBack = new MyItemTouchHelperCallback(mPhotoAdapter);
        //实现拖拽排序
        final ItemTouchHelper touchHelper = new ItemTouchHelper(callBack);
        //调用ItemTouchHelper的attachToRecyclerView方法建立联系
        touchHelper.attachToRecyclerView(idRecycleview);
    }

    private void setAdapterVideoNewData(String filevideoResult) {
        if (images.size() == 0 || TextUtils.isEmpty(filevideo)) {
            filevideo = filevideoResult; //给视频地址赋值
            TImage tImage = new TImage();
            tImage.setCompressPath("1");
            images.add(tImage);
            mPhotoAdapter.notifyDataSetChanged();
        }
    }

    private void initLister() {
        idCommonEt.setOnTextChaged(new CommonEditText.OnTextChaged() {
            @Override
            public void setText(String s) {
                tvNum.setText(s.length() + "");
            }
        });
        tvYuyin.setAudioStartRecorderListener(new AudioRecorderButton.onAudioStartRecorderListener() {
            @Override
            public void onStart() {
                recode.setText("正在录音..");
            }
        });
        tvYuyin.onAudioShotRecorderListener(new AudioRecorderButton.onAudioShotRecorderListener() {
            @Override
            public void onShot() {
                recode.setText("按住说话");
            }
        });
        tvYuyin.setAudioFinishRecorderListener(new AudioRecorderButton.onAudioFinishRecorderListener() {
            @Override
            public void onFinish(float seconds, String filePath) {
                fl.setVisibility(View.VISIBLE);
                filePaths = filePath;
                recode.setText("录音完成");
                int time = (int) Math.ceil(seconds);
                //注意转义字符
                tvTime.setText(time + "\"");
                ViewGroup.LayoutParams lp = fl.getLayoutParams();
                if (time <= 60) {
                    lp.width = (int) (min_length + max_length * time / 60f);
                } else {
                    lp.width = min_length + max_length;
                }
            }
        });
        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAnimView = view.findViewById(R.id.v_anime);
                mAnimView.setBackgroundResource(R.drawable.voice_animation);
                AnimationDrawable anim = (AnimationDrawable) mAnimView.getBackground();
                anim.start();
                //播放音频,取消动画
                MediaManager.playAudio(filePaths,
                        new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                //播放完成，停止动画效果
                                mAnimView.setBackgroundResource(R.drawable.v_anim3);
                            }
                        });
            }
        });
    }



    //========================= 高德地图==============================
    @OnClick({R.id.exit, R.id.shangchauan_sub, R.id.tvYuyin, R.id.iv_add, R.id.im_deletes})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.exit:
                finish();
                break;
            case R.id.shangchauan_sub:
                requestUploadHead();
                break;
            case R.id.tvYuyin:
                break;
//            case R.id.iv_add:
//                takeCheck();
            //break;
            case R.id.im_deletes:
                idRlVodeo.setVisibility(View.GONE);
                break;
            case R.id.tv_uploude:

                break;
        }
    }

    /**
     * 设置一些amap的属性
     */

    //提交反馈结果

    /**
     * 上传文件
     *
     * @param
     */
    private void requestUploadHead() {
        mDialog = WeiboDialogUtils.createLoadingDialog(ProbleActvity.this, "正在提交..");
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        String desc = idCommonEt.getText().toString().trim();
        if (TextUtils.isEmpty(desc)) {
            WeiboDialogUtils.closeDialog(mDialog);
            ErrorDialog.getInstance(2, "请输入问题描叙").show(getSupportFragmentManager(), "");
            return;
        }
        //录音文件
        File filevide = null;
        File voice = null;
        if (!TextUtils.isEmpty(filePaths)) {
            voice = new File(filePaths);
            LogUtil.e("录音", voice.getName());
            builder.addFormDataPart("voice", voice.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), voice));
        }
        //视频文件
        if (!TextUtils.isEmpty(filevideo)) {
            filevide = new File(filevideo);
            LogUtil.e("视频", filevide.getPath());
            builder.addFormDataPart("video", filevide.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), filevide));
        }
        for (int i = 0; i < images.size(); i++) {
            if (!images.get(i).getCompressPath().equals("1")) { //1 是视频不是图片
                File image = new File(images.get(i).getCompressPath());
                LogUtil.e("图片路径", image.getPath());
                if (image != null) {
                    LogUtil.e("图片", images.size() + "");
                    builder.addFormDataPart("image" + i, image.getName(), RequestBody.create(MEDIA_TYPE_PNG, image));
                }
            }
        }
        if (!TextUtils.isEmpty(filevideo)) { //因为手动添加了一个视频 1 所以要移除
            builder.addFormDataPart("num", images.size() - 1 + ""); //有视频
        } else {
            builder.addFormDataPart("num", images.size() + ""); //无视频
        }

        builder.addFormDataPart("action", action);
        builder.addFormDataPart("desc", desc);
        //添加其它信息
//        builder.addFormDataPart("time",takePicTime);
//        builder.addFormDataPart("mapX", SharedInfoUtils.getLongitude());
//        builder.addFormDataPart("mapY",SharedInfoUtils.getLatitude());
//        builder.addFormDataPart("name",SharedInfoUtils.getUserName());

        MultipartBody requestBody = builder.build();
        //构建请求
        Request request = new Request.Builder()
                .url(HttpUrl.getXunjianProblem + "serial_id/" + serial_id + "/user_id/" + mobile)//地址
                .post(requestBody)//添加请求体
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e("上传失败", e.getMessage());
                WeiboDialogUtils.closeDialog(mDialog);
                ErrorDialog.getInstance(2, "问题上传失败！请重试").show(getSupportFragmentManager(), "");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                WeiboDialogUtils.closeDialog(mDialog);
                LogUtil.e("日志", response.toString());
                ErrorDialog.getInstance(3, "问题上传成功！").show(getSupportFragmentManager(), "");
            }
        });

    }

    private void takeCheck() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);

        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);
        final String[] items = {"拍照", "从相册选择", "拍摄视频"};
        new CircleDialog.Builder(this)
                .configDialog(new ConfigDialog() {
                    @Override
                    public void onConfig(DialogParams params) {
                        //增加弹出动画
                        params.animStyle = R.style.PopWindowAnimStyle;
                    }
                })
                .setTitle("请选择上传方式")
                .setTitleColor(Color.BLUE)
                .setItems(items, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
                                // AlertUtils.showToast(ProbleActvity.this, "点击了拍照");
                                break;
                            case 1:
                                takePic(imageUri);
                                //selectPhoto();
                                break;
                            case 2:
                                //AlertUtils.showToast(ProbleActvity.this, "点击了拍摄视频");

                                Intent intent = new Intent(ProbleActvity.this, ViodeMain.class);
                                if (images.size() != 0) {
                                    LogUtil.e("images", images.size() + "imagess");
                                    intent.putExtra("images", (Serializable) images);
                                }
                                intent.putExtra("serial_id", serial_id);
                                intent.putExtra("mobile", mobile);
                                intent.putExtra("action", action);
                                startActivity(intent);
//                                finish();
                                break;
                        }

                    }
                })
                .setNegative("取消", null)
                .configNegative(new ConfigButton() {
                    @Override
                    public void onConfig(ButtonParams params) {
                        //取消按钮字体颜色
                        params.textColor = Color.RED;
                    }
                })
                .show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //停止播放
        MediaManager.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaManager.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (isRequireCheck) {
//            //权限没有授权，进入授权界面
//            if(mPermissionsChecker.judgePermissions(PERMISSIONS)){
//                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);
//            }
//        }else{
//            isRequireCheck = true;
//        }
        //继续播放
        MediaManager.resume();
    }

    private void takePic(Uri imageUri) {
        int limit = Integer.parseInt("9");
        if (limit > 1) {
            //裁剪
            takePhoto.onPickMultipleWithCrop(limit, getCropOptions());
            return;
        }
        takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
    }


    public void delImages(int position) {
        if ("1".equals(images.get(position).getCompressPath()))
            filevideo = "";
        images.remove(position);
        if (mPhotoAdapter != null)
            mPhotoAdapter.notifyItemRemoved(position);

    }

//    public void clearPhotoS(int size) {
//        LogUtil.e("返回有几张照片", size + "");
//        images.clear();
//        if (!TextUtils.isEmpty(filevideo)){
//            TImage tImage = new TImage();
//            tImage.setCompressPath("1");
//            images.add(tImage);
//            //idRlVodeo.setVisibility(View.VISIBLE);
//        }
//        //images.add(1);
//    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        AlertUtils.showToast(ProbleActvity.this, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        if (result != null) {
            photos = result.getImages();
            LogUtil.e("photos", photos + "");
        }
        if (photos != null) {
//            clearPhotoS(photos.size());
            images.addAll(images.size(), photos);
            LogUtil.e("返回有几张照片", images + "");
            mPhotoAdapter.notifyDataSetChanged();
        }
    }


    private void configCompress(TakePhoto takePhoto) {
        //压缩文件
        int maxSize = Integer.parseInt("102400");
        int width = Integer.parseInt("800");
        int height = Integer.parseInt("800");
        //是否显示亚索进度条
        boolean showProgressBar = true;
        //是否保存原图
        boolean enableRawFile = false;
        CompressConfig config;
        //用自带的亚索软件
        //if (rgCompressTool.getCheckedRadioButtonId() == R.id.rbCompressWithOwn) {
        config = new CompressConfig.Builder().setMaxSize(maxSize)
                .setMaxPixel(width >= height ? width : height)
                .enableReserveRaw(enableRawFile)
                .create();
        //}
//        else {
//            LubanOptions option = new LubanOptions.Builder().setMaxHeight(height).setMaxWidth(width).setMaxSize(maxSize).create();
//            config = CompressConfig.ofLuban(option);
//            config.enableReserveRaw(enableRawFile);
//        }
        takePhoto.onEnableCompress(config, showProgressBar);


    }

    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(true);
        builder.setCorrectImage(true);
        takePhoto.setTakePhotoOptions(builder.create());

    }


    private CropOptions getCropOptions() {
        //裁剪的匡高
        int height = Integer.parseInt("800");
        int width = Integer.parseInt("800");
        //是否是用第三方裁剪工具
        boolean withWonCrop = true;

        CropOptions.Builder builder = new CropOptions.Builder();

        builder.setAspectX(width).setAspectY(height);

        builder.setWithOwnCrop(withWonCrop);
        return builder.create();
    }

    @Override
    public void onClick(View v, int position) {
        switch (v.getId()) {
            case R.id.iv_add:
                //selectPhoto();
                takeCheck();
                break;
            case R.id.im_delete:
                delImages(position);
                break;
            case R.id.iv_img:
                break;

        }
    }
}
