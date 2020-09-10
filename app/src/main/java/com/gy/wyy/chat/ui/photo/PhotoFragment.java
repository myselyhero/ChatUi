package com.gy.wyy.chat.ui.photo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.gy.wyy.chat.R;
import com.gy.wyy.chat.ui.tool.ChatUiLog;
import com.gy.wyy.chat.ui.tool.ToastUtil;
import com.yongyong.chat.selected.MultimediaSelectedBuild;
import com.yongyong.chat.selected.MultimediaSelectedResultListener;
import com.yongyong.chat.selected.MultimediaSelectedType;
import com.yongyong.chat.selected.entity.MultimediaSelectedEntity;
import com.yongyong.chat.selected.view.MultimediaSelectedView;

import java.util.List;

/**
 *
 */
public class PhotoFragment extends Fragment implements MultimediaSelectedView.OnMultimediaSelectedViewListener,MultimediaSelectedResultListener {

    private View contentView;

    private MultimediaSelectedView selectedView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_photo, container, false);
        init();
        return contentView;
    }

    /**
     *
     */
    private void init(){
        selectedView = contentView.findViewById(R.id.multimedia_selected_view);
        selectedView.setViewListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onAdd() {
        MultimediaSelectedBuild.create(getContext())
                .isCamera(true)
                .isGif(true)
                .setMultimediaType(MultimediaSelectedType.All)
                .start(this);
    }

    @Override
    public void onItem(int position, MultimediaSelectedEntity entity) {
        ToastUtil.toastShort(entity.getPath());
    }

    @Override
    public void onSuccess(List<MultimediaSelectedEntity> list) {
        if (list == null || list.size() == 0)
            return;
        selectedView.setDataSource(list);
    }

    @Override
    public void onError(int code) {

    }
}