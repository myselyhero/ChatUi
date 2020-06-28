package com.gy.wyy.chat.ui.more;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
public class InputMoreAdapter extends BaseAdapter {

    public Context context;
    private List<InputMoreEntity> dataSource = new ArrayList<>();

    public InputMoreAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InputMoreViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.input_more_grid_item,parent,false);
            viewHolder = new InputMoreViewHolder();
            viewHolder.textView = convertView.findViewById(R.id.input_more_grid_tv);
            viewHolder.imageView = convertView.findViewById(R.id.input_more_grid_iv);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (InputMoreViewHolder) convertView.getTag();
        }

        InputMoreEntity entity = dataSource.get(position);
        GlideEngine.loaderCircle(entity.getResourceId(),viewHolder.imageView,10);
        viewHolder.textView.setText(entity.getTitle());

        return convertView;
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
    public class InputMoreViewHolder {

        TextView textView;
        ImageView imageView;
    }
}
