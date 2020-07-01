package com.gy.wyy.chat.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gy.wyy.chat.R;
import com.gy.wyy.chat.ui.tool.GlideEngine;

import java.util.List;

/**
 *
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeAdapterViewHolder> {

    private Context mContext;
    private List<String> dataSource;
    private OnHomeAdapterListener listener;

    public HomeAdapter(Context context,List<String> list,OnHomeAdapterListener listener){
        mContext = context;
        dataSource = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HomeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_home_item,parent,false);
        return new HomeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapterViewHolder holder, int position) {
        GlideEngine.loader(dataSource.get(position),holder.imageView);
        holder.itemView.setOnClickListener(v -> {
            listener.onListener(position,dataSource.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    class HomeAdapterViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public HomeAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.fragment_home_item_iv);
        }
    }

    /**
     *
     */
    public interface OnHomeAdapterListener {

        /**
         *
         * @param position
         * @param data
         */
        void onListener(int position,String data);
    }
}
