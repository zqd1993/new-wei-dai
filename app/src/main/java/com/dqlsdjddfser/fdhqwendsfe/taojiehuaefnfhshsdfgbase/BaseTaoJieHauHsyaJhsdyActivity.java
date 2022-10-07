package com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgbase;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgutil.ActivityCollectorTaoJieHauHsyaJhsdy;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseTaoJieHauHsyaJhsdyActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollectorTaoJieHauHsyaJhsdy.addActivity(this);
        setContentView(getLayoutId());
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorTaoJieHauHsyaJhsdy.removeActivity(this);
    }

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initListener();
}
