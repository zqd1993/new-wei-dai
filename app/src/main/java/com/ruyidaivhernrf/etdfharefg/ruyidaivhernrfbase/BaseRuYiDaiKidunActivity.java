package com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfbase;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfutil.ActivityRuYiDaiKidunCollector;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseRuYiDaiKidunActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRuYiDaiKidunCollector.addActivity(this);
        setContentView(getLayoutId());
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityRuYiDaiKidunCollector.removeActivity(this);
    }

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initListener();
}
