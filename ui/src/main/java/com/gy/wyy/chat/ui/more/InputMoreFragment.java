package com.gy.wyy.chat.ui.more;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gy.wyy.chat.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class InputMoreFragment extends Fragment {

    private View view;
    private InputMoreLayout moreLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.input_more_fragment,container,false);
        initView();
        return view;
    }

    /**
     *
     */
    private void initView(){
        moreLayout = view.findViewById(R.id.input_more);
        List<InputMoreEntity> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            InputMoreEntity entity = new InputMoreEntity();
            entity.setResourceId(R.drawable.default_image);
            entity.setTitle("测试"+i);
            list.add(entity);
        }
        moreLayout.setDataSource(list);
    }
}
