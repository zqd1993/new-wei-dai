package com.asvsdfer.bjirmndf.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.asvsdfer.bjirmndf.util.ActivityCollector;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(getLayoutId());
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    public abstract int getLayoutId();

    public abstract void initListener();

    public abstract void initData();
}
