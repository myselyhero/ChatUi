package com.gy.wyy.chat.ui.home;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gy.wyy.chat.R;
import com.gy.wyy.chat.ui.home.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeAdapter.OnHomeAdapterListener {

    private View rootView;
    private HomeViewModel homeViewModel;

    private List<String> dataSource = new ArrayList<>();
    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);//获取viewModel并于此fragment绑定
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        return rootView;
    }

    /**
     *
     */
    private void initView(){
        recyclerView = rootView.findViewById(R.id.home_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 10;
            }
        });

        homeAdapter = new HomeAdapter(getContext(),dataSource,this);
        recyclerView.setAdapter(homeAdapter);

        dataSource.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=111713540,615806613&fm=26&gp=0.jpg");
        dataSource.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1589746494233&di=7d69285ec30ee675446b0a9ac1e06e62&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201401%2F11%2F145825zn1sxa8anrg11gt1.jpg");
        dataSource.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1589746494229&di=44c11b56415df7d72c43719ee8fee245&imgtype=0&src=http%3A%2F%2Fattachments.gfan.com%2Fforum%2F201702%2F24%2F082111tt8ih6o4hiib657c.jpg");
        dataSource.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1589746494264&di=f270709cd06289c92f81fbf9b4913a6d&imgtype=0&src=http%3A%2F%2F2b.zol-img.com.cn%2Fproduct%2F61_940x705%2F83%2Fce9MEqItt60go.jpg");
        dataSource.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1589746494264&di=33536bbfa36b2baab3d26184b31de434&imgtype=0&src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201401%2F17%2F20140117221137_asMSK.jpeg");
        dataSource.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1589746624786&di=b6144477987d91041d2dc4780f99894e&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201408%2F06%2F123308ehtn3tyzt0thh1r9.jpg");
        dataSource.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1589746710462&di=9141a4d02f924b5e7a82bc34baad5c7b&imgtype=0&src=http%3A%2F%2Fattachments.gfan.com%2Fforum%2F201503%2F20%2F2222226y6c4iij6io2ku66.jpg");
        dataSource.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1589746866882&di=2ec357bec586096c1ca56356cd9ad1ae&imgtype=0&src=http%3A%2F%2Fimg.qqzhi.com%2Fuploads%2F2018-12-11%2F234321839.jpg");
        dataSource.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1589746894117&di=8431816d8cc09a878730aacc21345b16&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201502%2F01%2F20150201224516_diJYt.jpeg");
        homeAdapter.notifyDataSetChanged();

        /*TextView text_home = rootView.findViewById(R.id.text_home);
        homeViewModel.getLiveData().observe(getViewLifecycleOwner(), s -> {
            text_home.setText(s);
        });*/
    }

    @Override
    public void onListener(int position, String data) {
        startActivity(new Intent(getActivity(),HomeDetailActivity.class));
    }
}