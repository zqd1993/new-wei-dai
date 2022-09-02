package com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdtebase;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteutil.ActivityCollectorWeiFenQiadsfSsd;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseRongjieSfFgdfActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollectorWeiFenQiadsfSsd.addActivity(this);
        setContentView(getLayoutId());
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorWeiFenQiadsfSsd.removeActivity(this);
    }

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initListener();
}
