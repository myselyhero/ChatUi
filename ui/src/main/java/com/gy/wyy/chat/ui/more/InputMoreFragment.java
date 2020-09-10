package com.gy.wyy.chat.ui.more;

import android.app.Fragment;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.gy.wyy.chat.ui.R;
import com.gy.wyy.chat.ui.UiKit;
import com.gy.wyy.chat.ui.face.Emoji;
import com.gy.wyy.chat.ui.face.EmojiIndicatorView;
import com.gy.wyy.chat.ui.face.FaceFragment;
import com.gy.wyy.chat.ui.tool.ChatUiLog;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class InputMoreFragment extends Fragment {

    private View view;

    ArrayList<View> ViewPagerItems = new ArrayList<>();
    private ViewPager viewPager;
    private EmojiIndicatorView emojiIndicatorView;
    private int columnsDefault = 4;
    private int rowsDefault = 2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.input_more_fragment,container,false);
        initView();
        return view;
    }

    /**
     *
     */
    private void initView(){
        viewPager = view.findViewById(R.id.input_more_view_pager);
        emojiIndicatorView = view.findViewById(R.id.input_more_view_index);
        List<InputMoreEntity> list = new ArrayList<>();

        list.add(new InputMoreEntity(R.drawable.ic_message_input_more_camera,getString(R.string.message_input_more_camera)));
        list.add(new InputMoreEntity(R.drawable.ic_message_input_more_photo,getString(R.string.message_input_more_photo)));
        list.add(new InputMoreEntity(R.drawable.ic_message_input_more_audio_call,getString(R.string.message_input_more_audio_call)));
        list.add(new InputMoreEntity(R.drawable.ic_message_input_more_video_call,getString(R.string.message_input_more_video_call)));
        list.add(new InputMoreEntity(R.drawable.ic_message_input_more_package,getString(R.string.message_input_more_package)));
        list.add(new InputMoreEntity(R.drawable.ic_message_input_more_transfer,getString(R.string.message_input_more_transfer)));
        list.add(new InputMoreEntity(R.drawable.ic_message_input_more_location,getString(R.string.message_input_more_location)));
        list.add(new InputMoreEntity(R.drawable.ic_message_input_more_collect,getString(R.string.message_input_more_collect)));
        list.add(new InputMoreEntity(R.drawable.ic_message_input_more_file,getString(R.string.message_input_more_file)));

        int pageCont = getPagerCount(list);
        emojiIndicatorView.init(pageCont);
        ViewPagerItems.clear();
        for (int i = 0; i < pageCont; i++) {
            ViewPagerItems.add(getViewPagerItem(i, list));
        }
        MoreViewPagerAdapter mVpAdapter = new MoreViewPagerAdapter(ViewPagerItems);
        viewPager.setAdapter(mVpAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int oldPosition = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                emojiIndicatorView.playBy(oldPosition, position);
                oldPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 根据表情数量以及GridView设置的行数和列数计算Pager数量
     * @param list
     * @return
     */
    private int getPagerCount(List<InputMoreEntity> list) {
        int count = list.size();
        int dit = 1;
        return count % (columnsDefault * 2 - dit) == 0 ? count / (columnsDefault * rowsDefault - dit)
                : count / (columnsDefault * rowsDefault - dit) + 1;
    }

    /**
     *
     * @param position
     * @param list
     * @return
     */
    private View getViewPagerItem(int position, List<InputMoreEntity> list) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.input_more_grid, null);//表情布局
        GridView gridview = layout.findViewById(R.id.input_more_grid);

        final List<InputMoreEntity> subList = new ArrayList<>();
        //int dit = 1;
        subList.addAll(list.subList(position * (columnsDefault * rowsDefault),//- dit
                (columnsDefault * rowsDefault) * (position + 1) > list
                        .size() ? list.size() : (columnsDefault * rowsDefault) * (position + 1)));

        InputMoreAdapter mGvAdapter = new InputMoreAdapter(UiKit.getAppContext());
        mGvAdapter.setDataSource(subList);
        gridview.setAdapter(mGvAdapter);
        // 单击表情执行的操作
        gridview.setOnItemClickListener((parent, v, position1, id) -> {

        });

        return gridview;
    }

    /**
     *
     */
    class MoreViewPagerAdapter extends PagerAdapter {
        // 界面列表
        private List<View> views;

        public MoreViewPagerAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) (arg2));
        }

        @Override
        public int getCount() {
            return views.size();
        }

        // 初始化arg1位置的界面
        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(views.get(arg1));
            return views.get(arg1);
        }

        // 判断是否由对象生成界
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return (arg0 == arg1);
        }
    }
}
