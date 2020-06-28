package com.gy.wyy.chat.ui.message;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.gy.wyy.chat.R;
import com.gy.wyy.chat.ui.ChatLayout;

/**
 *
 */
public class MessageFragment extends Fragment {

    private View rootView;
    private MessageViewModel messageViewModel;

    private ChatLayout chatLayout;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);//获取viewModel并于此fragment绑定
        rootView = inflater.inflate(R.layout.fragment_message, container, false);
        initView();
        messageViewModel.getLiveData().observe(getViewLifecycleOwner(), messageEntity -> {//注册观察者、观察数据变化
            chatLayout.getMessageAdapter().setDataSource(messageViewModel.getLiveData().getValue());
        });
        return rootView;
    }

    /**
     *
     */
    private void initView(){
        chatLayout = rootView.findViewById(R.id.fragment_message_chat);
    }
}
