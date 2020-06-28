package com.gy.wyy.chat.ui.holder;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.gy.wyy.chat.ui.R;
import com.gy.wyy.chat.ui.MessageEntity;
import com.gy.wyy.chat.ui.tool.GlideEngine;

/**
 *
 */
public class MessageImageHolder extends MessageContentHolder {

    private static final int DEFAULT_MAX_SIZE = 540;
    private static final int DEFAULT_RADIUS = 10;

    private ImageView imageView;

    public MessageImageHolder(View itemView) {
        super(itemView);
    }

    @Override
    public int getVariableLayout() {
        return R.layout.message_item_image;
    }

    @Override
    public void initVariableViews() {
        imageView = rootView.findViewById(R.id.message_item_image_iv);
    }

    @Override
    public void layoutVariableViews(MessageEntity entity, int position) {
        mContentFrame.setBackground(null);
        performImage(entity);
    }

    /**
     * 加载图片
     * @param entity
     */
    private void performImage(final MessageEntity entity) {
        setImageParams(imageView.getLayoutParams(), entity.getDataPath());
        ((FrameLayout) imageView.getParent().getParent()).setPadding(17, 0, 13, 0);

        if (!TextUtils.isEmpty(entity.getDataPath())) {
            GlideEngine.loaderCircle(entity.getDataPath(),imageView,10);
        }
    }

    /**
     * 通过glide获取宽高后设置缩放
     * @param params
     * @param dataPath
     */
    private void setImageParams(final ViewGroup.LayoutParams params, final String dataPath) {
        Glide.with(rootView.getContext())
                .asBitmap()
                .load(dataPath)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        setImageParams(params,dataPath);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        int width = resource.getWidth();
                        int height = resource.getHeight();
                        if (width > height) {
                            params.width = DEFAULT_MAX_SIZE;
                            params.height = DEFAULT_MAX_SIZE * height / width;
                        } else {
                            params.width = DEFAULT_MAX_SIZE * width / height;
                            params.height = DEFAULT_MAX_SIZE;
                        }
                        imageView.setLayoutParams(params);
                    }
                });

    }
}
