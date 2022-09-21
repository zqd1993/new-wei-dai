package com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertbase;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertutil.ActivityJiuJiHdfnfhGDhsdCollector;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseJiuJiHdfnfhGDhsdActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityJiuJiHdfnfhGDhsdCollector.addActivity(this);
        setContentView(getLayoutId());
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityJiuJiHdfnfhGDhsdCollector.removeActivity(this);
    }

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initListener();
}
