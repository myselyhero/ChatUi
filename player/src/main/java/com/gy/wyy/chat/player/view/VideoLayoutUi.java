package com.gy.wyy.chat.player.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gy.wyy.chat.player.R;
import com.gy.wyy.chat.player.manager.PlayerManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 王永勇
 * @// TODO: 2020/6/28
 */
public class VideoLayoutUi extends LinearLayout {

    protected SurfaceHolder mSurfaceHolder;
    protected SurfaceView mSurfaceView;

    protected ImageView mThumbnailImageVew;

    protected LinearLayout topBackground;
    protected LinearLayout topLeftGroup;
    protected ImageView topLeftGroupIcon;
    protected TextView topLeftGroupText;

    protected LinearLayout topRightGroup;
    protected ImageView topRightGroupShare;
    protected ImageView topRightGroupMore;
    protected LinearLayout topRightGroupInfo;
    protected ImageView topRightGroupInfoElectric;
    protected TextView topRightGroupInfoTime;

    protected LinearLayout bottomBackground;
    protected ImageView bottomVoiceIcon;
    protected TextView bottomCurrentText;
    protected SeekBar bottomSeekBar;
    protected TextView bottomTotalText;
    protected ImageView bottomScreenIcon;

    protected VideoStatusLayout mStatusLayout;

    protected VideoSpeedLayout mSpeedLayout;

    protected VideoVolumeAndLuminanceLayout mVideoVolumeAndLuminanceLayout;

    /**
     * 计时器
     */
    private Timer topTimer;
    private Timer bottomTimer;

    /**
     *
     */
    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    break;
            }
        }
    };

    /**
     * 是否为全屏状态
     */
    protected boolean isFullScreen;

    /**
     * 电池电量广播接收者
     */
    private BatteryBoradcasReceiver batteryReceiver;

    /**
     * 网络状态广播
     */
    private NetworkBroadcastReceiver networkBroadcastReceiver;

    public VideoLayoutUi(Context context) {
        super(context);
        initView();
    }

    public VideoLayoutUi(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public VideoLayoutUi(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     *
     */
    private void initView(){
        LayoutInflater.from(getContext()).inflate(R.layout.video_layout_ui,this);

        mSurfaceView = findViewById(R.id.video_layout_surface);
        mThumbnailImageVew = findViewById(R.id.video_layout_thumbnail);
        topBackground = findViewById(R.id.video_layout_top);
        topLeftGroup = findViewById(R.id.video_layout_left_group);
        topLeftGroupIcon = findViewById(R.id.video_layout_left_group_iv);
        topLeftGroupText = findViewById(R.id.video_layout_left_group_tv);
        topRightGroup = findViewById(R.id.video_layout_right_group);
        topRightGroupShare = findViewById(R.id.video_layout_right_group_share);
        topRightGroupMore = findViewById(R.id.video_layout_right_group_more);
        topRightGroupInfo = findViewById(R.id.video_layout_right_group_info);
        topRightGroupInfoElectric = findViewById(R.id.video_layout_right_group_electric);
        topRightGroupInfoTime = findViewById(R.id.video_layout_right_group_time);
        bottomBackground = findViewById(R.id.video_layout_bottom);
        bottomVoiceIcon = findViewById(R.id.video_layout_bottom_voice);
        bottomCurrentText = findViewById(R.id.video_layout_bottom_current);
        bottomSeekBar = findViewById(R.id.video_layout_bottom_bar);
        bottomTotalText = findViewById(R.id.video_layout_bottom_total);
        bottomScreenIcon = findViewById(R.id.video_layout_bottom_screen);
        mStatusLayout = findViewById(R.id.video_layout_status);
        mVideoVolumeAndLuminanceLayout = findViewById(R.id.video_layout_volume_and_luminance);
        mSpeedLayout = findViewById(R.id.video_layout_speed);

        topRightGroupInfoTime.setText(getCurrentTime());

        register();
    }

    /* 内部API */
    public static String getCurrentTime() {
        SimpleDateFormat HHmm = new SimpleDateFormat("HH:mm");
        return HHmm.format(new Date());
    }

    /* 下列方法在VideoLayout中引用 */

    /**
     * 设置静音状态
     * @param volume
     */
    protected void isMute(int volume){
        if (volume > 0){
            bottomVoiceIcon.setImageResource(R.drawable.player_voice);
        }else {
            bottomVoiceIcon.setImageResource(R.drawable.player_mute);
        }
    }


    /**
     *
     */
    protected void startTimer(){
        /* 顶部控制器需在全屏时显示，为了测试暂时放开 */
        if (topTimer == null){
            topTimer = new Timer();
            topTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    post(new Runnable() {
                        @Override
                        public void run() {
                            topRightGroupInfoTime.setText(getCurrentTime());
                        }
                    });
                }
            }, 0, 1000);
        }

        if (bottomTimer == null){
            bottomTimer = new Timer();
            bottomTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    post(new Runnable() {
                        @Override
                        public void run() {
                            if (PlayerManager.getInstance().getCurrentPosition() != -1){
                                bottomSeekBar.setProgress(PlayerManager.getInstance().getCurrentPosition());
                                bottomCurrentText.setText(longTimeToString(PlayerManager.getInstance().getCurrentPosition()));
                            }
                            if (PlayerManager.getInstance().getDuration() != -1){
                                bottomTotalText.setText(longTimeToString(PlayerManager.getInstance().getDuration()));
                            }
                        }
                    });
                }
            }, 0, 500);
        }
    }

    /**
     *
     */
    protected void destroyTimer(){
        if (topTimer != null){
            topTimer.cancel();
            topTimer = null;
        }
        if (bottomTimer != null){
            bottomTimer.cancel();
            bottomTimer = null;
        }
    }

    /**
     * 转换毫秒数成“分、秒”，如“01:53”。若超过60分钟则显示“时、分、秒”，如“01:01:30
     * @param time
     * @return
     */
    protected String longTimeToString(long time) {
        int second = 1000;
        int minute = second * 60;
        int hour = minute * 60;

        long hourTime = (time) / hour;
        long minuteTime = (time - hourTime * hour) / minute;
        long secondTime = (time - hourTime * hour - minuteTime * minute) / second;

        String strHour = hourTime < 10 ? "0" + hourTime : "" + hourTime;
        String strMinute = minuteTime < 10 ? "0" + minuteTime : "" + minuteTime;
        String strSecond = secondTime < 10 ? "0" + secondTime : "" + secondTime;
        if (hourTime > 0) {
            return strHour + ":" + strMinute + ":" + strSecond;
        } else {
            return strMinute + ":" + strSecond;
        }
    }

    /**
     *
     */
    protected void register(){
        //&& isFullScreen
        if (batteryReceiver == null){
            IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            batteryReceiver = new BatteryBoradcasReceiver();
            getContext().registerReceiver(batteryReceiver, intentFilter);
        }

        if (networkBroadcastReceiver == null){
            IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            networkBroadcastReceiver = new NetworkBroadcastReceiver();
            getContext().registerReceiver(networkBroadcastReceiver, intentFilter);
        }
    }

    /**
     *
     */
    protected void unRegister(){
        if (batteryReceiver != null) {
            getContext().unregisterReceiver(batteryReceiver);
        }
        if (networkBroadcastReceiver != null){
            getContext().unregisterReceiver(networkBroadcastReceiver);
        }
    }


    /* 广播 */

    /**
     * 电池电量广播
     */
    private class BatteryBoradcasReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //判断它是否是为电量变化的Broadcast Action
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                //获取当前电量
                int level = intent.getIntExtra("level", 0);
                //电量的总刻度
                int scale = intent.getIntExtra("scale", 100);
                int battery = (level * 100) / scale;

                /**
                 * 判断手机是否正在充电
                 */
                IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                Intent batteryStatus = context.registerReceiver(null, intentFilter);
                int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;
                if (isCharging){
                    topRightGroupInfoElectric.setImageResource(R.drawable.player_electric60);
                }else if (battery > 0 && battery < 20) {
                    topRightGroupInfoElectric.setImageResource(R.drawable.player_electric10);
                } else if (battery >= 20 && battery < 40) {
                    topRightGroupInfoElectric.setImageResource(R.drawable.player_electric20);
                } else if (battery >= 40 && battery < 65) {
                    topRightGroupInfoElectric.setImageResource(R.drawable.player_electric30);
                } else if (battery >= 65 && battery < 90) {
                    topRightGroupInfoElectric.setImageResource(R.drawable.player_electric40);
                } else if (battery >= 90 && battery <= 100) {
                    topRightGroupInfoElectric.setImageResource(R.drawable.player_electric50);
                }
            }
        }
    }

    /**
     * 网络状态广播
     */
    private class NetworkBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            //
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (NetworkInfo.State.CONNECTED == netInfo.getState()) {
                if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) { //WiFi网络
                } else if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {   //3g网络
                } else {//其他
                }
            } else {//断开
            }
        }
    }
}
