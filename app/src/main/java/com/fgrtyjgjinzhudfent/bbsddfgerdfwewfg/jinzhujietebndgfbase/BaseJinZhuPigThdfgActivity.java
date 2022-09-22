package com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfbase;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfutil.ActivityJinZhuPigThdfgCollector;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseJinZhuPigThdfgActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityJinZhuPigThdfgCollector.addActivity(this);
        setContentView(getLayoutId());
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityJinZhuPigThdfgCollector.removeActivity(this);
    }

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initListener();
}
