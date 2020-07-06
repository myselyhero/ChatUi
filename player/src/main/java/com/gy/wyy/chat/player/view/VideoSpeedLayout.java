package com.gy.wyy.chat.player.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.gy.wyy.chat.player.R;

/**
 *
 */
public class VideoSpeedLayout extends LinearLayout {

    private ImageView imageView;
    private TextView textView;

    public VideoSpeedLayout(Context context) {
        super(context);
        initView();
    }

    public VideoSpeedLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public VideoSpeedLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     *
     */
    private void initView(){
        LayoutInflater.from(getContext()).inflate(R.layout.video_speed_layout,this);
        imageView = findViewById(R.id.video_speed_layout_iv);
        textView = findViewById(R.id.video_speed_layout_tv);
    }

    /**
     *
     * @param speed
     * @param position
     */
    public void speedAndNoSpeed(boolean speed,String position){
        if (speed){
            imageView.setImageResource(R.drawable.ic_player_speed);
        }else {
            imageView.setImageResource(R.drawable.ic_player_nospeed);
        }
        textView.setText(position);
    }
}
