package com.gy.wyy.chat.ui.face;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.gy.wyy.chat.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class EmojiFragment extends Fragment {

    private View view;
    private EmojiRecyclerView emojiRecyclerView;
    private List<String> dataSource = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.input_emoji_fragment,container,false);
        initView();
        return view;
    }

    /**
     *
     */
    private void initView(){
        emojiRecyclerView = view.findViewById(R.id.input_emoji_item);
        emojiRecyclerView.setDataSource(dataSource);
    }

    /**
     *
     * @param matching
     */
    public void emojiMatching(String matching){
        Log.e(EmojiFragment.class.getSimpleName(), "emojiMatching: "+matching);
        for (int i = 0; i < 10; i++) {
            dataSource.add("http://pic2.zhimg.com/50/v2-6f7ecca94b20af97d1716bfd332fbaf6_hd.jpg");
        }
        if (emojiRecyclerView != null)
            emojiRecyclerView.notifyAdapter();
    }

    /**
     *
     */
    public void clear(){
        if (dataSource.size() != 0){
            dataSource.clear();
            emojiRecyclerView.notifyAdapter();
        }
    }
}
