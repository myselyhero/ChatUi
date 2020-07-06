package com.gy.wyy.chat.ui.voice;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.gy.wyy.chat.ui.R;
import com.gy.wyy.chat.ui.tool.AudioPlayer;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 */
public class VoiceFrameLayout extends LinearLayout {

    /**
     * 最小滑动距离
     */
    private float minMove = 50;

    private TextView tipsTextView;

    //取消按钮
    private ImageView cancelButton;
    private boolean mCancelExecuteAnimation;

    //开始
    private Button startButton;
    //起始坐标
    private float mStartY;
    private float mStartX;

    //语音转文字
    private ImageView translateButton;
    private boolean mTranslateAnimation;

    /**
     * 放大动画
     */
    private Animation amplificationAnimation;

    /**
     * 缩小动画
     */
    private Animation shrinkAnimation;

    /**
     * 点击开始按钮的动画
     */
    private Animation startAnimation;

    private OnVoiceHandler voiceHandler;

    /**
     * 是否正在录音
     */
    private boolean isRecord;

    public VoiceFrameLayout(@NonNull Context context) {
        super(context);
        initView();
    }

    public VoiceFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public VoiceFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     *
     */
    private void initView(){
        LayoutInflater.from(getContext()).inflate(R.layout.voice_frame_layout,this);
        tipsTextView = findViewById(R.id.voice_frame_tips);
        cancelButton = findViewById(R.id.voice_frame_cancel);
        startButton = findViewById(R.id.voice_frame_start);
        translateButton = findViewById(R.id.voice_frame_translate);

        /**
         * 初始化动画
         */
        amplificationAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.voice_amplification_anim);
        shrinkAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.voice_shrink_anim);
        startAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.voice_start_anim);

        startButton.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        if (!checkAudioPermission()){
                            if (voiceHandler != null){
                                voiceHandler.notPermission();
                            }
                            return false;
                        }

                        mStartY = motionEvent.getY();
                        mStartX = motionEvent.getX();
                        startButton.startAnimation(startAnimation);
                        startButton.setBackground(getResources().getDrawable(R.drawable.ic_voice_start_white));

                        if (voiceHandler != null)
                            voiceHandler.startRecording();
                        isRecord = true;
                        AudioPlayer.getInstance().startRecord(audioRecordCallback);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (motionEvent.getX() - mStartX > minMove && (Math.abs(motionEvent.getX() - mStartX) > minMove)) {
                            tipsTextView.setText(getResources().getString(R.string.voice_translate));
                            //向右
                            if (mTranslateAnimation){
                                return false;
                            }
                            clearAnimation();
                            mTranslateAnimation = true;
                            translateButton.startAnimation(amplificationAnimation);
                            translateButton.setBackground(getResources().getDrawable(R.drawable.voice_button_blue));
                            translateButton.setImageResource(R.drawable.ic_voice_cn_white);
                        } else if (motionEvent.getX() - mStartX < -100 && (Math.abs(motionEvent.getX() - mStartX) > minMove)) {
                            tipsTextView.setText(getResources().getString(R.string.voice_cancel));
                            //向左
                            if (mCancelExecuteAnimation){
                                return false;
                            }
                            clearAnimation();
                            mCancelExecuteAnimation = true;
                            cancelButton.startAnimation(amplificationAnimation);
                            cancelButton.setBackground(getResources().getDrawable(R.drawable.voice_button_red));
                            cancelButton.setImageResource(R.drawable.ic_voice_delete_white);
                        } else if (motionEvent.getY() - mStartY > minMove && (Math.abs(motionEvent.getY() - mStartY) > minMove)) {
                            //向下
                            clearAnimation();
                            //tipsTextView.setText(getResources().getString(R.string.voice_pressed));
                        } else if (motionEvent.getY() - mStartY < -100 && (Math.abs(motionEvent.getY() - mStartY) > minMove)) {
                            //向上
                            clearAnimation();
                            //tipsTextView.setText(getResources().getString(R.string.voice_pressed));
                        }else {
                            clearAnimation();
                            //tipsTextView.setText(getResources().getString(R.string.voice_pressed));
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        isRecord = false;
                        if (voiceHandler != null){
                            AudioPlayer.getInstance().stopRecord();
                            String path = AudioPlayer.getInstance().getRecordAudioPath();
                            if (mCancelExecuteAnimation){//在左边抬起手指

                            }else if (mTranslateAnimation){//右边抬起手指

                            }else {

                            }
                        }
                        clearAnimation();
                        startButton.setBackground(getResources().getDrawable(R.drawable.ic_voice_start_black));
                        tipsTextView.setText(getResources().getString(R.string.voice_normal));
                        break;
                }
                return false;
            }
        });
    }

    /**
     *
     */
    public void clearAnimation(){
        if (mTranslateAnimation){
            mTranslateAnimation = false;
            translateButton.startAnimation(shrinkAnimation);
            translateButton.setImageResource(R.drawable.ic_voice_cn_black);
            translateButton.setBackground(getResources().getDrawable(R.drawable.voice_button_black));
        }
        if (mCancelExecuteAnimation){
            mCancelExecuteAnimation = false;
            cancelButton.startAnimation(shrinkAnimation);
            cancelButton.setImageResource(R.drawable.ic_voice_delete_black);
            cancelButton.setBackground(getResources().getDrawable(R.drawable.voice_button_black));
        }
    }

    /**
     *
     * @param voiceHandler
     */
    public void setVoiceHandler(OnVoiceHandler voiceHandler) {
        this.voiceHandler = voiceHandler;
    }

    /**
     *
     */
    private AudioPlayer.AudioRecordCallback audioRecordCallback = new AudioPlayer.AudioRecordCallback() {
        @Override
        public void recordComplete(long duration) {
            clearAnimation();
            startButton.setBackground(getResources().getDrawable(R.drawable.ic_voice_start_black));
            tipsTextView.setText(getResources().getString(R.string.voice_normal));
            isRecord = false;
            if (voiceHandler != null){
                voiceHandler.completeRecording(duration);
            }
        }

        @Override
        public void recordSchedule(String date) {
            post(new Runnable() {
                @Override
                public void run() {
                    if (isRecord && !mTranslateAnimation && !mCancelExecuteAnimation){
                        tipsTextView.setText(date);
                    }
                }
            });
        }
    };

    /**
     *
     */
    public interface OnVoiceHandler {

        /**
         *
         */
        void notPermission();

        /**
         * 开始录音
         */
        void startRecording();

        /**
         * 完成录音
         * @param d 录音时长
         */
        void completeRecording(long d);
    }

    /**
     *
     * @return
     */
    protected boolean checkAudioPermission() {
        boolean flag = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO)) {
                flag = false;
            }
        }
        return flag;
    }
}