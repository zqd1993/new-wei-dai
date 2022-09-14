package com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsui;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.R;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsbase.BaseWuYouJieShdfnFjfuirActivity;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsutil.WuYouJieShdfnFjfuirSharePreferencesUtil;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsutil.StaticWuYouJieShdfnFjfuirUtil;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsutil.WuYouJieShdfnFjfuirStatusBarUtil;

public class AppInfoWuYouJieShdfnFjfuirActivity extends BaseWuYouJieShdfnFjfuirActivity {

    private TextView appVersionInfoTv, titleTv;
    private View backImg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_info_wu_you_jie_jdf_eryj;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v ->{
            finish();
        });
    }

    @Override
    public void initData() {
        if (WuYouJieShdfnFjfuirSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        WuYouJieShdfnFjfuirStatusBarUtil.setTransparent(this, false);
        appVersionInfoTv = findViewById(R.id.app_version_info_tv);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        titleTv.setText("关于我们");
        appVersionInfoTv.setText("当前版本号：v" + StaticWuYouJieShdfnFjfuirUtil.getAppVersion(this));
    }
}
