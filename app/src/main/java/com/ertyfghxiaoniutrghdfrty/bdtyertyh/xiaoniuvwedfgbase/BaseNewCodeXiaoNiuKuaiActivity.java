package com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgbase;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgutil.ActivityNewCodeXiaoNiuKuaiCollector;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseNewCodeXiaoNiuKuaiActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNewCodeXiaoNiuKuaiCollector.addActivity(this);
        setContentView(getLayoutId());
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityNewCodeXiaoNiuKuaiCollector.removeActivity(this);
    }

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initListener();
}
