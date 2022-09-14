package com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsbase;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsutil.ActivityWuYouJieShdfnFjfuirCollector;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseWuYouJieShdfnFjfuirActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWuYouJieShdfnFjfuirCollector.addActivity(this);
        setContentView(getLayoutId());
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityWuYouJieShdfnFjfuirCollector.removeActivity(this);
    }

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initListener();
}
