package com.gy.wyy.chat.player.view;

import android.content.Context;
import android.net.TrafficStats;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.gy.wyy.chat.player.R;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 王永勇
 * @// TODO: 2020/6/28
 */
public class VideoStatusLayout extends LinearLayout {

    private LinearLayout statusBackground;
    private ImageView statusImageView;
    private TextView statusTextView;

    private Animation rotateAnimation;

    private String loaderDefaultHint = "加载中...";
    private String networkDefaultHint = "网络连接断开...";

    private VideoStatusEnum statusEnum;

    /**
     * 网速
     */
    private Timer networkTimer;
    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;

    public VideoStatusLayout(Context context) {
        super(context);
        initView();
    }

    public VideoStatusLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public VideoStatusLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     *
     */
    private void initView(){
        LayoutInflater.from(getContext()).inflate(R.layout.video_status_layout,this);
        statusBackground = findViewById(R.id.video_status_layout);
        statusImageView = findViewById(R.id.video_status_layout_iv);
        statusTextView = findViewById(R.id.video_status_layout_tv);

        rotateAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.player_loader_anim);
    }

    /**
     *
     */
    public void ok(){
        statusEnum = VideoStatusEnum.OK;
        statusImageView.clearAnimation();
        setVisibility(View.GONE);
        if (networkTimer != null){
            networkTimer.cancel();
            networkTimer = null;
        }
    }

    /**
     *
     */
    public void stop(){
        statusEnum = VideoStatusEnum.STOP;
        setVisibility(View.VISIBLE);
        setBackgroundColor(getContext().getResources().getColor(R.color.transparent));
        statusImageView.clearAnimation();
        statusImageView.setImageResource(R.drawable.player_start);
        statusTextView.setVisibility(View.GONE);
    }

    /**
     *
     */
    public void loader(){
        statusEnum = VideoStatusEnum.LOADER;
        setVisibility(View.VISIBLE);
        setBackground(getContext().getResources().getDrawable(R.drawable.video_status_layout_background));
        statusImageView.setImageResource(R.drawable.player_loader);
        statusImageView.startAnimation(rotateAnimation);
        if (networkTimer == null){
            networkTimer = new Timer();
            networkTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    post(new Runnable() {
                        @Override
                        public void run() {
                            statusTextView.setText(networkSpeed());
                        }
                    });
                }
            }, 0, 1000);
        }
        if (statusTextView.getVisibility() == View.GONE){
            statusTextView.setVisibility(View.VISIBLE);
        }
    }

    /**
     *
     */
    public void networkError(){
        statusEnum = VideoStatusEnum.NETWORK;
        setVisibility(View.VISIBLE);
        setBackground(getContext().getResources().getDrawable(R.drawable.video_status_layout_background));
        statusImageView.clearAnimation();
        statusImageView.setImageResource(R.drawable.player_network);
        statusTextView.setText(networkDefaultHint);
        if (statusTextView.getVisibility() == View.GONE){
            statusTextView.setVisibility(View.VISIBLE);
        }
    }

    /**
     *
     * @return
     */
    public VideoStatusEnum getStatusEnum() {
        return statusEnum;
    }

    /**
     *
     */
    private String networkSpeed() {
        long nowTotalRxBytes = TrafficStats.getUidRxBytes(getContext().getApplicationInfo().uid) == TrafficStats.UNSUPPORTED ? 0 :(TrafficStats.getTotalRxBytes()/1024);
        long nowTimeStamp = System.currentTimeMillis();
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换

        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;

        return speed +" KB/S";
    }

    /**
     *
     */
    public enum VideoStatusEnum {
        OK,
        LOADER,
        STOP,
        NETWORK
    }
}
