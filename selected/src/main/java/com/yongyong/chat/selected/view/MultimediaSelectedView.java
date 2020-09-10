package com.yongyong.chat.selected.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.yongyong.chat.selected.entity.MultimediaSelectedEntity;
import com.yongyong.chat.selected.R;
import com.yongyong.chat.selected.tool.GlideEngine;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 王永勇
 * @// TODO: 2020/7/7
 */
public class MultimediaSelectedView extends RecyclerView {

    private static final int MULTIMEDIA_SELECTED_TYPE_ADD = 101;
    private static final int MULTIMEDIA_SELECTED_TYPE_PICTURE = 102;

    private AttributeSet attributeSet;

    private boolean isAdd = true;
    private int maxNum = 9;
    private int spanCount = 3;
    private Integer defaultImage = R.drawable.multimedia_selected_view_add;
    private Integer defaultDelete = R.drawable.multimedia_selected_view_delete;

    private List<MultimediaSelectedEntity> dataSource = new ArrayList<>();
    private MultimediaSelectedViewAdapter viewAdapter;
    private OnMultimediaSelectedViewListener viewListener;

    public MultimediaSelectedView(@NonNull Context context) {
        super(context);
        init();
    }

    public MultimediaSelectedView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (attributeSet == null)
            attributeSet = attrs;
        init();
    }

    public MultimediaSelectedView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attributeSet == null)
            attributeSet = attrs;
        init();
    }

    /**
     *  初始化
     */
    private void init(){

        if (attributeSet != null){
            TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.MultimediaSelectedView, 0, 0);
            try {
                isAdd = typedArray.getBoolean(R.styleable.MultimediaSelectedView_add,isAdd);
                maxNum = typedArray.getInt(R.styleable.MultimediaSelectedView_maxNum,maxNum);
                spanCount = typedArray.getInt(R.styleable.MultimediaSelectedView_spanCount,spanCount);
                defaultImage = typedArray.getResourceId(R.styleable.MultimediaSelectedView_defaultImage,defaultImage);
                defaultDelete = typedArray.getResourceId(R.styleable.MultimediaSelectedView_deleteImage,defaultDelete);
            } finally {
                typedArray.recycle();
            }
        }

        setOverScrollMode(OVER_SCROLL_NEVER);
        setHasFixedSize(true);
        addItemDecoration(new ItemDecoration(){
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = 3;
                outRect.top = 3;
                outRect.right = 3;
                outRect.bottom = 3;
            }
        });
        setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        ((SimpleItemAnimator) Objects.requireNonNull(this.getItemAnimator())).setSupportsChangeAnimations(false);
        viewAdapter = new MultimediaSelectedViewAdapter();
        setAdapter(viewAdapter);
    }

    /**
     *
     * @return
     */
    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    /**
     *
     * @return
     */
    public List<MultimediaSelectedEntity> getDataSource() {
        return dataSource;
    }

    /**
     *
     * @param data
     */
    public void setDataSource(List<MultimediaSelectedEntity> data) {
        if (data == null)
            return;
        dataSource.addAll(data);
        viewAdapter.notifyDataSetChanged();
    }

    /**
     *
     * @param viewListener
     */
    public void setViewListener(OnMultimediaSelectedViewListener viewListener) {
        this.viewListener = viewListener;
    }

    /**
     *
     */
    class MultimediaSelectedViewAdapter extends Adapter<MultimediaSelectedViewHolder> {


        @NonNull
        @Override
        public MultimediaSelectedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.multimedia_selected_view_item,parent,false);
            return new MultimediaSelectedViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MultimediaSelectedViewHolder holder, int position) {
            //少于8张，显示继续添加的图标
            if (getItemViewType(position) == MULTIMEDIA_SELECTED_TYPE_ADD && isAdd) {
                holder.imageView.setImageResource(defaultImage);
                /**
                 *
                 */
                if (viewListener != null) {
                    holder.itemView.setOnClickListener(v -> {
                        viewListener.onAdd();
                    });
                }
            } else {
                if (isAdd){
                    holder.deleteImageView.setVisibility(View.VISIBLE);
                    holder.deleteImageView.setImageResource(defaultDelete);
                    holder.deleteImageView.setOnClickListener(v -> {
                        int index = holder.getAdapterPosition();
                        if (index != RecyclerView.NO_POSITION) {
                            dataSource.remove(index);
                            notifyItemRemoved(index);
                            notifyItemRangeChanged(index, dataSource.size());
                        }
                    });
                }

                /**/
                MultimediaSelectedEntity entity = dataSource.get(position);
                String path = entity.getPath();
                if (!TextUtils.isEmpty(entity.getCutPath())) {
                    path = entity.getCutPath();
                } else if (!TextUtils.isEmpty(entity.getCompressPath())) {
                    path = entity.getCompressPath();
                }
                GlideEngine.loader(getContext(),path,holder.imageView);

                /**
                 *
                 */
                if (viewListener != null) {
                    holder.itemView.setOnClickListener(v -> {
                        viewListener.onItem(position,entity);
                    });
                }
            }
        }

        @Override
        public int getItemCount() {
            if (getDataSourceSize() < maxNum && isAdd) {
                return getDataSourceSize() + 1;
            } else {
                return getDataSourceSize();
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == getDataSourceSize() && isAdd) {
                return MULTIMEDIA_SELECTED_TYPE_ADD;
            } else {
                return MULTIMEDIA_SELECTED_TYPE_PICTURE;
            }
        }

        /**
         *
         * @return
         */
        private int getDataSourceSize(){
            return dataSource == null ? 0 : dataSource.size();
        }
    }

    /**
     *
     */
    class MultimediaSelectedViewHolder extends ViewHolder {

        ImageView imageView;
        ImageView deleteImageView;

        public MultimediaSelectedViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.multimedia_selected_view_item_add);
            deleteImageView = itemView.findViewById(R.id.multimedia_selected_view_item_del);
        }
    }

    /**
     *
     */
    public interface OnMultimediaSelectedViewListener {

        void onAdd();

        /**
         *
         * @param position
         * @param entity
         */
        void onItem(int position,MultimediaSelectedEntity entity);
    }
}
