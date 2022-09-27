package com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrbase;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrutil.ActivityRYDQHdhtTsdhfrCollector;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseRYDQHdhtTsdhfrActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRYDQHdhtTsdhfrCollector.addActivity(this);
        setContentView(getLayoutId());
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityRYDQHdhtTsdhfrCollector.removeActivity(this);
    }

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initListener();
}
