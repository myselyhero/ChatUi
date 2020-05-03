package com.gy.wyy.chat.ui.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 *  状态栏高度
 */
public class ToolBarHeightView extends LinearLayout {

    private int barHeight;

    public ToolBarHeightView(Context context) {
        super(context);
        init();
    }

    public ToolBarHeightView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ToolBarHeightView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    /**
     *
     */
    private void init() {

        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(resourceId>0) {
                barHeight = getResources().getDimensionPixelSize(resourceId);
            }
        }else{
            barHeight = 0;
        }
        setPadding(getPaddingLeft(), barHeight, getPaddingRight(), getPaddingBottom());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), barHeight);
    }
}
