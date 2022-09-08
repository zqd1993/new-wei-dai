package com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvbase;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvutil.ActivityBaseQuHuaDjdfuVdhrCollector;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseBaseQuHuaDjdfuVdhrActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBaseQuHuaDjdfuVdhrCollector.addActivity(this);
        setContentView(getLayoutId());
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityBaseQuHuaDjdfuVdhrCollector.removeActivity(this);
    }

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initListener();
}
