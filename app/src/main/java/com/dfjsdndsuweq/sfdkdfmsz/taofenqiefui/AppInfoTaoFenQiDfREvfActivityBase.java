package com.dfjsdndsuweq.sfdkdfmsz.taofenqiefui;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.dfjsdndsuweq.sfdkdfmsz.R;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefbase.BaseBaseTaoFenQiDfREvfActivity;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefutil.BaseTaoFenQiDfREvfSharePreferencesUtil;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefutil.StaticBaseTaoFenQiDfREvfUtil;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefutil.BaseTaoFenQiDfREvfStatusBarUtil;

public class AppInfoTaoFenQiDfREvfActivityBase extends BaseBaseTaoFenQiDfREvfActivity {

    private TextView appVersionInfoTv, titleTv;
    private View backImg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_info_tao_fen_qi_rtgr_vbd;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v ->{
            finish();
        });
    }

    @Override
    public void initData() {
        if (BaseTaoFenQiDfREvfSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        BaseTaoFenQiDfREvfStatusBarUtil.setTransparent(this, false);
        appVersionInfoTv = findViewById(R.id.app_version_info_tv);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        titleTv.setText("关于我们");
        appVersionInfoTv.setText("当前版本号：v" + StaticBaseTaoFenQiDfREvfUtil.getAppVersion(this));
    }
}
