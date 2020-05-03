package com.gy.wyy.chat.ui.more;

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
public class InputMoreLayout extends RecyclerView {

    private InputMoreAdapter adapter;

    public InputMoreLayout(Context context) {
        super(context);
        initView();
    }

    public InputMoreLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public InputMoreLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     *
     */
    private void initView(){
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        setLayoutManager(manager);
        addItemDecoration(new ItemDecoration(){

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = 2;
                outRect.right = 2;
            }
        });

        adapter = new InputMoreAdapter(getContext());
        setAdapter(adapter);
    }

    /**
     *
     * @param list
     */
    public void setDataSource(List<InputMoreEntity> list){
        if (list == null)
            return;
        adapter.setDataSource(list);
    }
}
