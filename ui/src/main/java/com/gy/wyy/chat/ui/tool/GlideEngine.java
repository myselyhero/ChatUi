package com.gy.wyy.chat.ui.tool;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.gy.wyy.chat.ui.R;
import com.gy.wyy.chat.ui.UiKit;

/**
 *
 */
public class GlideEngine {

    /**
     * 加载原型的用户头像
     * @param url
     * @param imageView
     */
    public static void loaderCircleUserIcon(String url, final ImageView imageView){

        Glide.with(UiKit.getAppContext())
                .asBitmap()
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.default_profile)
                        .error(R.drawable.default_profile)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .bitmapTransform(new CircleCrop()))
                .into(imageView);
    }

    /**
     * 加载指定大小的圆角图片
     * @param url
     * @param imageView
     * @param size
     */
    public static void loaderCircle(String url, final ImageView imageView, int size){
        Glide.with(UiKit.getAppContext())
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.default_image)
                        .error(R.drawable.default_error)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .bitmapTransform(new CornerTransform(UiKit.getAppContext(),size)))
                .into(imageView);
    }

    /**
     *
     * @param resId
     * @param imageView
     * @param size
     */
    public static void loaderCircle(int resId, final ImageView imageView, int size){
        Glide.with(UiKit.getAppContext())
                .load(resId)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .bitmapTransform(new CornerTransform(UiKit.getAppContext(),size)))
                .into(imageView);
    }

    /**
     *
     * @param url
     * @param imageView
     */
    public static void loader(String url, final ImageView imageView){
        Glide.with(UiKit.getAppContext())
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.default_image)
                        .error(R.drawable.default_error)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imageView);
    }
}
