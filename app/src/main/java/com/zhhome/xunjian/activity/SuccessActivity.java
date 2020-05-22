package com.zhhome.xunjian.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.zhhome.xunjian.R;
import com.zhhome.xunjian.model.EventBusModel;
import com.zhhome.xunjian.model.VideoJumpModel;
import com.zhhome.xunjian.utils.LogUtil;

import org.devio.takephoto.model.TImage;
import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class SuccessActivity extends Activity implements OnClickListener {

    private TextView text;//视频保存的路径
    private Button button1;//播放开关
    private Button button2;//暂停开关
    private Button button3;//重新播放开关
    private Button button4;//视频大小开关
    private TextView button5;//确定
    private VideoView videoView1;//视频播放控件
    private String file;//视频路径
    String serial_id,mobile,action;
    private List<TImage> images ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        Intent intent = getIntent();
        file = intent.getStringExtra("text");//获得拍摄的短视频保存地址
        images = (List<TImage>)intent.getSerializableExtra("images");
        serial_id = intent.getStringExtra("serial_id");
        mobile = intent.getStringExtra("mobile");
        action = intent.getStringExtra("action");

        init();
        setValue();
    }

    //初始化
    private void init() {
        text = (TextView) findViewById(R.id.text);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (TextView) findViewById(R.id.button5);
        videoView1 = (VideoView) findViewById(R.id.videoView1);
    }

    //设置
    private void setValue() {
        text.setText(file);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        videoView1.setVideoPath(file);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                videoView1.start();
                break;

            case R.id.button2:
                videoView1.pause();
                break;

            case R.id.button3:
                videoView1.resume();
                videoView1.start();
                break;

            case R.id.button4:
                Toast.makeText(this, "视频长度：" + (videoView1.getDuration() / 1024) + "M", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button5:
//                Intent intent = new Intent(SuccessActivity.this,ProbleActvity.class);
//                intent.putExtra("serial_id",serial_id);
//                intent.putExtra("mobile",mobile);
//                intent.putExtra("action",action);
//                intent.putExtra("filevideo",file);
//                intent.putExtra("isimage","2");
//
//                LogUtil.e("images", images + "录制视频成功");
//                if (images == null){
//                    intent.putExtra("null","null");
//                }
//                intent.putExtra("images", (Serializable) images);
//                startActivity(intent);
//                finish();

                VideoJumpModel vj = new VideoJumpModel();
                vj.setSerial_id(serial_id);
                vj.setMobile(mobile);
                vj.setAction(action);
                vj.setFilevideo(file);
                vj.setIsimage("2");
                if (images == null){
                    vj.setNul("null");
                }
                LogUtil.e("images", images + "录制视频成功");
                vj.setImages(images);
                EventBus.getDefault().post(new EventBusModel<VideoJumpModel>("video", vj));
                finish();
                break;

            default:
                break;
        }
    }

}
