package com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretbase;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretutil.ActivityCollectorRongjieSfFgdf;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseRongjieSfFgdfActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollectorRongjieSfFgdf.addActivity(this);
        setContentView(getLayoutId());
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorRongjieSfFgdf.removeActivity(this);
    }

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initListener();
}