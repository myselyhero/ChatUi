package com.gy.wyy.chat.ui.face;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gy.wyy.chat.ui.R;

import java.util.List;

/**
 *
 */
public class EmojiRecyclerAdapter extends RecyclerView.Adapter<EmojiRecyclerAdapter.EmojiRecyclerViewHolder> {

    public Context context;
    private List<String> dataSource;

    public EmojiRecyclerAdapter(Context context,List<String> list){
        this.context = context;
        dataSource = list;
    }

    @NonNull
    @Override
    public EmojiRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.input_emoji_item,parent,false);
        return new EmojiRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmojiRecyclerViewHolder holder, int position) {
        String emoji = dataSource.get(position);

        if (!TextUtils.isEmpty(emoji)){
            Glide.with(context)
                    .asBitmap()
                    .load(emoji)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    /**
     *
     */
    public class EmojiRecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public EmojiRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.input_emoji_item_iv);
        }
    }
}
