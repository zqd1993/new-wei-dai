package com.xbk1jk.zldkbk.zhulihuavrsdtrbase;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.xbk1jk.zldkbk.zhulihuavrsdtrutil.ActivityZhuLiDaiKuanHuadewgCollector;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseZhuLiDaiKuanHuadewgFgsActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityZhuLiDaiKuanHuadewgCollector.addActivity(this);
        setContentView(getLayoutId());
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityZhuLiDaiKuanHuadewgCollector.removeActivity(this);
    }

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initListener();
}
