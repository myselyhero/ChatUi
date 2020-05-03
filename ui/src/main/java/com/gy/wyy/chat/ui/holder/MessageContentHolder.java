package com.gy.wyy.chat.ui.holder;

import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gy.wyy.chat.ui.R;
import com.gy.wyy.chat.ui.entity.MessageEntity;
import com.gy.wyy.chat.ui.tool.DateTimeUtil;
import com.gy.wyy.chat.ui.tool.GlideEngine;

import java.util.Date;

/**
 *
 */
public abstract class MessageContentHolder extends MessageBaseViewHolder {

    public TextView timeTextView;
    public ImageView leftUserIcon;
    public ImageView rightUserIcon;
    public TextView usernameText;
    public LinearLayout msgContentLinear;
    public ProgressBar sendingProgress;
    public ImageView statusImage;
    public FrameLayout mContentFrame;

    public MessageContentHolder(View itemView) {
        super(itemView);
        rootView = itemView;
        timeTextView = itemView.findViewById(R.id.message_item_content_time);
        leftUserIcon = itemView.findViewById(R.id.message_item_content_left_icon);
        rightUserIcon = itemView.findViewById(R.id.message_item_content_right_icon);
        usernameText = itemView.findViewById(R.id.message_item_content_name);
        msgContentLinear = itemView.findViewById(R.id.msg_content_ll);
        statusImage = itemView.findViewById(R.id.message_item_content_status);
        sendingProgress = itemView.findViewById(R.id.message_item_content_progress);
        mContentFrame = itemView.findViewById(R.id.message_item_content_fl);
        initVariableLayout();
    }

    @Override
    public void layoutViews(int position, final MessageEntity msg) {

        //// 时间线设置
        if (properties.getTimeBubble() != null) {
            timeTextView.setBackground(properties.getTimeBubble());
        }
        if (properties.getTimeFontColor() != 0) {
            timeTextView.setTextColor(properties.getTimeFontColor());
        }
        if (properties.getTimeFontSize() != 0) {
            timeTextView.setTextSize(properties.getTimeFontSize());
        }
        if (position > 1) {
            MessageEntity last = mAdapter.getItem(position - 1);
            if (last != null) {
                if (msg.getMsgTime() - last.getMsgTime() >= 5 * 60 * 1000) {
                    timeTextView.setVisibility(View.VISIBLE);
                    timeTextView.setText(DateTimeUtil.getTimeFormatText(new Date(msg.getMsgTime())));
                } else {
                    timeTextView.setVisibility(View.GONE);
                }
            }
        } else {
            timeTextView.setVisibility(View.VISIBLE);
            timeTextView.setText(DateTimeUtil.getTimeFormatText(new Date(msg.getMsgTime())));
        }

        //// 头像设置
        if (msg.isSelf()) {
            leftUserIcon.setVisibility(View.GONE);
            rightUserIcon.setVisibility(View.VISIBLE);
            GlideEngine.loaderCircleUserIcon("http://b-ssl.duitang.com/uploads/item/201510/18/20151018113959_UwGFk.thumb.700_0.jpeg",rightUserIcon);
        } else {
            leftUserIcon.setVisibility(View.VISIBLE);
            rightUserIcon.setVisibility(View.GONE);
            GlideEngine.loaderCircleUserIcon("http://pic2.zhimg.com/50/v2-6f7ecca94b20af97d1716bfd332fbaf6_hd.jpg",leftUserIcon);
        }

        //// 聊天气泡设置
        if (msg.isSelf()) {
            mContentFrame.setBackgroundResource(R.drawable.chat_bubble_myself);
        } else {
            mContentFrame.setBackgroundResource(R.drawable.chat_other_bg);
        }

        //// 聊天气泡的点击事件处理
        if (onItemClickListener != null) {
            mContentFrame.setOnClickListener(v -> {
                onItemClickListener.onMessageClick(mContentFrame,position,msg);
            });
            mContentFrame.setOnLongClickListener(v -> {
                onItemClickListener.onMessageLongClick(mContentFrame, position, msg);
                return true;
            });
        }

        //// 发送状态的设置
        if (msg.isSelf()){
            switch (msg.getStatus()){
                case MessageEntity.MESSAGE_STATUS_NORMAL:
                case MessageEntity.MESSAGE_STATUS_SEND_SUCCESS:
                    statusImage.setVisibility(View.GONE);
                    sendingProgress.setVisibility(View.GONE);
                    break;
                case MessageEntity.MESSAGE_STATUS_SENDING:
                    statusImage.setVisibility(View.GONE);
                    sendingProgress.setVisibility(View.VISIBLE);
                    break;
                case MessageEntity.MESSAGE_STATUS_SEND_FAIL:
                    statusImage.setVisibility(View.VISIBLE);
                    sendingProgress.setVisibility(View.GONE);
                    statusImage.setOnClickListener(v -> {
                        if (onItemClickListener != null) {
                            onItemClickListener.onMessageLongClick(mContentFrame, position, msg);
                        }
                    });
                    break;
            }
        }else {
            statusImage.setVisibility(View.GONE);
            sendingProgress.setVisibility(View.GONE);
        }

        //// 左右边的消息需要调整一下内容的位置
        if (msg.isSelf()) {
            msgContentLinear.setGravity(Gravity.RIGHT);
            msgContentLinear.removeView(mContentFrame);
            msgContentLinear.addView(mContentFrame);
        } else {
            msgContentLinear.setGravity(Gravity.LEFT);
            msgContentLinear.removeView(mContentFrame);
            msgContentLinear.addView(mContentFrame, 0);
        }
        //// 由子类设置指定消息类型的views
        layoutVariableViews(msg, position);
    }

    /**
     *
     * @return
     */
    public abstract int getVariableLayout();

    /**
     *
     */
    public abstract void initVariableViews();

    /**
     *
     */
    private void initVariableLayout() {
        if (getVariableLayout() != 0) {
            setVariableLayout(getVariableLayout());
        }
    }

    /**
     *
     * @param resId
     */
    private void setVariableLayout(int resId) {
        if (mContentFrame.getChildCount() == 0) {
            View.inflate(rootView.getContext(), resId, mContentFrame);
        }
        initVariableViews();
    }

    /**
     *
     * @param entity
     * @param position
     */
    public abstract void layoutVariableViews(final MessageEntity entity, final int position);
}
