package com.yongyong.chat.selected.view;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.yongyong.chat.selected.R;
import com.yongyong.chat.selected.entity.MultimediaSelectedFolderEntity;
import com.yongyong.chat.selected.window.MultimediaSelectedFolderWindow;

import java.util.List;

/**
 * @author 王永勇
 * @// TODO: 2020/7/7
 */
public class MultimediaSelectedTopLayout extends LinearLayout implements View.OnClickListener {

    private LinearLayout backBackground;
    private ImageView backImageView;

    private LinearLayout folderBackground;
    private TextView folderTextView;
    private ImageView folderImageView;

    private Button button;

    private Activity mActivity;

    private MultimediaSelectedFolderWindow folderWindow;
    private ObjectAnimator rotationAnimator;
    private ObjectAnimator recoverAnimator;

    private OnMultimediaSelectedTopLayoutListener layoutListener;

    public MultimediaSelectedTopLayout(Context context) {
        super(context);
        initView();
    }

    public MultimediaSelectedTopLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MultimediaSelectedTopLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     *
     */
    private void initView(){
        mActivity = (Activity) getContext();
        LayoutInflater.from(getContext()).inflate(R.layout.multimedia_selected_top_layout,this);

        backBackground = findViewById(R.id.multimedia_selected_top_layout_close);
        backImageView = findViewById(R.id.multimedia_selected_top_layout_close_iv);

        folderBackground = findViewById(R.id.multimedia_selected_top_layout_folder);
        folderTextView = findViewById(R.id.multimedia_selected_top_layout_folder_tv);
        folderImageView = findViewById(R.id.multimedia_selected_top_layout_folder_iv);

        button = findViewById(R.id.multimedia_selected_top_layout_button);

        rotationAnimator = ObjectAnimator.ofFloat(folderImageView,"rotation",0,180f);
        rotationAnimator.setDuration(500);

        recoverAnimator = ObjectAnimator.ofFloat(folderImageView,"rotation",180f,0);
        recoverAnimator.setDuration(500);

        backBackground.setOnClickListener(this);
        folderBackground.setOnClickListener(this);
        button.setOnClickListener(this);
    }

    /**
     *
     * @param data
     */
    public void init(List<MultimediaSelectedFolderEntity> data){
        if (data == null || data.size() == 0)
            return;
        post(new Runnable() {
            @Override
            public void run() {
                MultimediaSelectedFolderEntity entity = data.get(0);
                folderTextView.setText(entity.getFolder());

                folderWindow = new MultimediaSelectedFolderWindow(getContext(), data, new MultimediaSelectedFolderWindow.OnMultimediaSelectedFolderWindowListener() {
                    @Override
                    public void onDismiss() {
                        folderImageView.clearAnimation();
                        recoverAnimator.start();
                    }

                    @Override
                    public void onListener(MultimediaSelectedFolderEntity entity) {
                        if (layoutListener != null)
                            layoutListener.onChecked(entity);
                    }
                });
            }
        });
    }

    /**
     *
     * @param num
     */
    public void setCheckedNum(int num){
        String str = getContext().getString(R.string.multimedia_selected_top_confirm);
        if (num > 0){
            str = str + " ("+num+")";
            button.setBackground(getResources().getDrawable(R.drawable.multimedia_top_right_green_background));
        }else {
            button.setBackground(getResources().getDrawable(R.drawable.multimedia_top_right_background));
        }
        button.setText(str);
    }

    /**
     *
     * @param layoutListener
     */
    public void setLayoutListener(OnMultimediaSelectedTopLayoutListener layoutListener) {
        this.layoutListener = layoutListener;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.multimedia_selected_top_layout_close) {
            mActivity.finish();
        } else if (id == R.id.multimedia_selected_top_layout_folder) {
            if (folderWindow == null)
                return;
            folderImageView.clearAnimation();
            folderWindow.showAsDropDown(this);
            rotationAnimator.start();
        } else if (id == R.id.multimedia_selected_top_layout_button) {
            if (layoutListener != null)
                layoutListener.onConfirm();
        }
    }

    /**
     *
     */
    public interface OnMultimediaSelectedTopLayoutListener {

        void onConfirm();

        /**
         *
         * @param entity
         */
        void onChecked(MultimediaSelectedFolderEntity entity);
    }
}
