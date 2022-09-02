package com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdbase;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdutil.ActivityMiaoBaiTiaoAdfFgsCollector;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseMiaoBaiTiaoAdfFgsActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMiaoBaiTiaoAdfFgsCollector.addActivity(this);
        setContentView(getLayoutId());
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityMiaoBaiTiaoAdfFgsCollector.removeActivity(this);
    }

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initListener();
}
