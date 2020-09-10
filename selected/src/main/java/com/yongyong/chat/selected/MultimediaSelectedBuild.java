package com.yongyong.chat.selected;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.yongyong.chat.selected.activity.MultimediaSelectedActivity;

import java.lang.ref.WeakReference;

/**
 * @author 王永勇
 * @// TODO: 2020/7/7
 */
public class MultimediaSelectedBuild {

    private WeakReference<Context> mContext;
    private WeakReference<Activity> mActivity;

    private MultimediaSelectedConfig selectedParameter = MultimediaSelectedConfig.getInstance();

    private MultimediaSelectedBuild(Context context) {
        mContext = new WeakReference<>(context);
        mActivity = new WeakReference<>((Activity) context);
    }

    /**
     *
     * @param context
     * @return
     */
    public static MultimediaSelectedBuild create(Context context) {
        return new MultimediaSelectedBuild(context);
    }

    /**
     * 选择类型
     * @param type
     * @return
     */
    public MultimediaSelectedBuild setMultimediaType (MultimediaSelectedType type){
        selectedParameter.setMultimediaType(type);
        return this;
    }

    /**
     * 单选
     * @param only
     * @return
     */
    public MultimediaSelectedBuild isOnly(boolean only){
        selectedParameter.setOnly(only);
        return this;
    }

    /**
     * 最大可选数量
     * @param maxNum
     * @return
     */
    public MultimediaSelectedBuild setMaxNum(int maxNum){
        selectedParameter.setMaxNum(maxNum);
        return this;
    }

    /**
     * 是否剪切
     * @param crop
     * @return
     */
    public MultimediaSelectedBuild isCrop(boolean crop){
        selectedParameter.setCrop(crop);
        return this;
    }

    /**
     * 是否压缩
     * @param compress
     * @return
     */
    public MultimediaSelectedBuild isCompress(boolean compress){
        selectedParameter.setCompress(compress);
        return this;
    }

    /**
     * 是否可拍照
     * @param camera
     * @return
     */
    public MultimediaSelectedBuild isCamera(boolean camera){
        selectedParameter.setCamera(camera);
        return this;
    }

    /**
     *
     * @param duration
     * @return
     */
    public MultimediaSelectedBuild minDuration(long duration){
        selectedParameter.setMinDuration(duration);
        return this;
    }

    /**
     *
     * @param duration
     * @return
     */
    public MultimediaSelectedBuild maxDuration(long duration){
        selectedParameter.setMaxDuration(duration);
        return this;
    }

    /* 以下两项为在选择图片时可用 */

    /**
     *  是否显示gif
     * @param gif
     * @return
     */
    public MultimediaSelectedBuild isGif(boolean gif){
        selectedParameter.setGif(gif);
        return this;
    }

    /**
     *
     * @param resultListener
     */
    public void start(MultimediaSelectedResultListener resultListener){
        if (resultListener == null)
            return;
        MultimediaSelectedConfig.resultListener = new WeakReference<>(resultListener).get();

        Intent intent = new Intent(mContext.get(), MultimediaSelectedActivity.class);
        intent.putExtra(MultimediaSelectedContent.MULTIMEDIA_SELECTED_CONTENT_BASE_KEY,selectedParameter);
        mContext.get().startActivity(intent);
    }

    /**
     *
     * @param requestCode
     */
    public void start(int requestCode){
        Intent intent = new Intent(mActivity.get(), MultimediaSelectedActivity.class);
        intent.putExtra(MultimediaSelectedContent.MULTIMEDIA_SELECTED_CONTENT_BASE_KEY,selectedParameter);
        mActivity.get().startActivityForResult(intent,requestCode);
    }
}
