package com.yongyong.chat.selected.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.yongyong.chat.selected.MultimediaSelectedConfig;
import com.yongyong.chat.selected.R;
import com.yongyong.chat.selected.entity.MultimediaSelectedEntity;
import com.yongyong.chat.selected.tool.GlideEngine;

import java.util.List;
import java.util.Objects;

/**
 * @author 王永勇
 * @// TODO: 2020/7/7
 */
public class MultimediaSelectedRecyclerView extends RecyclerView {

    private static final int MULTIMEDIA_SELECTED_HOLDER_TYPE_CAMERA = 101;
    private static final int MULTIMEDIA_SELECTED_HOLDER_TYPE_PICTURE = 102;

    /**
     *
     */
    private boolean isCamera = true;


    private List<MultimediaSelectedEntity> dataSource;
    private MultimediaSelectedRecyclerViewAdapter mAdapter;

    private OnMultimediaSelectedRecyclerViewListener viewListener;

    public MultimediaSelectedRecyclerView(@NonNull Context context) {
        super(context);
        initView();
    }

    public MultimediaSelectedRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MultimediaSelectedRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     *
     */
    private void initView(){
        setOverScrollMode(OVER_SCROLL_NEVER);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),4);
        setLayoutManager(layoutManager);
        addItemDecoration(new ItemDecoration(){

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.right = 2;
                outRect.left = 2;
                outRect.top = 2;
                outRect.bottom = 2;
            }
        });
        ((SimpleItemAnimator) Objects.requireNonNull(this.getItemAnimator())).setSupportsChangeAnimations(false);

        mAdapter = new MultimediaSelectedRecyclerViewAdapter();
        setAdapter(mAdapter);
    }

    /**
     *
     * @param camera
     */
    public void setCamera(boolean camera) {
        isCamera = camera;
    }

    /**
     *
     * @param data
     */
    public void setDataSource(List<MultimediaSelectedEntity> data) {
        if (data == null)
            return;
        post(new Runnable() {
            @Override
            public void run() {
                if (dataSource != null && dataSource.size() != 0){
                    dataSource.clear();
                }
                dataSource = data;
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     *
     * @param position
     */
    public void notifyItemChanged(int position){
        /*if (checked && imageView != null){
            zoom(imageView);
        }else {
            disZoom(imageView);
        }*/
        if (mAdapter != null)
            mAdapter.notifyItemChanged(position);
    }

    /**
     *
     */
    public void notifyItemChanged(){
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }

    /**
     *
     * @param viewListener
     */
    public void setViewListener(OnMultimediaSelectedRecyclerViewListener viewListener) {
        this.viewListener = viewListener;
    }

    /**
     *
     */
    class MultimediaSelectedRecyclerViewAdapter extends RecyclerView.Adapter<MultimediaSelectedRecyclerViewHolder> {

        @NonNull
        @Override
        public MultimediaSelectedRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.multimedia_selected_recycer_item,parent,false);
            return new MultimediaSelectedRecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MultimediaSelectedRecyclerViewHolder holder, int position) {
            if (getItemViewType(position) == MULTIMEDIA_SELECTED_HOLDER_TYPE_CAMERA) {
                holder.imageView.setScaleType(ImageView.ScaleType.CENTER);
                holder.imageView.setImageResource(R.drawable.multimedia_selected_camera);
                holder.checkedImageView.setVisibility(View.GONE);
                holder.textView.setVisibility(View.GONE);
                if (viewListener != null){
                    holder.itemView.setOnClickListener(v -> {
                        viewListener.onCamera();
                    });
                }
            } else {
                holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                holder.checkedImageView.setVisibility(View.VISIBLE);
                holder.textView.setVisibility(View.VISIBLE);
                MultimediaSelectedEntity entity = dataSource.get(position);
                if (!TextUtils.isEmpty(entity.getPath())){
                    GlideEngine.loader(getContext(),entity.getPath(),holder.imageView);
                }

                if (entity.isChecked()){
                    holder.checkedImageView.setImageResource(R.drawable.multimedia_selected_sel);
                }else {
                    holder.checkedImageView.setImageResource(R.drawable.multimedia_selected_un);
                }

                if (viewListener != null){
                    holder.checkedImageView.setOnClickListener(v -> {
                        viewListener.onChecked(position,entity);
                    });
                    holder.itemView.setOnClickListener(v -> {
                        viewListener.onItemClick(position);
                    });
                }
            }
        }

        @Override
        public int getItemCount() {
            return dataSource == null ? 0 : dataSource.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (isCamera && position == 0){
                return MULTIMEDIA_SELECTED_HOLDER_TYPE_CAMERA;
            }else {
                return MULTIMEDIA_SELECTED_HOLDER_TYPE_PICTURE;
            }
        }
    }

    /**
     *
     */
    class MultimediaSelectedRecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        ImageView checkedImageView;
        TextView textView;

        public MultimediaSelectedRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.multimedia_Selected_recycler_item_image);
            checkedImageView = itemView.findViewById(R.id.multimedia_Selected_recycler_item_checked);
            textView = itemView.findViewById(R.id.multimedia_Selected_recycler_item_flag);
        }
    }

    /**
     *
     * @param view
     */
    private void zoom(ImageView view){
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.12f),
                ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.12f)
        );
        set.setDuration(450);
        set.start();
    }

    /**
     *
     * @param view
     */
    private void disZoom(ImageView view) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(view, "scaleX", 1.12f, 1f),
                ObjectAnimator.ofFloat(view, "scaleY", 1.12f, 1f)
        );
        set.setDuration(450);
        set.start();
    }

    /**
     *
     */
    public interface OnMultimediaSelectedRecyclerViewListener {

        void onCamera();

        /**
         *
         * @param position
         */
        void onItemClick(int position);

        /**
         *
         * @param position
         * @param entity
         */
        void onChecked(int position,MultimediaSelectedEntity entity);
    }
}
