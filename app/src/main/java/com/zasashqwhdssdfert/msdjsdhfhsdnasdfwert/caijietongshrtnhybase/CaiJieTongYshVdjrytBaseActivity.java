package com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhybase;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyutil.ActivityCaiJieTongYshVdjrytCollector;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class CaiJieTongYshVdjrytBaseActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCaiJieTongYshVdjrytCollector.addActivity(this);
        setContentView(getLayoutId());
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCaiJieTongYshVdjrytCollector.removeActivity(this);
    }

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initListener();
}
