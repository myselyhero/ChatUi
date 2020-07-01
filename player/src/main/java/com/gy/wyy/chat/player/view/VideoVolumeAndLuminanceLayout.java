package com.gy.wyy.chat.player.view;

import android.content.Context;
import android.util.AttributeSet;
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
public class VideoVolumeAndLuminanceLayout extends LinearLayout {

    private ImageView imageView;
    private TextView textView;

    public VideoVolumeAndLuminanceLayout(Context context) {
        super(context);
        initView();
    }

    public VideoVolumeAndLuminanceLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public VideoVolumeAndLuminanceLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     *
     */
    private void initView(){
        LayoutInflater.from(getContext()).inflate(R.layout.video_volume_and_luminance,this);
        imageView = findViewById(R.id.video_volume_and_luminance_iv);
        textView = findViewById(R.id.video_volume_and_luminance_tv);
    }

    /**
     *
     */
    public void ok(){
        setVisibility(View.GONE);
    }

    /**
     *
     * @param volume
     */
    public void volume(int volume){
        if (getVisibility() == View.GONE){
            setVisibility(View.VISIBLE);
        }
        if (volume > 0){
            imageView.setImageResource(R.drawable.player_voice);
            textView.setText(volume+"%");
        }else {
            imageView.setImageResource(R.drawable.player_mute);
            textView.setText("0%");
        }
    }

    /**
     *
     * @param luminance
     */
    public void luminance(int luminance){
        if (getVisibility() == View.GONE){
            setVisibility(View.VISIBLE);
        }
        imageView.setImageResource(R.drawable.player_luminance);
        textView.setText(luminance+"%");
    }
}
