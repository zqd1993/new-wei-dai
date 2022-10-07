package com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgui;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.dqlsdjddfser.fdhqwendsfe.R;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgbase.BaseTaoJieHauHsyaJhsdyActivity;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgutil.SharePreferencesUtilTaoJieHauHsyaJhsdy;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgutil.StaticTaoJieHauHsyaJhsdyUtil;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgutil.TaoJieHauHsyaJhsdyStatusBarUtil;

public class TaoJieHauHsyaJhsdyAppInfoActivity extends BaseTaoJieHauHsyaJhsdyActivity {

    private TextView appVersionInfoTv, titleTv;
    private View backImg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_info_tao_jie_hua_djheru_fhentj;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v ->{
            finish();
        });
    }

    @Override
    public void initData() {
        if (SharePreferencesUtilTaoJieHauHsyaJhsdy.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        TaoJieHauHsyaJhsdyStatusBarUtil.setTransparent(this, false);
        appVersionInfoTv = findViewById(R.id.app_version_info_tv);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        titleTv.setText("关于我们");
        appVersionInfoTv.setText("当前版本号：v" + StaticTaoJieHauHsyaJhsdyUtil.getAppVersion(this));
    }
}
