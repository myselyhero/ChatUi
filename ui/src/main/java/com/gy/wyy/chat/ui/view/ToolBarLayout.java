package com.gy.wyy.chat.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.gy.wyy.chat.ui.R;

/**
 *
 */
public class ToolBarLayout extends LinearLayout implements View.OnClickListener {

    public static final int TOOL_BAR_LEFT_ICON = 101;
    public static final int TOOL_BAR_LEFT_TEXT = 102;
    public static final int TOOL_BAR_RIGHT_TEXT = 103;
    public static final int TOOL_BAR_RIGHT_ICON = 104;

    private ToolBarHeightView heightView;

    private LinearLayout leftGroupIconBackGround;
    private ImageView leftGroupIcon;
    private TextView leftGroupTextView;

    private TextView centerTitleTextView;

    private LinearLayout rightGroupIconBackground;
    private ImageView rightGroupIcon;
    private TextView rightGroupTextView;


    /**
     * 高亮显示（图标，字体颜色为白色）
     */
    private boolean highlight = true;
    private int highlightDefaultWhite = getResources().getColor(R.color.tool_bar_white);
    private int highlightDefaultBlack = getResources().getColor(R.color.tool_bar_black);
    private int leftDefaultIcon = -1;
    private int rightDefaultIcon = -1;

    private OnToolBarLayoutClickListener clickListener;

    public ToolBarLayout(Context context) {
        super(context);
        initView();
    }

    public ToolBarLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
        getStyleable(attrs);
    }

    public ToolBarLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        getStyleable(attrs);
    }

    /**
     *
     */
    private void initView(){
        LayoutInflater.from(getContext()).inflate(R.layout.tool_bar_layout,this);

        heightView = findViewById(R.id.tool_bar_height_view);
        leftGroupIconBackGround = findViewById(R.id.tool_bar_left_icon_bg);
        leftGroupIcon = findViewById(R.id.tool_bar_left_icon);
        leftGroupTextView = findViewById(R.id.tool_bar_left_text);
        centerTitleTextView = findViewById(R.id.tool_bar_center_title);
        rightGroupIconBackground = findViewById(R.id.tool_bar_right_icon_bg);
        rightGroupIcon = findViewById(R.id.tool_bar_right_icon);
        rightGroupTextView = findViewById(R.id.tool_bar_right_text);

        leftGroupIconBackGround.setOnClickListener(this);
    }

    /**
     *
     * @param attributeSet
     */
    private void getStyleable(AttributeSet attributeSet){
        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.ToolBarLayout, 0, 0);
        try {
            boolean light = typedArray.getBoolean(R.styleable.ToolBarLayout_highlight,highlight);
            String leftString = typedArray.getString(R.styleable.ToolBarLayout_leftString);
            String centerString = typedArray.getString(R.styleable.ToolBarLayout_centerString);
            String rightString = typedArray.getString(R.styleable.ToolBarLayout_rightString);

            leftDefaultIcon = typedArray.getResourceId(R.styleable.ToolBarLayout_leftIcon,leftDefaultIcon);
            rightDefaultIcon = typedArray.getResourceId(R.styleable.ToolBarLayout_rightIcon,rightDefaultIcon);
            int display = typedArray.getInt(R.styleable.ToolBarLayout_toolBarHeightView,0);

            if (light){
                leftGroupTextView.setTextColor(highlightDefaultWhite);
                centerTitleTextView.setTextColor(highlightDefaultWhite);
                rightGroupTextView.setTextColor(highlightDefaultWhite);
                leftGroupIcon.setImageResource(R.drawable.tool_bar_left_white);
            }else {
                leftGroupTextView.setTextColor(highlightDefaultBlack);
                centerTitleTextView.setTextColor(highlightDefaultBlack);
                rightGroupTextView.setTextColor(highlightDefaultBlack);
                leftGroupIcon.setImageResource(R.drawable.tool_bar_left_black);
            }

            if (!TextUtils.isEmpty(centerString))
                centerTitleTextView.setText(centerString);

            if (!TextUtils.isEmpty(leftString)){
                leftGroupTextView.setVisibility(View.VISIBLE);
                leftGroupTextView.setText(leftString);
                leftGroupTextView.setOnClickListener(this);
            }else {
                leftGroupTextView.setVisibility(View.INVISIBLE);
            }
            if (!TextUtils.isEmpty(rightString)){
                rightGroupTextView.setVisibility(View.VISIBLE);
                rightGroupTextView.setText(rightString);
                rightGroupTextView.setOnClickListener(this);
            }else {
                rightGroupTextView.setVisibility(View.INVISIBLE);
            }

            heightView.setVisibility(display);

            if (leftDefaultIcon != -1)
                leftGroupIcon.setImageResource(leftDefaultIcon);

            if (rightDefaultIcon != -1){
                rightGroupIcon.setImageResource(rightDefaultIcon);
                rightGroupIconBackground.setVisibility(View.VISIBLE);
                rightGroupIconBackground.setOnClickListener(this);
            }else {
                rightGroupIconBackground.setVisibility(View.INVISIBLE);
            }
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tool_bar_left_icon_bg) {
            if (clickListener != null)
                clickListener.onClick(TOOL_BAR_LEFT_ICON);
        } else if (id == R.id.tool_bar_left_text) {
            if (clickListener != null)
                clickListener.onClick(TOOL_BAR_LEFT_TEXT);
        } else if (id == R.id.tool_bar_right_icon_bg) {
            if (clickListener != null)
                clickListener.onClick(TOOL_BAR_RIGHT_ICON);
        } else if (id == R.id.tool_bar_right_text) {
            if (clickListener != null)
                clickListener.onClick(TOOL_BAR_RIGHT_TEXT);
        }
    }

    /* 对外API */

    /**
     *
     * @param listener
     */
    public void setOnItemClickListener(OnToolBarLayoutClickListener listener){
        this.clickListener = listener;
    }

    /**
     *
     */
    public interface OnToolBarLayoutClickListener {

        /**
         *
         * @param function
         */
        void onClick(int function);
    }
}
