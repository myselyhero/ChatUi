package com.gy.wyy.chat.ui.home;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gy.wyy.chat.R;
import com.gy.wyy.chat.ui.view.ToolBarLayout;

/**
 *
 */
public class HomeDetailActivity extends AppCompatActivity {

    private ToolBarLayout barLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_detail_activity);
        barLayout = findViewById(R.id.home_detail_status);
        barLayout.setOnItemClickListener(function -> {
            finish();
        });
    }
}
