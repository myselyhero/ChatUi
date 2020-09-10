package com.yongyong.chat.selected.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.yongyong.chat.selected.R;
import com.yongyong.chat.selected.entity.MultimediaSelectedEntity;
import com.yongyong.chat.selected.entity.MultimediaSelectedFolderEntity;
import com.yongyong.chat.selected.tool.GlideEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王永勇
 * @// TODO: 2020/7/9
 */
public class MultimediaPreviewActivity extends MultimediaBaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private String TAG = MultimediaPreviewActivity.class.getSimpleName();

    public static final int MULTIMEDIA_PREVIEW_ALL = 101;
    public static final int MULTIMEDIA_PREVIEW_CHECKED = 102;

    public static final String MULTIMEDIA_PREVIEW_TYPE = "preview_type";
    public static final String MULTIMEDIA_PREVIEW_POSITION = "preview_position";

    private int previewType = MULTIMEDIA_PREVIEW_ALL;
    private int previewPosition;

    private ViewPager viewPager;
    private ViewPageAdapter pageAdapter;

    private List<MultimediaSelectedEntity> mDataSource = new ArrayList<>();

    private LinearLayout backBackground;
    private TextView positionTextView;
    private CheckBox checkedBox;
    private LinearLayout checkedBackground;
    private ImageView checkedImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multimedia_preview_activity);
        previewType = getIntent().getIntExtra(MULTIMEDIA_PREVIEW_TYPE,MULTIMEDIA_PREVIEW_ALL);
        previewPosition = getIntent().getIntExtra(MULTIMEDIA_PREVIEW_POSITION,0);
        initView();
    }

    /**
     *
     */
    private void initView(){
        viewPager = findViewById(R.id.multimedia_preview_activity_pager);
        backBackground = findViewById(R.id.multimedia_preview_activity_close);
        positionTextView = findViewById(R.id.multimedia_preview_activity_index);
        checkedBox = findViewById(R.id.multimedia_preview_activity_checked);
        checkedBackground = findViewById(R.id.multimedia_preview_activity_checked_background);
        checkedImageView = findViewById(R.id.multimedia_preview_activity_checked_background_iv);

        backBackground.setOnClickListener(this);

        if (previewType == MULTIMEDIA_PREVIEW_ALL){
            for (MultimediaSelectedFolderEntity folderEntity : dataSource) {
                if (folderEntity.isChecked() && folderEntity.getData() != null){
                    mDataSource.addAll(folderEntity.getData());
                    break;
                }
            }
        }

        pageAdapter = new ViewPageAdapter();
        viewPager.addOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(pageAdapter);
        positionTextView.setText("1/" + selectedDataSource.size());
        if (previewType == MULTIMEDIA_PREVIEW_ALL){
            viewPager.setCurrentItem(previewPosition);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        MultimediaSelectedEntity entity;
        if (previewType == MULTIMEDIA_PREVIEW_ALL){
            entity = mDataSource.get(viewPager.getCurrentItem());
            positionTextView.setText((viewPager.getCurrentItem()+1) + "/" + mDataSource.size());
        }else {
            entity = selectedDataSource.get(viewPager.getCurrentItem());
            positionTextView.setText((viewPager.getCurrentItem()+1) + "/" + selectedDataSource.size());
        }
        if (entity != null){
           /* checkedBox.setChecked(entity.isChecked());
            checkedBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                addItem(entity);
            });*/
           isChecked(entity.isChecked());
           checkedBackground.setOnClickListener(v -> {
               isChecked(addItem(entity));
           });
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     *
     * @param checked
     */
    private void isChecked(boolean checked){
        if (checked){
            checkedImageView.setImageResource(R.drawable.multimedia_selected_bottom_original_sel);
        }else {
            checkedImageView.setImageResource(R.drawable.multimedia_selected_bottom_original_un);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.multimedia_preview_activity_close) {
            finish();
        }
    }

    class ViewPageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (previewType == MULTIMEDIA_PREVIEW_ALL){
                return mDataSource.size();
            }else {
                return selectedDataSource.size();
            }
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return  view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
            final MultimediaSelectedEntity mediaEntity = getEntity(position);
            ImageView imageView = new ImageView(MultimediaPreviewActivity.this);
            GlideEngine.loader(MultimediaPreviewActivity.this,mediaEntity.getPath(),imageView);
            ((ViewPager) container).addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }

    /**
     *
     * @param position
     * @return
     */
    private MultimediaSelectedEntity getEntity(int position){
        if (previewType == MULTIMEDIA_PREVIEW_ALL){
            return mDataSource.get(position);
        }else {
            return selectedDataSource.get(position);
        }
    }
}
