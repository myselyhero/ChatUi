package com.gy.wyy.chat;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

/**
 * 自定义宽度撑满屏幕的NavigationView
 */
public class FullScreenNavigationView extends NavigationView {

    public FullScreenNavigationView(@NonNull Context context) {
        super(context);
        initView();
    }

    public FullScreenNavigationView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public FullScreenNavigationView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     *
     */
    private void initView(){
        /**
         * 侦测待UI完全加载完成才允许计算宽值
         */
        ViewTreeObserver viewTreeObserver = this.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                setFullScreenWidth();
            }
        });
    }

    /**
     *
     */
    private void setFullScreenWidth() {
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) this.getLayoutParams();
        params.width = getScreenWidth();
        this.setLayoutParams(params);
    }

    /**
     *
     * @return
     */
    private int getScreenWidth() {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }
}
