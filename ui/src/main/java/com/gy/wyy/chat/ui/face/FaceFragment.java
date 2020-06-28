package com.gy.wyy.chat.ui.face;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.gy.wyy.chat.ui.R;
import com.gy.wyy.chat.ui.tool.ScreenUtil;
import com.gy.wyy.chat.ui.tool.SoftKeyBoardUtil;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class FaceFragment extends Fragment {

    private ArrayList<Emoji> emojiList;
    private OnEmojiClickListener listener;

    ArrayList<View> ViewPagerItems = new ArrayList<>();
    private ViewPager viewPager;
    private EmojiIndicatorView emojiIndicatorView;
    private int columns = 7;
    private int rows = 3;
    //private int vMargin = 0;

    public void setListener(OnEmojiClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onAttach(Activity activity) {
        if (activity instanceof OnEmojiClickListener) {
            this.listener = (OnEmojiClickListener) activity;
        }
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        emojiList = FaceManager.getEmojiList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.input_face_fragment, container, false);
        /*ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = FaceUtil.getSoftKeyBoardHeight();
        view.setLayoutParams(params);*/
        viewPager = view.findViewById(R.id.input_face_fragment_view_pager);
        emojiIndicatorView = view.findViewById(R.id.input_face_fragment_index);
        initViewPager();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     *
     */
    private void initViewPager() {
        if (emojiList == null){
            return;
        }
        /*if (emojiList.size() > 0) {
            vMargin = (SoftKeyBoardUtil.getSoftKeyBoardHeight() - (ScreenUtil.getPxByDp(40 + 20) + emojiList.get(0).getHeight() * rows)) / 5;
        }*/

        emojiIndicatorView.init(getPagerCount(emojiList));
        ViewPagerItems.clear();
        int pageCont = getPagerCount(emojiList);
        for (int i = 0; i < pageCont; i++) {
            ViewPagerItems.add(getViewPagerItem(i, emojiList));
        }
        FaceVPAdapter mVpAdapter = new FaceVPAdapter(ViewPagerItems);
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
     *
     * @return
     */
    private int getPagerCount(ArrayList<Emoji> list) {
        int count = list.size();
        int dit = 1;
        return count % (columns * rows - dit) == 0 ? count / (columns * rows - dit)
                : count / (columns * rows - dit) + 1;
    }

    private View getViewPagerItem(int position, ArrayList<Emoji> list) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.input_face_grid, null);//表情布局
        GridView gridview = layout.findViewById(R.id.chart_face_gv);
        /**
         * 注：因为每一页末尾都有一个删除图标，所以每一页的实际表情columns *　rows　－　1; 空出最后一个位置给删除图标
         * */
        final List<Emoji> subList = new ArrayList<>();
        int dit = 1;;
        subList.addAll(list.subList(position * (columns * rows - dit),
                (columns * rows - dit) * (position + 1) > list
                        .size() ? list.size() : (columns
                        * rows - dit)
                        * (position + 1)));

        /**
         * 末尾添加删除图标
         * */
        if (subList.size() < (columns * rows - dit)) {
            for (int i = subList.size(); i < (columns * rows - dit); i++) {
                subList.add(null);
            }
        }

        Emoji deleteEmoji = new Emoji();
        deleteEmoji.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.input_face_delete));
        subList.add(deleteEmoji);


        FaceGVAdapter mGvAdapter = new FaceGVAdapter(subList, getActivity());
        gridview.setAdapter(mGvAdapter);
        gridview.setNumColumns(columns);
        // 单击表情执行的操作
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == columns * rows - 1) {
                    if (listener != null) {
                        listener.onEmojiDelete();
                    }
                    return;
                }
                if (listener != null) {
                    listener.onEmojiClick(subList.get(position));
                }
            }
        });

        return gridview;
    }

    /**
     *
     */
    public interface OnEmojiClickListener {

        void onEmojiDelete();

        void onEmojiClick(Emoji emoji);
    }

    /**
     * viewpager适配器
     */
    class FaceVPAdapter extends PagerAdapter {
        // 界面列表
        private List<View> views;

        public FaceVPAdapter(List<View> views) {
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

    /**
     * 表情适配器
     */
    class FaceGVAdapter extends BaseAdapter {
        private List<Emoji> list;
        private Context mContext;

        public FaceGVAdapter(List<Emoji> list, Context mContext) {
            super();
            this.list = list;
            this.mContext = mContext;
        }


        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            Emoji emoji = list.get(position);
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.input_face_grid_item, null);
                holder.iv = convertView.findViewById(R.id.face_image);
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) holder.iv.getLayoutParams();
                if (emoji != null) {
                    params.width = emoji.getWidth();
                    params.height = emoji.getHeight();
                }
                /*if (position / columns == 0) {
                    params.setMargins(0, vMargin, 0, 0);
                } else if (rows == 2) {
                    params.setMargins(0, vMargin, 0, 0);
                } else {
                    if (position / columns < rows - 1) {
                        params.setMargins(0, vMargin, 0, vMargin);
                    } else {
                        params.setMargins(0, 0, 0, vMargin);
                    }
                }*/

                holder.iv.setLayoutParams(params);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (emoji != null) {
                holder.iv.setImageBitmap(emoji.getIcon());
            }
            return convertView;
        }

        class ViewHolder {
            ImageView iv;
        }
    }
}
