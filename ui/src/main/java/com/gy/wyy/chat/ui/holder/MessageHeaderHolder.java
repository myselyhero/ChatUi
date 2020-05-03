package com.gy.wyy.chat.ui.holder;

import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.gy.wyy.chat.ui.entity.MessageEntity;

/**
 * 头部加载
 */
public class MessageHeaderHolder extends MessageBaseViewHolder {

    private boolean mLoading;

    public MessageHeaderHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void layoutViews(int position, MessageEntity entity) {
        RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) rootView.getLayoutParams();
        if (mLoading) {
            param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            param.width = LinearLayout.LayoutParams.MATCH_PARENT;
            rootView.setVisibility(View.VISIBLE);
        } else {
            param.height = 0;
            param.width = 0;
            rootView.setVisibility(View.GONE);
        }
        rootView.setLayoutParams(param);
    }

    /**
     *
     * @param loading
     */
    public void setLoadingStatus(boolean loading) {
        mLoading = loading;
    }
}
