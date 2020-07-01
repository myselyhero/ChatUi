package com.gy.wyy.chat.player.manager;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.SurfaceHolder;

import com.gy.wyy.chat.player.PlayerKit;

import java.io.IOException;

/**
 * @author 王永勇
 * @// TODO: 2020/6/28
 */
public class PlayerManager implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnInfoListener, MediaPlayer.OnVideoSizeChangedListener,
        AudioManager.OnAudioFocusChangeListener {


    private static PlayerManager manager = new PlayerManager();

    /**
     *
     * @return
     */
    public static PlayerManager getInstance(){
        return manager;
    }

    private AudioManager mAudioManager;
    private MediaPlayer mMediaPlayer;
    private int currentPosition;

    /**
     *
     */
    private PlayerManagerListener managerListener;

    /**
     *
     */
    public PlayerManager(){
        /**
         * 音频焦点
         */
        if (mAudioManager == null){
            mAudioManager = (AudioManager) PlayerKit.getAppContext().getSystemService(Context.AUDIO_SERVICE);
        }
    }

    /**
     *
     * @param dataSource
     * @param holder
     * @param loop
     */
    public void initPlayer(String dataSource, SurfaceHolder holder,boolean loop){
        if (TextUtils.isEmpty(dataSource) || holder == null){
            return;
        }
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setDisplay(holder); // 添加到容器中
        mMediaPlayer.setLooping(loop);
        mMediaPlayer.setScreenOnWhilePlaying(true);//播放时屏幕常亮

        //视频URL
        try {
            mMediaPlayer.setDataSource(dataSource);
            mMediaPlayer.prepareAsync();//异步准备
        } catch (IOException e) {
            e.printStackTrace();
        }

        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnVideoSizeChangedListener(this);
        mMediaPlayer.setOnInfoListener(this);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnBufferingUpdateListener(this);
    }


    /**
     *
     * @param managerListener
     */
    public void setManagerListener(PlayerManagerListener managerListener) {
        this.managerListener = managerListener;
    }

    /**
     *
     * @return
     */
    public int getDuration(){
        if (mMediaPlayer == null){
            return -1;
        }
        return mMediaPlayer.getDuration();
    }

    /**
     *
     * @return
     */
    public int getCurrentPosition(){
        if (!isPlayer()){
            return -1;
        }
        return mMediaPlayer.getCurrentPosition();
    }

    /**
     *
     * @param position
     */
    public void seekTo(int position){
        currentPosition = position;
        if (!isPlayer()){
            start();
        }else {
            mMediaPlayer.seekTo(currentPosition);
        }
    }

    /**
     *
     * @return
     */
    public boolean isPlayer(){
        if (mMediaPlayer == null){
            return false;
        }else {
            return mMediaPlayer.isPlaying();
        }
    }

    /**
     *
     * @return
     */
    public boolean isMediaPlayer(){
        return mMediaPlayer == null;
    }

    /**
     *
     */
    public void start(){
        if (mMediaPlayer == null || isPlayer()){
            return;
        }

        if (mAudioManager != null){
            mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        }

        if (currentPosition != 0){//大于0时恢复播放位置
            mMediaPlayer.prepareAsync();
        }else {
            mMediaPlayer.start();
        }
    }

    /**
     *
     */
    public void stop(){
        if (mMediaPlayer == null || !isPlayer()){
            return;
        }
        if (mAudioManager != null){//释放音频焦点
            mAudioManager.abandonAudioFocus(this);
        }
        currentPosition = mMediaPlayer.getCurrentPosition();
        mMediaPlayer.pause();
        mMediaPlayer.stop();
    }

    /**
     *
     */
    public void release(){
        stop();
        if (mMediaPlayer != null){
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
            currentPosition = 0;
        }
    }

    /* 音频管理 */

    /**
     *
     * @return
     */
    public int geMaxVolume(){
        if (mAudioManager == null){
            return -1;
        }else {
            return mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        }
    }

    /**
     *
     */
    public int getVolume(){
        if (mAudioManager == null){
            return -1;
        }else {
            return mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        }
    }

    /**
     *
     * @param volume
     * @return
     */
    public boolean setVolume(int volume){
        if (mAudioManager == null){
            return false;
        }else {
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
            return true;
        }
    }

    /* 播放器回调 */

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (managerListener != null){
            managerListener.onCompletion();
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        if (managerListener != null && percent >= 0 && percent <= 100) {
            int secondProgress = mp.getDuration() * percent / 100;
            managerListener.onBuffering(secondProgress);
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        if (what != -38) {  //这个错误不管
            if (managerListener != null)
                managerListener.onError(what);
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (currentPosition != 0){
            mMediaPlayer.seekTo(currentPosition);
            mMediaPlayer.start();
        }else {
            if (managerListener != null){
                managerListener.onPrepared(mp);
            }
        }
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        if (managerListener != null){
            managerListener.onInfo(what);
        }
        return false;
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        if (managerListener != null){
            managerListener.onSizeChanged(width,height);
        }
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange){
            case AudioManager.AUDIOFOCUS_LOSS:
                //长时间丢失焦点,当其他应用申请的焦点为AUDIOFOCUS_GAIN时触发此回调 将不会再自动获得
                //mAudioManager.abandonAudioFocus(mAudioFocusChange);
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                //短暂性丢失焦点，当其他应用申请AUDIOFOCUS_GAIN_TRANSIENT或AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE时，触发此回调事件(如播放短视频，拨打电话等)
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                //短暂性丢失焦点并作降音处理
                break;
            case AudioManager.AUDIOFOCUS_GAIN:
                //当其他应用申请焦点之后又释放焦点会触发此回调
                break;
        }
    }
}