package com.yongyong.chat.selected.tool;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.yongyong.chat.selected.R;

/**
 * @author 王永勇
 * @// TODO: 2020/7/7
 */
public class GlideEngine {

    /**
     * 常规加载图片
     * @param context
     * @param imageView
     */
    public static void loader(Context context,String url, ImageView imageView){
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.default_image)
                        .error(R.drawable.default_error)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imageView);
    }

    /**
     * 加载gif图片
     * @param context
     * @param url
     * @param imageView
     */
    public static void loaderGif(Context context,String url, ImageView imageView){
        Glide.with(context)
                .asGif()
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.default_image)
                        .error(R.drawable.default_error)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imageView);
    }

    /**
     * 加载视频第一帧作为缩略图
     * @param context
     * @param url
     * @param imageView
     */
    public static void loaderThumbnail(Context context,String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                .thumbnail(0.1f)
                .apply(new RequestOptions().frame(1000000))
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}
