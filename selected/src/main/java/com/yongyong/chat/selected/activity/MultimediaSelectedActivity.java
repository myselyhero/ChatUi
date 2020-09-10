package com.yongyong.chat.selected.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.yongyong.chat.selected.MultimediaSelectedConfig;
import com.yongyong.chat.selected.MultimediaSelectedContent;
import com.yongyong.chat.selected.R;
import com.yongyong.chat.selected.entity.MultimediaSelectedEntity;
import com.yongyong.chat.selected.entity.MultimediaSelectedFolderEntity;
import com.yongyong.chat.selected.tool.MultimediaScanUtil;
import com.yongyong.chat.selected.view.MultimediaSelectedBottomLayout;
import com.yongyong.chat.selected.view.MultimediaSelectedRecyclerView;
import com.yongyong.chat.selected.view.MultimediaSelectedTopLayout;

import java.io.Serializable;
import java.util.List;

/**
 * @author 王永勇
 * @// TODO: 2020/7/7
 */
public class MultimediaSelectedActivity extends MultimediaBaseActivity implements MultimediaScanUtil.OnMultimediaFolderCompleteListener,MultimediaScanUtil.OnMultimediaCompleteListener,
        MultimediaSelectedTopLayout.OnMultimediaSelectedTopLayoutListener, MultimediaSelectedRecyclerView.OnMultimediaSelectedRecyclerViewListener,
        MultimediaSelectedBottomLayout.OnMultimediaSelectedBottomLayoutListener {

    private String TAG = MultimediaSelectedActivity.class.getSimpleName();

    private MultimediaSelectedTopLayout topLayout;
    private MultimediaSelectedRecyclerView recyclerView;
    private MultimediaSelectedBottomLayout bottomLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multimedia_selected_activity);
        initView();
    }

    /**
     *
     */
    private void initView(){
        topLayout = findViewById(R.id.multimedia_selected_top);
        recyclerView = findViewById(R.id.multimedia_selected_item);
        bottomLayout = findViewById(R.id.multimedia_selected_bottom);

        topLayout.setLayoutListener(this);
        bottomLayout.setLayoutListener(this);

        recyclerView.setCamera(selectedConfig.isCamera());
        recyclerView.setViewListener(this);

        MultimediaScanUtil.create(this)
                .setGif(selectedConfig.isGif())
                .setMaxDuration(selectedConfig.getMaxDuration())
                .setMinDuration(selectedConfig.getMinDuration())
                .setMultimediaType(selectedConfig.getMultimediaType())
                .loadFolder(this);
    }

    @Override
    public void onComplete(List<MultimediaSelectedFolderEntity> list) {
        if (list == null || list.size() == 0)
            return;
        dataSource = list;
        topLayout.init(dataSource);
        MultimediaSelectedFolderEntity folderEntity = list.get(0);
        MultimediaScanUtil.create(this)
                .setGif(selectedConfig.isGif())
                .setMaxDuration(selectedConfig.getMaxDuration())
                .setMinDuration(selectedConfig.getMinDuration())
                .setMultimediaType(selectedConfig.getMultimediaType())
                .loadMultimedia(folderEntity.getBucketId(),1, this);
    }


    @Override
    public void onMultimediaComplete(long bucketId, List<MultimediaSelectedEntity> list) {
        for (MultimediaSelectedFolderEntity entity : dataSource) {
            if (entity.isChecked()){
                entity.setData(list);
                recyclerView.setDataSource(entity.getData());
                break;
            }
        }
    }

    @Override
    public void onConfirm() {
        if (selectedDataSource.size() == 0){
            return;
        }

        /**
         * 优先使用接口回调
         */
        if (MultimediaSelectedConfig.resultListener != null){
            MultimediaSelectedConfig.resultListener.onSuccess(selectedDataSource);
        }else {
            Intent intent = new Intent();
            intent.putExtra(MultimediaSelectedContent.MULTIMEDIA_SELECTED_CONTENT_RESULT_KEY, (Serializable) selectedDataSource);
            setResult(RESULT_OK,intent);
        }
        finish();
    }

    @Override
    public void onChecked(MultimediaSelectedFolderEntity entity) {
        if (entity.getData().size() != 0){
            recyclerView.setDataSource(entity.getData());
        }else {
            MultimediaScanUtil.create(this).loadMultimedia(entity.getBucketId(),1, this);
        }
    }

    @Override
    public void onCamera() {

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this,MultimediaPreviewActivity.class);
        intent.putExtra(MultimediaPreviewActivity.MULTIMEDIA_PREVIEW_POSITION,position);
        intent.putExtra(MultimediaPreviewActivity.MULTIMEDIA_PREVIEW_TYPE,MultimediaPreviewActivity.MULTIMEDIA_PREVIEW_ALL);
        startActivity(intent);
    }

    @Override
    public void onChecked(int position, MultimediaSelectedEntity entity) {
        if (entity.isChecked()){
            entity.setChecked(false);
            selectedDataSource.remove(entity);
            recyclerView.notifyItemChanged(position);
        }else {
            if (selectedConfig.isOnly()){
                for (MultimediaSelectedFolderEntity folderEntity : dataSource) {
                    if (folderEntity.getData().size() != 0){
                        for (MultimediaSelectedEntity selectedEntity : folderEntity.getData()) {
                            selectedEntity.setChecked(false);
                        }
                    }
                }
                selectedDataSource.clear();
                entity.setChecked(true);
                selectedDataSource.add(entity);
                recyclerView.notifyItemChanged();
            }else {
                if (selectedDataSource.size() < selectedConfig.getMaxNum()){
                    entity.setChecked(true);
                    selectedDataSource.add(entity);
                    recyclerView.notifyItemChanged(position);
                }
            }
        }
        topLayout.setCheckedNum(selectedDataSource.size());
        bottomLayout.setPreviewNum(selectedDataSource.size());
    }

    @Override
    public void onCheckedChanged(boolean isChecked) {

    }

    @Override
    public void onPreview() {
        if (selectedDataSource.size() == 0)
            return;
        Intent intent = new Intent(this,MultimediaPreviewActivity.class);
        intent.putExtra(MultimediaPreviewActivity.MULTIMEDIA_PREVIEW_POSITION,0);
        intent.putExtra(MultimediaPreviewActivity.MULTIMEDIA_PREVIEW_TYPE,MultimediaPreviewActivity.MULTIMEDIA_PREVIEW_CHECKED);
        startActivity(intent);
    }
}
