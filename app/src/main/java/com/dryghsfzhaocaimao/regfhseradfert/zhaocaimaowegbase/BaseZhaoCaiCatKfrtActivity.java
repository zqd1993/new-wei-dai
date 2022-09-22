package com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegbase;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegutil.ActivityZhaoCaiCatKfrtCollector;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseZhaoCaiCatKfrtActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityZhaoCaiCatKfrtCollector.addActivity(this);
        setContentView(getLayoutId());
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityZhaoCaiCatKfrtCollector.removeActivity(this);
    }

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initListener();
}
