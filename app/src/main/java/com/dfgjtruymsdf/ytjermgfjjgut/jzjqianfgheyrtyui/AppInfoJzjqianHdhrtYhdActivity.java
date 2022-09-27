package com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyui;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.dfgjtruymsdf.ytjermgfjjgut.R;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtybase.BaseJzjqianHdhrtYhdActivity;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyutil.JzjqianHdhrtYhdSharePreferencesUtil;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyutil.StaticJzjqianHdhrtYhdUtil;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyutil.StatusJzjqianHdhrtYhdBarUtil;

public class AppInfoJzjqianHdhrtYhdActivity extends BaseJzjqianHdhrtYhdActivity {

    private TextView appVersionInfoTv, titleTv;
    private View backImg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_info_jzjqian_sdhr_utryn;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v ->{
            finish();
        });
    }

    @Override
    public void initData() {
        if (JzjqianHdhrtYhdSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        StatusJzjqianHdhrtYhdBarUtil.setTransparent(this, false);
        appVersionInfoTv = findViewById(R.id.app_version_info_tv);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        titleTv.setText("关于我们");
        appVersionInfoTv.setText("当前版本号：v" + StaticJzjqianHdhrtYhdUtil.getAppVersion(this));
    }
}
