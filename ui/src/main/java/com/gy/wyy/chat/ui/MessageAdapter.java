package com.gy.wyy.chat.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gy.wyy.chat.ui.holder.MessageBaseViewHolder;
import com.gy.wyy.chat.ui.holder.MessageHeaderHolder;
import com.gy.wyy.chat.ui.holder.MessageImageHolder;
import com.gy.wyy.chat.ui.holder.MessageTextHolder;
import com.gy.wyy.chat.ui.model.OnMessageLayoutItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MessageAdapter extends RecyclerView.Adapter {

    public static final int MESSAGE_HEADER_VIEW = -99;

    private Context mContext;

    private boolean mLoading = true;

    private MessageLayout mRecycleView;
    private List<MessageEntity> mDataSource = new ArrayList<>();
    private OnMessageLayoutItemClickListener mOnItemClickListener;

    public MessageAdapter(Context context){
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(UiKit.getAppContext());
        RecyclerView.ViewHolder holder = null;
        View view;
        // 头部的holder
        if (viewType == MESSAGE_HEADER_VIEW) {
            view = inflater.inflate(R.layout.message_item_header, parent, false);
            holder = new MessageHeaderHolder(view);
            return holder;
        }

        // 具体消息holder
        view = inflater.inflate(R.layout.message_item_content, parent, false);
        switch (viewType) {
            case MessageEntity.MESSAGE_ENTITY_TEXT:
                holder = new MessageTextHolder(view);
                break;
            case MessageEntity.MESSAGE_ENTITY_IMAGE:
                holder = new MessageImageHolder(view);
                break;
        }
        if (holder != null) {
            ((MessageBaseViewHolder) holder).setAdapter(this);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageBaseViewHolder baseHolder = (MessageBaseViewHolder) holder;
        baseHolder.setOnItemClickListener(mOnItemClickListener);
        switch (getItemViewType(position)) {
            case MESSAGE_HEADER_VIEW:
                ((MessageHeaderHolder) baseHolder).setLoadingStatus(mLoading);
                break;
        }
        MessageEntity entity = getItem(position);
        baseHolder.layoutViews(position,entity);
    }

    @Override
    public int getItemCount() {
        return mDataSource.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return MESSAGE_HEADER_VIEW;
        }
        MessageEntity msg = getItem(position);
        return msg.getMsgType();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecycleView = (MessageLayout) recyclerView;
    }

    /**
     *
     * @param mOnItemClickListener
     */
    public void setOnItemClickListener(OnMessageLayoutItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    /**
     *
     * @param list
     */
    public void setDataSource(List<MessageEntity> list) {
        if (list == null)
            return;
        mDataSource = list;
        notifyDataSourceChanged(MessageLayout.DATA_CHANGE_TYPE_REFRESH, getItemCount());
    }

    /**
     *
     * @param position
     * @return
     */
    public MessageEntity getItem(int position) {
        if (position == 0 || mDataSource.size() == 0)
            return null;
        MessageEntity entity = mDataSource.get(position - 1);
        return entity;
    }

    /**
     *
     */
    public void showLoading() {
        if (mLoading) {
            return;
        }
        mLoading = true;
        notifyItemChanged(0);
    }

    /**
     *
     */
    public void shutLoading() {
        if (!mLoading) {
            return;
        }
        mLoading = false;
        notifyDataSetChanged();
    }

    /**
     *
     * @return
     */
    public Context getContext(){
        return mContext;
    }

    /**
     *
     * @param type
     * @param value
     */
    public void notifyDataSourceChanged(final int type, final int value) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mLoading = false;
                if (type == MessageLayout.DATA_CHANGE_TYPE_REFRESH) {
                    notifyDataSetChanged();
                    mRecycleView.scrollToEnd();
                } else if (type == MessageLayout.DATA_CHANGE_TYPE_ADD_BACK) {
                    notifyItemRangeInserted(mDataSource.size() + 1, value);
                    mRecycleView.scrollToEnd();
                } else if (type == MessageLayout.DATA_CHANGE_TYPE_UPDATE) {
                    notifyItemChanged(value + 1);
                } else if (type == MessageLayout.DATA_CHANGE_TYPE_LOAD || type == MessageLayout.DATA_CHANGE_TYPE_ADD_FRONT) {
                    //加载条目为数0，只更新动画
                    if (value == 0) {
                        notifyItemChanged(0);
                    } else {
                        //加载过程中有可能之前第一条与新加载的最后一条的时间间隔不超过5分钟，时间条目需去掉，所以这里的刷新要多一个条目
                        if (getItemCount() > value) {
                            notifyItemRangeInserted(0, value);
                        } else {
                            notifyItemRangeInserted(0, value);
                        }
                    }
                } else if (type == MessageLayout.DATA_CHANGE_TYPE_DELETE) {
                    notifyItemRemoved(value + 1);
                    notifyDataSetChanged();
                    mRecycleView.scrollToEnd();
                }
            }
        }).start();
    }
}
