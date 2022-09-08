package com.qingsongvyrnng.mrjgndsdg.qingsojdkvbase;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.qingsongvyrnng.mrjgndsdg.qingsojdkvutil.ActivityBaseQingSongShfjAFduCollector;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseBaseQingSongShfjAFduActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBaseQingSongShfjAFduCollector.addActivity(this);
        setContentView(getLayoutId());
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityBaseQingSongShfjAFduCollector.removeActivity(this);
    }

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initListener();
}
