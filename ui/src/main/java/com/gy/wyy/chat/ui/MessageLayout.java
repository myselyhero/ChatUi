package com.gy.wyy.chat.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gy.wyy.chat.ui.adapter.MessageAdapter;
import com.gy.wyy.chat.ui.entity.MessageEntity;
import com.gy.wyy.chat.ui.model.OnMessageLayoutItemClickListener;

/**
 *
 */
public class MessageLayout extends MessageLayoutInterfaceUi {

    public static final int DATA_CHANGE_TYPE_REFRESH = 0;
    public static final int DATA_CHANGE_TYPE_LOAD = 1;
    public static final int DATA_CHANGE_TYPE_ADD_FRONT = 2;
    public static final int DATA_CHANGE_TYPE_ADD_BACK = 3;
    public static final int DATA_CHANGE_TYPE_UPDATE = 4;
    public static final int DATA_CHANGE_TYPE_DELETE = 5;
    public static final int DATA_CHANGE_TYPE_CLEAR = 6;

    public MessageLayout(@NonNull Context context) {
        super(context);
    }

    public MessageLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MessageLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void postSetAdapter(MessageAdapter adapter) {
        adapter.setOnItemClickListener(new OnMessageLayoutItemClickListener() {
            @Override
            public void onMessageClick(View view, int position, MessageEntity entity) {
                if (itemClickListener != null){
                    itemClickListener.onMessageClick(view,position,entity);
                }
            }

            @Override
            public void onMessageLongClick(View view, int position, MessageEntity entity) {
                if (itemClickListener != null){
                    itemClickListener.onMessageLongClick(view,position,entity);
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_UP) {
            View child = findChildViewUnder(e.getX(), e.getY());
            if (child == null) {
                if (onEmptySpaceClickListener != null)
                    onEmptySpaceClickListener.onClick();
            } else if (child instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) child;
                final int count = group.getChildCount();
                float x = e.getRawX();
                float y = e.getRawY();
                View touchChild = null;
                for (int i = count - 1; i >= 0; i--) {
                    final View innerChild = group.getChildAt(i);
                    int position[] = new int[2];
                    innerChild.getLocationOnScreen(position);
                    if (x >= position[0]
                            && x <= position[0] + innerChild.getMeasuredWidth()
                            && y >= position[1]
                            && y <= position[1] + innerChild.getMeasuredHeight()) {
                        touchChild = innerChild;
                        break;
                    }
                }
                if (touchChild == null) {
                    if (onEmptySpaceClickListener != null) {
                        onEmptySpaceClickListener.onClick();
                    }
                }
            }
        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            if (mHandler != null) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
                int firstPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
                int lastPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                if (firstPosition == 0 && ((lastPosition - firstPosition + 1) < getAdapter().getItemCount())) {
                    if (getAdapter() instanceof MessageAdapter) {
                        //((MessageAdapter) getAdapter()).showLoading();
                    }
                    mHandler.loadMore();
                }
            }
        }
    }

    /**
     *
     */
    public void scrollToEnd() {
        if (getAdapter() != null) {
            scrollToPosition(getAdapter().getItemCount() - 1);
        }
    }

    /**
     *
     * @param handler
     */
    public void setHandler(OnLoadMoreHandler handler){
        mHandler = handler;
    }

    /**
     *
     * @param clickListener
     */
    public void setOnEmptySpaceClickListener(OnEmptySpaceClickListener clickListener){
        onEmptySpaceClickListener = clickListener;
    }

    /**
     *  点击空白处
     */
    public interface OnEmptySpaceClickListener {
        void onClick();
    }

    /**
     *
     */
    public interface OnLoadMoreHandler {
        void loadMore();
    }
}
