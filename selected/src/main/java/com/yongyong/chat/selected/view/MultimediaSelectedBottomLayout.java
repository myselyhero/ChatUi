package com.yongyong.chat.selected.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.yongyong.chat.selected.R;

/**
 * @author 王永勇
 * @// TODO: 2020/7/7
 */
public class MultimediaSelectedBottomLayout extends LinearLayout implements View.OnClickListener,CompoundButton.OnCheckedChangeListener {

    private CheckBox radioButton;
    private LinearLayout previewBackground;
    private TextView previewTextView;

    private OnMultimediaSelectedBottomLayoutListener layoutListener;

    public MultimediaSelectedBottomLayout(Context context) {
        super(context);
        initView();
    }

    public MultimediaSelectedBottomLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MultimediaSelectedBottomLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     *
     */
    private void initView(){
        LayoutInflater.from(getContext()).inflate(R.layout.multimedia_selected_bottom_layout,this);

        radioButton = findViewById(R.id.multimedia_selected_bottom_original);
        previewBackground = findViewById(R.id.multimedia_selected_bottom_preview);
        previewTextView = findViewById(R.id.multimedia_selected_bottom_preview_tv);

        previewBackground.setOnClickListener(this);
        radioButton.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.multimedia_selected_bottom_preview) {
            if (layoutListener != null){
                layoutListener.onPreview();
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (layoutListener != null){
            layoutListener.onCheckedChanged(isChecked);
        }
    }

    /**
     *
     * @param layoutListener
     */
    public void setLayoutListener(OnMultimediaSelectedBottomLayoutListener layoutListener) {
        this.layoutListener = layoutListener;
    }

    /**
     *
     * @param num
     */
    public void setPreviewNum(int num){
        String str = getContext().getString(R.string.multimedia_selected_bottom_preview);
        if (num > 0){
            str = str + " ("+num+")";
        }
        previewTextView.setText(str);
    }

    /**
     *
     */
    public interface OnMultimediaSelectedBottomLayoutListener {
        void onCheckedChanged(boolean isChecked);
        void onPreview();
    }
}
