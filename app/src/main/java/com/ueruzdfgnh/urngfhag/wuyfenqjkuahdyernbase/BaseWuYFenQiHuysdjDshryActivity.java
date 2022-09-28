package com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernbase;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernutil.ActivityWuYFenQiHuysdjDshryCollector;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseWuYFenQiHuysdjDshryActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWuYFenQiHuysdjDshryCollector.addActivity(this);
        setContentView(getLayoutId());
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityWuYFenQiHuysdjDshryCollector.removeActivity(this);
    }

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initListener();
}
