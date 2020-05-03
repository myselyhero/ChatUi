package com.gy.wyy.chat.ui.face;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 *
 */
public class EmojiRecyclerView extends RecyclerView {

    private EmojiRecyclerAdapter adapter;

    public EmojiRecyclerView(@NonNull Context context) {
        super(context);
        initView();
    }

    public EmojiRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public EmojiRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     *
     */
    private void initView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        setLayoutManager(linearLayoutManager);
        addItemDecoration(new ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = 2;
                outRect.right = 2;
            }
        });
    }

    /**
     *
     * @param list
     */
    public void setDataSource(List<String> list){
        if (list == null)
            return;

        adapter = new EmojiRecyclerAdapter(getContext(),list);
        setAdapter(adapter);
    }

    /**
     *
     */
    public void notifyAdapter(){
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }
}
