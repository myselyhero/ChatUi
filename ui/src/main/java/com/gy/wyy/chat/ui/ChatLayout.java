package com.gy.wyy.chat.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.gy.wyy.chat.ui.input.InputLayout;
import com.gy.wyy.chat.ui.model.ChatLayoutInterface;
import com.gy.wyy.chat.ui.model.OnMessageCallBack;

/**
 *
 */
public class ChatLayout extends LinearLayout implements ChatLayoutInterface, OnMessageCallBack {

    private MessageLayout messageLayout;
    private InputLayout inputLayout;

    private MessageAdapter messageAdapter;

    public ChatLayout(Context context) {
        super(context);
        initView();
    }

    public ChatLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ChatLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     *
     */
    private void initView(){
        LayoutInflater.from(getContext()).inflate(R.layout.chat_layout,this);
        messageLayout = findViewById(R.id.chat_layout_msg);
        inputLayout = findViewById(R.id.chat_layout_input);

        //
        messageAdapter = new MessageAdapter(getContext());
        messageLayout.setAdapter(messageAdapter);

        MessageManagerKit.getInstance().get(this);
    }

    @Override
    public void initDefault() {

    }

    @Override
    public MessageLayout getMessageLayout() {
        return messageLayout;
    }

    @Override
    public MessageAdapter getMessageAdapter() {
        return messageAdapter;
    }

    @Override
    public InputLayout getInputLayout() {
        return inputLayout;
    }

    @Override
    public void onSuccess(Object object) {

    }

    @Override
    public void onError(int code) {

    }
}
