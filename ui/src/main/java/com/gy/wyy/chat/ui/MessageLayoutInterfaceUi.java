package com.gy.wyy.chat.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gy.wyy.chat.ui.model.MessageLayoutInterface;
import com.gy.wyy.chat.ui.model.OnMessageLayoutItemClickListener;

/**
 *
 */
public abstract class MessageLayoutInterfaceUi extends RecyclerView implements MessageLayoutInterface {

    private MessageProperties properties = MessageProperties.getInstance();

    protected OnMessageLayoutItemClickListener itemClickListener;
    protected MessageLayout.OnLoadMoreHandler mHandler;
    protected MessageLayout.OnEmptySpaceClickListener  onEmptySpaceClickListener;

    protected MessageAdapter messageAdapter;

    public MessageLayoutInterfaceUi(@NonNull Context context) {
        super(context);
        init();
    }

    public MessageLayoutInterfaceUi(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MessageLayoutInterfaceUi(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     *
     */
    private void init() {
        setLayoutFrozen(false);
        setItemViewCacheSize(0);
        setHasFixedSize(true);
        setFocusableInTouchMode(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        setLayoutManager(linearLayoutManager);

    }

    protected abstract void postSetAdapter(MessageAdapter adapter);

    @Override
    public void setAdapter(MessageAdapter messageAdapter) {
        super.setAdapter(messageAdapter);
        this.messageAdapter = messageAdapter;
        postSetAdapter(messageAdapter);
    }

    @Override
    public void setOnItemClick(OnMessageLayoutItemClickListener layoutItemClickListener) {
        itemClickListener = layoutItemClickListener;
    }

    @Override
    public void setRightBubble(Drawable drawable) {
        properties.setRightBubble(drawable);
    }

    @Override
    public Drawable getRightBubble() {
        return properties.getRightBubble();
    }

    @Override
    public void setLeftBubble(Drawable drawable) {
        properties.setLeftBubble(drawable);
    }

    @Override
    public Drawable getLeftBubble() {
        return properties.getLeftBubble();
    }

    @Override
    public void setRightContentFontColor(int color) {
        properties.setRightContentFontColor(color);
    }

    @Override
    public int getRightContentFontColor() {
        return properties.getRightContentFontColor();
    }

    @Override
    public void setRightContentFontSize(int size) {
        properties.setRightContentFontSize(size);
    }

    @Override
    public int getRightContentFontSize() {
        return properties.getRightContentFontSize();
    }

    @Override
    public void setLeftContentFontColor(int color) {
        properties.setLeftContentFontColor(color);
    }

    @Override
    public int getLeftContentFontColor() {
        return properties.getLeftContentFontColor();
    }

    @Override
    public void setLeftContentFontSize(int size) {
        properties.getLeftContentFontSize();
    }

    @Override
    public int getLeftContentFontSize() {
        return properties.getLeftContentFontSize();
    }

    @Override
    public void setTimeBubble(Drawable drawable) {
        properties.setTimeBubble(drawable);
    }

    @Override
    public Drawable getTimeBubble() {
        return properties.getTimeBubble();
    }

    @Override
    public void setTimeFontSize(int size) {
        properties.setTimeFontSize(size);
    }

    @Override
    public int getTimeFontSize() {
        return properties.getTimeFontSize();
    }

    @Override
    public void setTimeFontColor(int color) {
        properties.setTimeFontColor(color);
    }

    @Override
    public int getTimeFontColor() {
        return properties.getTimeFontColor();
    }
}
