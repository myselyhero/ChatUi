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

    public HomeAdapter(Context context,List<String> list){
        mContext = context;
        dataSource = list;
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
}
