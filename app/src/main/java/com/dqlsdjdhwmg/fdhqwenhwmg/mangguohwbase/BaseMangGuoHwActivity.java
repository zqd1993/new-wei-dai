package com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwbase;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwutil.ActivityMangGuoHwCollector;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseMangGuoHwActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMangGuoHwCollector.addActivity(this);
        setContentView(getLayoutId());
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityMangGuoHwCollector.removeActivity(this);
    }

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initListener();
}
