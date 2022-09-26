package com.fjsdkqwj.pfdioewjnsd.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.fjsdkqwj.pfdioewjnsd.R;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

public class HomePageActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);
    }
}
