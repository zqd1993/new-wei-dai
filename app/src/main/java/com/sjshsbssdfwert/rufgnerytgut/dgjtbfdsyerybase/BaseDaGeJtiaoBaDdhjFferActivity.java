package com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyerybase;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryutil.ActivityDaGeJtiaoBaDdhjFferCollector;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseDaGeJtiaoBaDdhjFferActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDaGeJtiaoBaDdhjFferCollector.addActivity(this);
        setContentView(getLayoutId());
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityDaGeJtiaoBaDdhjFferCollector.removeActivity(this);
    }

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initListener();
}
