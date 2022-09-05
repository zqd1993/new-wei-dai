package com.youjiegherh.pocketqwrh.youjiewetdfhui;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.youjiegherh.pocketqwrh.R;
import com.youjiegherh.pocketqwrh.youjiewetdfhbase.BaseNewCodeXiaoNiuKuaiActivity;
import com.youjiegherh.pocketqwrh.youjiewetdfhutil.YouJieSDjdfiSharePreferencesUtil;
import com.youjiegherh.pocketqwrh.youjiewetdfhutil.StaticYouJieSDjdfiUtil;
import com.youjiegherh.pocketqwrh.youjiewetdfhutil.YouJieSDjdfiStatusBarUtil;

public class YouJieSDjdfiAppInfoActivity extends BaseNewCodeXiaoNiuKuaiActivity {

    private TextView appVersionInfoTv, titleTv;
    private View backImg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_info_you_jie_iejbvr;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v ->{
            finish();
        });
    }

    @Override
    public void initData() {
        if (YouJieSDjdfiSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        YouJieSDjdfiStatusBarUtil.setTransparent(this, false);
        appVersionInfoTv = findViewById(R.id.app_version_info_tv);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        titleTv.setText("关于我们");
        appVersionInfoTv.setText("当前版本号：v" + StaticYouJieSDjdfiUtil.getAppVersion(this));
    }
}
