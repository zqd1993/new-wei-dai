package com.youjiegherh.pocketqwrh.youjiewetdfhbase;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.youjiegherh.pocketqwrh.youjiewetdfhutil.ActivityYouJieSDjdfiCollector;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseNewCodeXiaoNiuKuaiActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityYouJieSDjdfiCollector.addActivity(this);
        setContentView(getLayoutId());
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityYouJieSDjdfiCollector.removeActivity(this);
    }

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initListener();
}
