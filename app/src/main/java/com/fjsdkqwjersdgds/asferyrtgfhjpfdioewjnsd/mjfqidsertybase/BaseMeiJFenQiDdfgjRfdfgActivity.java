package com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertybase;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyutil.ActivityMeiJFenQiDdfgjRfdfgCollector;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseMeiJFenQiDdfgjRfdfgActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMeiJFenQiDdfgjRfdfgCollector.addActivity(this);
        setContentView(getLayoutId());
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityMeiJFenQiDdfgjRfdfgCollector.removeActivity(this);
    }

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initListener();
}
