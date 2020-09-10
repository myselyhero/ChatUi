package com.gy.wyy.chat.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gy.wyy.chat.R;
import com.gy.wyy.chat.player.view.VideoLayout;
import com.gy.wyy.chat.ui.home.HomeEntity;
import com.gy.wyy.chat.ui.tool.GlideEngine;

import java.util.List;

/**
 *
 */
public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<HomeEntity> dataSource;
    private OnHomeAdapterListener listener;

    public HomeAdapter(Context context,List<HomeEntity> list,OnHomeAdapterListener listener){
        mContext = context;
        dataSource = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == HomeEntity.HOME_HOLDER_IMAGE){
            View view = inflater.inflate(R.layout.fragment_home_image,parent,false);
            return new HomeAdapterImageHolder(view);
        }else {
            View view = inflater.inflate(R.layout.fragment_home_video,parent,false);
            return new HomeAdapterVideoHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HomeEntity entity = dataSource.get(position);
        if (holder instanceof  HomeAdapterImageHolder){
            HomeAdapterImageHolder imageHolder = (HomeAdapterImageHolder) holder;
            GlideEngine.loader(entity.getUrl(),imageHolder.imageView);
            holder.itemView.setOnClickListener(v -> {
                listener.onListener(position,entity);
            });
        }else {
            HomeAdapterVideoHolder videoHolder = (HomeAdapterVideoHolder) holder;
            videoHolder.videoLayout.initPlayer(entity.getUrl(),"");
        }
    }

    @Override
    public int getItemViewType(int position) {
        return dataSource.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    /**
     *
     */
    class HomeAdapterImageHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public HomeAdapterImageHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.fragment_home_item_iv);
        }
    }

    /**
     *
     */
    class HomeAdapterVideoHolder extends RecyclerView.ViewHolder {

        VideoLayout videoLayout;

        public HomeAdapterVideoHolder(@NonNull View itemView) {
            super(itemView);
            videoLayout = itemView.findViewById(R.id.fragment_home_video_layout);
        }
    }

    /**
     *
     */
    public interface OnHomeAdapterListener {

        /**
         *
         * @param position
         * @param entity
         */
        void onListener(int position,HomeEntity entity);
    }
}
