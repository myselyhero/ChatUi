package com.gy.wyy.chat.ui.more;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gy.wyy.chat.ui.R;
import com.gy.wyy.chat.ui.tool.GlideEngine;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class InputMoreAdapter extends RecyclerView.Adapter<InputMoreAdapter.InputMoreViewHolder> {

    public Context context;
    private List<InputMoreEntity> dataSource = new ArrayList<>();

    public InputMoreAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public InputMoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.input_more_layout,parent,false);
        return new InputMoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InputMoreViewHolder holder, int position) {
        InputMoreEntity entity = dataSource.get(position);
        GlideEngine.loaderCircle(entity.getResourceId(),holder.imageView,10);
        holder.textView.setText(entity.getTitle());
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    /**
     *
     * @param list
     */
    public void setDataSource(List<InputMoreEntity> list){
        if (list == null)
            return;
        if (dataSource.size() != 0)
            dataSource.clear();
        dataSource.addAll(list);
        notifyDataSetChanged();
    }

    /**
     *
     */
    public class InputMoreViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public InputMoreViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.input_more_tv);
            imageView = itemView.findViewById(R.id.input_more_iv);
        }
    }
}
