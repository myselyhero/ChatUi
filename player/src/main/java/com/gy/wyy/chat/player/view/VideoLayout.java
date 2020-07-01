package com.gy.wyy.chat.player.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.SeekBar;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.gy.wyy.chat.player.R;
import com.gy.wyy.chat.player.manager.PlayerManager;
import com.gy.wyy.chat.player.manager.PlayerManagerListener;
import com.gy.wyy.chat.player.tool.LuminanceUtils;
import com.gy.wyy.chat.player.tool.ScreenUtil;

/**
 * @author 王永勇
 * @// TODO: 2020/6/28
 */
public class VideoLayout extends VideoLayoutUi implements PlayerManagerListener, View.OnClickListener,
        SeekBar.OnSeekBarChangeListener, GestureDetector.OnGestureListener {

    private String TAG = VideoLayout.class.getSimpleName();

    public static final int VIDEO_LAYOUT_LUMINANCE_PERMISSION_REQUEST_CODE = 101;

    private AttributeSet attributeSet;

    /**
     * 加载视频缩略图默认帧
     */
    private int defaultFrame = 1000000;

    /**
     * 是否显示缩略图
     */
    private boolean isThumbnail;

    /**
     * 是否循环播放
     */
    private boolean isLoop;

    /**
     * 加载完成自动播放
     */
    private boolean isAutoPlayer;



    /**
     * 是否静音,标识是否显示静音/音量按钮
     */
    private boolean isSilence;

    /**
     * 系统的当前音量
     */
    private int oldVolume ;
    private int currentVolume;

    /**
     * 手势监听
     */
    private GestureDetector gestureDetector;

    /**
     * 权限弹窗
     */
    private AlertDialog.Builder mAlertDialog;

    /**
     * 上传一次点击时间
     */
    private long oldClickTime;

    public VideoLayout(Context context) {
        super(context);
        initView();
    }

    public VideoLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (attributeSet == null)
            attributeSet = attrs;
        initView();
    }

    public VideoLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attributeSet == null)
            attributeSet = attrs;
        initView();
    }

    /**
     *
     */
    private void initView(){
        if (attributeSet != null){
            TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.VideoLayout, 0, 0);
            try {
                isThumbnail = typedArray.getBoolean(R.styleable.VideoLayout_thumbnail,false);
                isLoop = typedArray.getBoolean(R.styleable.VideoLayout_loop,false);
                isAutoPlayer = typedArray.getBoolean(R.styleable.VideoLayout_autoPlayer,false);
                isSilence = typedArray.getBoolean(R.styleable.VideoLayout_silence,false);
            } finally {
                typedArray.recycle();
            }
        }

        currentVolume = PlayerManager.getInstance().getVolume();
        isMute(currentVolume);

        bottomSeekBar.setOnSeekBarChangeListener(this);
        mStatusLayout.setOnClickListener(this);
        bottomVoiceIcon.setOnClickListener(this);

        gestureDetector = new GestureDetector(getContext(),this);
        gestureDetector.setIsLongpressEnabled(true);
    }

    /* 生命周期 */

    /**
     *
     */
    public void onPause(){
        destroyTimer();
        PlayerManager.getInstance().stop();
    }

    /**
     *
     */
    public void onResume(){
        startTimer();
        PlayerManager.getInstance().start();
    }

    /**
     *
     */
    public void onDestroy(){
        PlayerManager.getInstance().release();
        destroyTimer();
        unRegister();
    }

    /**
     *
     * @param dataSource
     * @param title
     */
    public void initPlayer(String dataSource,String title){
        if (TextUtils.isEmpty(dataSource)){
            return;
        }
        if (isThumbnail){
            mThumbnailImageVew.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(dataSource)
                    .thumbnail(0.1f)
                    .apply(new RequestOptions().frame(defaultFrame))
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mThumbnailImageVew);
        }

        mStatusLayout.loader();

        if (!TextUtils.isEmpty(title)){
            topLeftGroupText.setVisibility(View.VISIBLE);
            topLeftGroupText.setText(title);
        }else {
            topLeftGroupText.setVisibility(View.GONE);
        }

        PlayerManager.getInstance().setManagerListener(this);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        mSurfaceHolder.setKeepScreenOn(true);
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(final SurfaceHolder holder) {
                PlayerManager.getInstance().initPlayer(dataSource,holder,isLoop);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }


    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        // 设置总长度、时长
        bottomSeekBar.setMax(mediaPlayer.getDuration());

        if (isAutoPlayer){
            if (isThumbnail && mThumbnailImageVew.getVisibility() == View.VISIBLE){
                mThumbnailImageVew.setVisibility(View.GONE);
            }
            mStatusLayout.ok();
            PlayerManager.getInstance().start();
            startTimer();
        }else {
            mStatusLayout.stop();
        }
    }

    @Override
    public void onCompletion() {
        Log.e(TAG, "onCompletion: ");
    }

    @Override
    public void onBuffering(int percent) {
        bottomSeekBar.setSecondaryProgress(percent);
    }

    @Override
    public void onError(int what) {
        Log.e(TAG, "onError: "+what );
    }

    @Override
    public void onInfo(int what) {
        if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
            mVideoVolumeAndLuminanceLayout.ok();
            mStatusLayout.loader();
        } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
            mStatusLayout.ok();
        }
    }

    @Override
    public void onSizeChanged(int width, int height) {
        Log.e(TAG, "onSizeChanged: "+width+"height:"+height);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.video_layout_status){
            switch (mStatusLayout.getStatusEnum()){
                case STOP:
                    mStatusLayout.ok();
                    if (isThumbnail && mThumbnailImageVew.getVisibility() == View.VISIBLE){
                        mThumbnailImageVew.setVisibility(View.GONE);
                    }
                    PlayerManager.getInstance().start();
                    break;
                case NETWORK:
                    break;
            }
        }else if (v.getId() == R.id.video_layout_bottom_voice){
            if (currentVolume > 0){
                oldVolume = currentVolume;
                currentVolume = 0;
            }else {
                currentVolume = oldVolume;
            }
            PlayerManager.getInstance().setVolume(currentVolume);
            isMute(currentVolume);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        bottomCurrentText.setText(longTimeToString(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int maxCanSeekTo = seekBar.getMax() - 5 * 1000;
        int position;
        if (seekBar.getProgress() < maxCanSeekTo) {
            position = seekBar.getProgress();
        } else {
            position = maxCanSeekTo;
        }
        PlayerManager.getInstance().seekTo(position);
    }

    /* 手势 */

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if ((System.currentTimeMillis() - oldClickTime) < 1000){
            oldClickTime = System.currentTimeMillis();
            Log.e(TAG, "onSingleTapUp: 双击");
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (mStatusLayout.getStatusEnum() != VideoStatusLayout.VideoStatusEnum.OK){
            return false;
        }
        if (Math.abs(distanceX) >= Math.abs(distanceY)){
            if (PlayerManager.getInstance().isMediaPlayer()){
                return false;
            }
            if (mVideoVolumeAndLuminanceLayout.getVisibility() == View.VISIBLE){
                mVideoVolumeAndLuminanceLayout.ok();
            }
            mSpeedLayout.setVisibility(View.VISIBLE);
            boolean isSpeed = false;
            try {
                if (Math.abs(distanceX) > Math.abs(distanceY)) {// 横向移动大于纵向移动
                    int position = PlayerManager.getInstance().getCurrentPosition();
                    if (distanceX >= ScreenUtil.dip2px(getContext(),6f)) {// 快退，用步长控制改变速度，可微调
                        isSpeed = false;
                        if (position > 0){
                            if (position > 3 * 1000) {// 避免为负
                                position -= 3000;
                            } else {
                                //什么都不做
                                position = 3000;
                            }
                            PlayerManager.getInstance().seekTo(position);
                        }
                    } else if (distanceX <= (-ScreenUtil.dip2px(getContext(),6f))) {// 快进
                        isSpeed = true;
                        if (position > 0){
                            if (position < PlayerManager.getInstance().getDuration() - 5 * 1000) {// 避免超过总时长
                                position += 3000;
                            }
                            PlayerManager.getInstance().seekTo(position);
                        }
                    }
                }
                String timeStr = longTimeToString(PlayerManager.getInstance().getCurrentPosition()) + " / "
                        + longTimeToString(PlayerManager.getInstance().getDuration());
                mSpeedLayout.speedAndNoSpeed(isSpeed,timeStr);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }else {
            //纵向滑动调整音量货亮度
            int screenWidth = ScreenUtil.getScreenWidth(getContext());
            //判断起始坐标位置,左边为亮度 右边为音量
            if ((e1.getX() > screenWidth / 2)) {//起始点在右边
                int max = PlayerManager.getInstance().geMaxVolume();
                if (max != -1){
                }else {
                    return false;
                }
                if (mSpeedLayout.getVisibility() == View.VISIBLE){
                    mSpeedLayout.setVisibility(View.GONE);
                }
                mVideoVolumeAndLuminanceLayout.setVisibility(View.VISIBLE);
                if (distanceY >= ScreenUtil.dip2px(getContext(),4f)) {// 音量调大,注意横屏时的坐标体系,尽管左上角是原点，但横向向上滑动时distanceY为正
                    if (currentVolume < max) {// 为避免调节过快，distanceY应大于一个设定值
                        currentVolume++;
                    }
                } else if (distanceY <= (-ScreenUtil.dip2px(getContext(),4f))) {// 音量调小
                    if (currentVolume > 0) {
                        currentVolume--;
                    }
                }
                int percentage = (currentVolume * 100) / max;
                PlayerManager.getInstance().setVolume(currentVolume);
                mVideoVolumeAndLuminanceLayout.volume(percentage);
                isMute(currentVolume);
            } else {//起始点在左边
                if (!checkPermission()){
                    if (mAlertDialog == null) {
                        mAlertDialog = new AlertDialog.Builder(getContext());
                        mAlertDialog.setTitle("提示");
                        mAlertDialog.setMessage("视频播放调节亮度需要申请权限");
                        mAlertDialog.setNegativeButton("关闭", null);
                        mAlertDialog.setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + getContext().getPackageName()));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                ((Activity) getContext()).startActivityForResult(intent, VIDEO_LAYOUT_LUMINANCE_PERMISSION_REQUEST_CODE);
                            }
                        }).show();
                    }
                    return false;
                }
                if (Math.abs(distanceY) > Math.abs(distanceX)) {// 纵向移动大于横向移动
                    mVideoVolumeAndLuminanceLayout.setVisibility(View.VISIBLE);
                    // 亮度调大,注意横屏时的坐标体系,尽管左上角是原点，但横向向上滑动时distanceY为正
                    int mLight = LuminanceUtils.getLightness((Activity) getContext());
                    if (mLight >= 0 && mLight <= 255) {
                        if (distanceY >= ScreenUtil.dip2px(getContext(),4f)) {
                            if (mLight > 245) {
                                LuminanceUtils.setLightness((Activity) getContext(), 255);
                            } else {
                                LuminanceUtils.setLightness((Activity) getContext(), mLight + 10);
                            }
                        } else if (distanceY <= (-ScreenUtil.dip2px(getContext(),4f))) {// 亮度调小
                            if (mLight < 10) {
                                LuminanceUtils.setLightness((Activity) getContext(), 0);
                            } else {
                                LuminanceUtils.setLightness((Activity) getContext(), mLight - 10);
                            }
                        }
                    } else if (mLight < 0) {
                        LuminanceUtils.setLightness((Activity) getContext(), 0);
                    } else {
                        LuminanceUtils.setLightness((Activity) getContext(), 255);
                    }
                    //获取当前亮度
                    int currentLight = LuminanceUtils.getLightness((Activity) getContext());
                    int percentage = (currentLight * 100) / 255;
                    mVideoVolumeAndLuminanceLayout.luminance(percentage);
                }
            }
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (mVideoVolumeAndLuminanceLayout.getVisibility() == View.VISIBLE){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mVideoVolumeAndLuminanceLayout.getVisibility() == View.VISIBLE){
                        mVideoVolumeAndLuminanceLayout.ok();
                    }
                    if (mSpeedLayout.getVisibility() == View.VISIBLE){
                        mSpeedLayout.setVisibility(View.GONE);
                    }
                }
            },500);
        }
        return false;
    }

    /* 权限 */

    /**
     *
     */
    private boolean checkPermission(){
        boolean flag = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flag = Settings.System.canWrite(getContext());
        }
        return flag;
    }
}
