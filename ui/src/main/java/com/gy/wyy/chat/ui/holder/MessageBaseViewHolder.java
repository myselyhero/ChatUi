package com.gy.wyy.chat.ui.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gy.wyy.chat.ui.MessageProperties;
import com.gy.wyy.chat.ui.MessageAdapter;
import com.gy.wyy.chat.ui.MessageEntity;
import com.gy.wyy.chat.ui.model.OnMessageLayoutItemClickListener;

/**
 *
 */
public abstract class MessageBaseViewHolder extends RecyclerView.ViewHolder {

    protected String TAG = MessageBaseViewHolder.class.getSimpleName();

    protected View rootView;

    protected MessageAdapter mAdapter;
    protected MessageProperties properties = MessageProperties.getInstance();
    protected OnMessageLayoutItemClickListener onItemClickListener;

    public MessageBaseViewHolder(@NonNull View itemView) {
        super(itemView);
        rootView = itemView;
    }

    /**
     *
     * @param adapter
     */
    public void setAdapter(MessageAdapter adapter){
        mAdapter = adapter;
    }

    /**
     *
     * @param listener
     */
    public void setOnItemClickListener(OnMessageLayoutItemClickListener listener){
        onItemClickListener = listener;
    }

    /**
     *
     * @param position
     * @param entity
     */
    public abstract void layoutViews(final int position,final MessageEntity entity);
}
