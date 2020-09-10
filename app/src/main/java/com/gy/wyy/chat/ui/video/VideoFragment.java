package com.gy.wyy.chat.ui.video;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.gy.wyy.chat.R;
import com.gy.wyy.chat.player.view.VideoLayout;

public class VideoFragment extends Fragment {

    private VideoViewModel videoViewModel;
    private VideoLayout videoLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //slideshowViewModel = ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_video, container, false);
        videoLayout = root.findViewById(R.id.video_layout);
        videoLayout.initPlayer("http://116.62.110.155:8081/static/video/鹤谷物流.mp4","小熊");
        /*final TextView textView = root.findViewById(R.id.text_slideshow);
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoLayout != null){
            videoLayout.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (videoLayout != null){
            videoLayout.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (videoLayout != null){
            videoLayout.onDestroy();
        }
    }
}