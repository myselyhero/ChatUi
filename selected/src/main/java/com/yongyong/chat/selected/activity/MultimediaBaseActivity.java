package com.yongyong.chat.selected.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yongyong.chat.selected.MultimediaSelectedConfig;
import com.yongyong.chat.selected.MultimediaSelectedContent;
import com.yongyong.chat.selected.MultimediaSelectedType;
import com.yongyong.chat.selected.entity.MultimediaSelectedEntity;
import com.yongyong.chat.selected.entity.MultimediaSelectedFolderEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王永勇
 * @// TODO: 2020/7/7
 */
public class MultimediaBaseActivity extends AppCompatActivity {

    protected MultimediaSelectedConfig selectedConfig;

    protected static List<MultimediaSelectedFolderEntity> dataSource = new ArrayList<>();
    protected static List<MultimediaSelectedEntity> selectedDataSource = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedConfig = getIntent().getParcelableExtra(MultimediaSelectedContent.MULTIMEDIA_SELECTED_CONTENT_BASE_KEY);
        if (selectedConfig == null)
            selectedConfig = MultimediaSelectedConfig.getInstance();
    }

    /**
     *
     * @param entity
     */
    protected boolean addItem(MultimediaSelectedEntity entity){
        if (entity.isChecked()){
            entity.setChecked(false);
            selectedDataSource.remove(entity);
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
            }else {
                if (selectedDataSource.size() < selectedConfig.getMaxNum()){
                    entity.setChecked(true);
                    selectedDataSource.add(entity);
                }else {
                    String str = "张图片";
                    if (selectedConfig.getMultimediaType() == MultimediaSelectedType.VIDEO)
                        str = "个视频";
                    Toast.makeText(this,"最多选择"+selectedConfig.getMaxNum()+str,Toast.LENGTH_SHORT).show();
                }
            }
        }
        return entity.isChecked();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dataSource != null && dataSource.size() != 0){
            dataSource.clear();
        }
        if (selectedDataSource != null && selectedDataSource.size() != 0){
            selectedDataSource.clear();
        }
    }
}
