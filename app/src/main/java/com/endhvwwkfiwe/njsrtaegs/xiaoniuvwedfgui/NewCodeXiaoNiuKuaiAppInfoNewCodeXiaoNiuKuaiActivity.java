package com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgui;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.endhvwwkfiwe.njsrtaegs.R;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgbase.BaseNewCodeXiaoNiuKuaiActivity;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgutil.NewCodeXiaoNiuKuaiSharePreferencesUtil;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgutil.StaticNewCodeXiaoNiuKuaiUtil;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgutil.NewCodeXiaoNiuKuaiStatusBarUtil;

public class NewCodeXiaoNiuKuaiAppInfoNewCodeXiaoNiuKuaiActivity extends BaseNewCodeXiaoNiuKuaiActivity {

    private TextView appVersionInfoTv, titleTv;
    private View backImg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_info_xiao_niu_kuai_sdf_efbdgh;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v ->{
            finish();
        });
    }

    @Override
    public void initData() {
        if (NewCodeXiaoNiuKuaiSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        NewCodeXiaoNiuKuaiStatusBarUtil.setTransparent(this, false);
        appVersionInfoTv = findViewById(R.id.app_version_info_tv);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        titleTv.setText("关于我们");
        appVersionInfoTv.setText("当前版本号：v" + StaticNewCodeXiaoNiuKuaiUtil.getAppVersion(this));
    }
}
