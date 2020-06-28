package com.gy.wyy.chat.ui.voice;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.gy.wyy.chat.ui.R;
import com.gy.wyy.chat.ui.tool.AudioPlayer;
import com.gy.wyy.chat.ui.tool.ChatUiLog;
import com.gy.wyy.chat.ui.tool.ToastUtil;

/**
 *
 */
public class VoiceFragment extends Fragment implements VoiceFrameLayout.OnVoiceHandler {

    private String TAG = VoiceFragment.class.getSimpleName();

    private static final int PERMISSION_REQUEST_CODE = 101;

    private View mContentView;

    private VoiceFrameLayout frameLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mContentView = inflater.inflate(R.layout.input_voice_fragment,container,false);
        initView();
        return mContentView;
    }

    /**
     *
     */
    private void initView(){
        frameLayout = mContentView.findViewById(R.id.input_voice_fragment);
        frameLayout.setVoiceHandler(this);
    }

    @Override
    public void notPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void startRecording() {

    }

    @Override
    public void completeRecording(long d) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE){
            if (grantResults.length != 0 && grantResults[0] != PackageManager.PERMISSION_DENIED){
                ToastUtil.toastShort("授权成功！");
            }else {
                ToastUtil.toastShort("录音权限未授予，功能无法使用！");
            }
        }
    }
}
