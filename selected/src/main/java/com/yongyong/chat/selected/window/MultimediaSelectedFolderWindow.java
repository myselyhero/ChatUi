package com.yongyong.chat.selected.window;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yongyong.chat.selected.R;
import com.yongyong.chat.selected.entity.MultimediaSelectedFolderEntity;
import com.yongyong.chat.selected.tool.GlideEngine;

import java.util.List;

/**
 * @author 王永勇
 * @// TODO: 2020/7/8
 */
public class MultimediaSelectedFolderWindow extends PopupWindow {

    private Context mContext;
    private List<MultimediaSelectedFolderEntity> dataSource;
    private OnMultimediaSelectedFolderWindowListener windowListener;

    /**
     *
     * @param context
     */
    public MultimediaSelectedFolderWindow(Context context,List<MultimediaSelectedFolderEntity> data,OnMultimediaSelectedFolderWindowListener listener){
        mContext = context;
        dataSource = data;
        windowListener = listener;

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置pw中的控件能够获取焦点
        setFocusable(true);
        //设置可以通过点击mPopupWindow外部关闭mPopupWindow
        setOutsideTouchable(true);
        //设置mPopupWindow的进出动画
        setAnimationStyle(R.style.multimedia_selected_folder_window);

        View view = View.inflate(context, R.layout.multimedia_selected_folder_window, null);
        RecyclerView recyclerView = view.findViewById(R.id.multimedia_selected_folder_window_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        MultimediaSelectedFolderWindowAdapter adapter = new MultimediaSelectedFolderWindowAdapter();
        recyclerView.setAdapter(adapter);
        /*recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 1;
            }
        });*/

        setContentView(view);
        update();//刷新mPopupWindow

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                if (windowListener != null)
                    windowListener.onDismiss();
            }
        });
    }

    /**
     *
     */
    class MultimediaSelectedFolderWindowAdapter extends RecyclerView.Adapter<MultimediaSelectedFolderWindowViewHolder> {

        @NonNull
        @Override
        public MultimediaSelectedFolderWindowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.multimedia_selected_folder_window_item,parent,false);
            return new MultimediaSelectedFolderWindowViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MultimediaSelectedFolderWindowViewHolder holder, int position) {
            MultimediaSelectedFolderEntity entity = dataSource.get(position);
            if (!TextUtils.isEmpty(entity.getPath())){
                GlideEngine.loader(mContext,entity.getPath(),holder.imageView);
            }

            String str = entity.getFolder()+" ("+entity.getNum()+")";
            SpannableString spannableString = new SpannableString(str);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#565656")), entity.getFolder().length(),str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.textView.setText(spannableString);

            if (entity.isChecked()){
                holder.checkedImageView.setVisibility(View.VISIBLE);
            }else {
                holder.checkedImageView.setVisibility(View.GONE);
            }

            holder.itemView.setOnClickListener(v -> {
                if (entity.isChecked()){
                    return;
                }
                for (MultimediaSelectedFolderEntity folderEntity:dataSource) {
                    folderEntity.setChecked(false);
                }
                entity.setChecked(true);
                notifyDataSetChanged();
                if (windowListener != null)
                    windowListener.onListener(entity);

                dismiss();
            });
        }

        @Override
        public int getItemCount() {
            return dataSource == null ? 0 : dataSource.size();
        }
    }

    /**
     *
     */
    class MultimediaSelectedFolderWindowViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;
        private ImageView checkedImageView;

        public MultimediaSelectedFolderWindowViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.multimedia_selected_folder_window_item_thumbnail);
            textView = itemView.findViewById(R.id.multimedia_selected_folder_window_item_name);
            checkedImageView = itemView.findViewById(R.id.multimedia_selected_folder_window_item_checked);
        }
    }

    /**
     *
     */
    public interface OnMultimediaSelectedFolderWindowListener{

        /**
         *
         */
        void onDismiss();

        /**
         *
         * @param entity
         */
        void onListener(MultimediaSelectedFolderEntity entity);
    }
}
