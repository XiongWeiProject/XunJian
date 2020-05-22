package com.zhhome.xunjian.view;

import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.zhhome.xunjian.R;
import com.zhhome.xunjian.manager.DialogManager;
import com.zhhome.xunjian.manager.MediaRecorderManager;
import com.zhhome.xunjian.utils.DensityUtil;

/**
 * Author:      WW
 * Date:        2018/3/13 16:35
 * Description: This is AudioRecorderButton
 */

public class AudioRecorderButton extends android.support.v7.widget.AppCompatButton implements MediaRecorderManager.IRecorderStateListener {
    private Context mContext;

    private Paint mRectPaint;

    private Paint mCirclePaint;

    private float corner;
    private float circleRadius;
    private float circleStrokeWidth;
    private float rectWidth;

    private float mMinCircleRadius;
    private float mMaxCircleRadius;
    private float mMinRectWidth;
    private float mMaxRectWidth;
    private float mMinCorner;
    private float mMaxCorner;
    private float mMinCircleStrokeWidth;
    private float mMaxCircleStrokeWidth;
    private float mInitX;

    private float mInfectionPoint;
    private float mInitY;

    private float mDownRawX;

    private float mDownRawY;

    private RectF mRectF = new RectF();
    private RecordMode mRecordMode = RecordMode.ORIGIN;

    private AnimatorSet mBeginAnimatorSet = new AnimatorSet();

    private AnimatorSet mEndAnimatorSet = new AnimatorSet();

    private Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
    /**
     * 三种状态：正常、录音、想结束
     */
    private static final int STATE_NORMAL = 1;
    private static final int STATE_RECORDING = 2;
    private static final int STATE_WANT_CANCEL = 3;
    /**
     * 当前状态
     */
    private static int mCurState = STATE_NORMAL;
    /**
     * 判断是否已经开始录音
     */
    private boolean isRecording = false;
    /**
     * 需转dp ---未转
     */
    private boolean mHasCancel = false;
    private int DISTANCE_Y_CAL = 80;

    private DialogManager mDialogManager;

    private MediaRecorderManager mMediaRecorderManager;

    private static final int MSG_RECORDER_PREPARED = 100;
    private static final int MSG_VOICE_CHANGED = 101;
    private static final int MSG_DIALOG_DIMISS = 102;
    /**
     * 语音等级判断
     */
    private static final int MAX_VOICE_LEVEL = 7;
    /**
     * 计时
     */
    private float mTime;
    /**
     * onclickc是否触发
     */
    private boolean mReady;

    private onAudioFinishRecorderListener mAudioFinishRecorderListener;
    private onAudioStartRecorderListener mAudioStartRecorderListener;
    private onAudioShotRecorderListener mAudioShotRecorderListener;

    public void setAudioFinishRecorderListener(onAudioFinishRecorderListener mAudioFinishRecorderListener) {
        this.mAudioFinishRecorderListener = mAudioFinishRecorderListener;
    }
    public void setAudioStartRecorderListener(onAudioStartRecorderListener mAudioStartRecorderListener) {
        this.mAudioStartRecorderListener = mAudioStartRecorderListener;
    }
    public void onAudioShotRecorderListener(onAudioShotRecorderListener mAudioShotRecorderListener) {
        this.mAudioShotRecorderListener = mAudioShotRecorderListener;
    }
    /**
     * 获取音量大小和计时功能的的runable
     */
    private Runnable mGetVoiceLevelRunable = new Runnable() {
        @Override
        public void run() {
            while (isRecording) {
                try {
                    Thread.sleep(100);
                    mTime += 0.1f;
                    mHandler.sendEmptyMessage(MSG_VOICE_CHANGED);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_RECORDER_PREPARED:
                    //真正显示在audio prepared以后
                    mDialogManager.showRecordingDialog();
                    isRecording = true;
                    //获取音量级别的线程
                    new Thread(mGetVoiceLevelRunable).start();
                    break;
                case MSG_VOICE_CHANGED:
                    mDialogManager.updateVoiceLevel(mMediaRecorderManager.getVoiceLevel(MAX_VOICE_LEVEL));
                    break;
                case MSG_DIALOG_DIMISS:
                    mDialogManager.dimissDialog();
                    break;
                default:
                    break;
            }
        }
    };

    public AudioRecorderButton(Context context) {
        this(context, null);
    }

    public AudioRecorderButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context);
    }

    /**
     * 初始化
     */
    private void init(Context context) {
        setLayerType(LAYER_TYPE_HARDWARE, null);
        mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectPaint.setStyle(Paint.Style.FILL);
        mRectPaint.setColor(getResources().getColor(R.color.text_line_button));

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(Color.parseColor("#0095fd"));

        mMinCircleStrokeWidth = DensityUtil.dip2px(mContext, 3);
        mMaxCircleStrokeWidth = DensityUtil.dip2px(mContext, 12);
        circleStrokeWidth = mMinCircleStrokeWidth;
        mCirclePaint.setStrokeWidth(circleStrokeWidth);
        mDialogManager = new DialogManager(context);
        //TODO 判断外部存储卡是否存在，是否可读写
        String dir = Environment.getExternalStorageDirectory() + "/ww_audio";
        //MediaRecorderManager初始化
        mMediaRecorderManager = MediaRecorderManager.getInstance(dir);
        mMediaRecorderManager.setIRecorderStateListener(this);
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mReady = true;
                mMediaRecorderManager.prepareRecorder();
                return false;
            }
        });
    }
    @Override
    protected void onDraw(Canvas canvas) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int centerX = width / 2;
        int centerY = height / 2;

        mMaxRectWidth = width / 3;
        mMinRectWidth = mMaxRectWidth * 0.6f;

        mMinCircleRadius = mMaxRectWidth / 2 + mMinCircleStrokeWidth + DensityUtil.dip2px(mContext, 5);
        mMaxCircleRadius = width / 2 - mMaxCircleStrokeWidth;

        mMinCorner = DensityUtil.dip2px(mContext, 5);
        mMaxCorner = mMaxRectWidth / 2;

        if (rectWidth == 0) {
            rectWidth = mMaxRectWidth;
        }
        if (circleRadius == 0) {
            circleRadius = mMinCircleRadius;
        }
        if (corner == 0) {
            corner = rectWidth / 2;
        }

        mCirclePaint.setColor(Color.parseColor("#660095fd"));
        canvas.drawCircle(centerX, centerY, circleRadius, mCirclePaint);
        mCirclePaint.setXfermode(mXfermode);

        mCirclePaint.setColor(Color.parseColor("#0095fd"));
        canvas.drawCircle(centerX, centerY, circleRadius - circleStrokeWidth, mCirclePaint);
        mCirclePaint.setXfermode(null);

        mRectF.left = centerX - rectWidth / 2;
        mRectF.right = centerX + rectWidth / 2;
        mRectF.top = centerY - rectWidth / 2;
        mRectF.bottom = centerY + rectWidth / 2;
        canvas.drawRoundRect(mRectF, corner, corner, mRectPaint);
    }

    @Override
    public void wellPrepared() {
        mHandler.sendEmptyMessage(MSG_RECORDER_PREPARED);
    }
    private boolean inBeginRange(MotionEvent event) {
        int centerX = getMeasuredWidth() / 2;
        int centerY = getMeasuredHeight() / 2;
        int minX = (int) (centerX - mMinCircleRadius);
        int maxX = (int) (centerX + mMinCircleRadius);
        int minY = (int) (centerY - mMinCircleRadius);
        int maxY = (int) (centerY + mMinCircleRadius);
        boolean isXInRange = event.getX() >= minX && event.getX() <= maxX;
        boolean isYInRange = event.getY() >= minY && event.getY() <= maxY;
        return isXInRange && isYInRange;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isRecording = true;
                changeState(STATE_RECORDING);
                mRectPaint.setColor(getResources().getColor(R.color.text_line_button1));
                if (mAudioStartRecorderListener != null) {
                    mAudioStartRecorderListener.onStart();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isRecording) {
                    if (wantToCancel(x, y)) {
                        changeState(STATE_WANT_CANCEL);
                        mRectPaint.setColor(getResources().getColor(R.color.text_line_button1));
                    } else {
                        changeState(STATE_RECORDING);
                        mRectPaint.setColor(getResources().getColor(R.color.text_line_button1));
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mRectPaint.setColor(getResources().getColor(R.color.text_line_button));
                if (!mReady) {
                    //onclick未触发
                    if (mAudioShotRecorderListener != null) {
                        mAudioShotRecorderListener.onShot();
                    }
                    reset();
                    return super.onTouchEvent(event);
                }
                if (!isRecording || mTime < 0.6f) {
                    //recorder的prepared未完成就up了 || 时间太短
                    if (mAudioShotRecorderListener != null) {
                        mAudioShotRecorderListener.onShot();
                    }
                    mDialogManager.tooShort();
                    mMediaRecorderManager.cancel();
                    //延迟关闭dialog
                    mHandler.sendEmptyMessageDelayed(MSG_DIALOG_DIMISS, 1300);
                } else if (mCurState == STATE_RECORDING) {

                    //正常录音结束
                    mDialogManager.dimissDialog();
                    mMediaRecorderManager.release();
                    if (mAudioFinishRecorderListener != null) {
                        mAudioFinishRecorderListener.onFinish(mTime,mMediaRecorderManager.getCurrentFilePath());
                    }
                } else if (mCurState == STATE_WANT_CANCEL) {
                    //取消录音
                    mDialogManager.dimissDialog();
                    mMediaRecorderManager.cancel();
                }
                reset();
                Log.e("TAG",isRecording+"");
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
    private enum RecordMode {
        /**
         * 单击录制模式
         */
        SINGLE_CLICK,
        /**
         * 长按录制模式
         */
        LONG_CLICK,
        /**
         * 初始化
         */
        ORIGIN;

        RecordMode() {

        }
    }
    /**
     * 恢复状态及标志位
     */
    private void reset() {
        isRecording = false;
        mTime = 0;
        mReady = false;
        changeState(STATE_NORMAL);
    }

    /**
     * 根据xy坐标判断，是否取消录制
     *
     * @param x
     * @param y
     * @return
     */
    private boolean wantToCancel(int x, int y) {
        if (x < 0 || x > getWidth()) {
            return true;
        } else if (y < -DISTANCE_Y_CAL || y - DISTANCE_Y_CAL > getHeight()) {
            return true;
        }
        return false;
    }

    /**
     * 更改button状态
     *
     * @param state
     */
    private void changeState(int state) {
        if (mCurState != state) {
            //传入状态与当前状态不同，才会changestate
            mCurState = state;
            switch (state) {
                case STATE_NORMAL:
                    //setBackgroundResource(R.drawable.btn_state_normal_bcg);
                    setText(R.string.recorder_normal);
                    break;
                case STATE_RECORDING:
                    //setBackgroundResource(R.drawable.btn_state_recording_bcg);
                    setText(R.string.recorder_recording);
                    if (isRecording) {
                        mDialogManager.recording();
                    }
                    break;
                case STATE_WANT_CANCEL:
                    //setBackgroundResource(R.drawable.btn_state_recording_bcg);
                    setText(R.string.recorder_want_cancel);
                    if (isRecording) {
                        mDialogManager.wantToCancel();
                    }
                    break;
                default:
                    break;
            }
        }
    }
    /**
     * 开始录音的回调
     */
    public interface onAudioStartRecorderListener {
        void onStart();

    }
    /**
     * 没开始或者时间太短
     */
    public interface onAudioShotRecorderListener {
        void onShot();

    }
    /**
     * 录音完成后的回调
     */
    public interface onAudioFinishRecorderListener {
        void onFinish(float seconds, String filePath);

    }
}
