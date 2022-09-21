package com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrbase;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil.ActivityYingJiHDSdJdgfsCollector;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class YingJiHDSdJdgfsBaseActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityYingJiHDSdJdgfsCollector.addActivity(this);
        setContentView(getLayoutId());
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityYingJiHDSdJdgfsCollector.removeActivity(this);
    }

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initListener();
}
