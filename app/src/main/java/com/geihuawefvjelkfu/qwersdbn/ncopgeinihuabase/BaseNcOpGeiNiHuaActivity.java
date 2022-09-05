package com.geihuawefvjelkfu.qwersdbn.ncopgeinihuabase;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuautil.ActivityNcOpGeiNiHuaCollector;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseNcOpGeiNiHuaActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNcOpGeiNiHuaCollector.addActivity(this);
        setContentView(getLayoutId());
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityNcOpGeiNiHuaCollector.removeActivity(this);
    }

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initListener();
}
