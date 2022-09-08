package com.dfjsdndsuweq.sfdkdfmsz.taofenqiefbase;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefutil.ActivityBaseTaoFenQiDfREvfCollector;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseBaseTaoFenQiDfREvfActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBaseTaoFenQiDfREvfCollector.addActivity(this);
        setContentView(getLayoutId());
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityBaseTaoFenQiDfREvfCollector.removeActivity(this);
    }

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initListener();
}
