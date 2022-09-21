package com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrui;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.ashasnasgasnzxdfge.asmasjasllldfrety.R;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrbase.YingJiHDSdJdgfsBaseActivity;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil.YingJiHDSdJdgfsRemindDialog;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil.YingJiHDSdJdgfsSharePreferencesUtil;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil.YingJiHDSdJdgfsStatusBarUtil;
import com.victor.loading.rotate.RotateLoading;

public class CancellationActivityYingJiHDSdJdgfs extends YingJiHDSdJdgfsBaseActivity {

    private TextView titleTv, commitBtn;
    private View backImg, loadingFl;
    private RotateLoading rotateLoading;

    private YingJiHDSdJdgfsRemindDialog mYingJiHDSdJdgfsRemindDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ying_ji_dh_jie_fuerty_cancellation;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            mYingJiHDSdJdgfsRemindDialog = new YingJiHDSdJdgfsRemindDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复", false);
            mYingJiHDSdJdgfsRemindDialog.setBtnClickListener(new YingJiHDSdJdgfsRemindDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mYingJiHDSdJdgfsRemindDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    rotateLoading.start();
                    loadingFl.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadingFl.setVisibility(View.GONE);
                            rotateLoading.stop();
                            finish();
                        }
                    }, 2000);
                    mYingJiHDSdJdgfsRemindDialog.dismiss();
                }
            });
            mYingJiHDSdJdgfsRemindDialog.show();
            mYingJiHDSdJdgfsRemindDialog.setBtnStr("取消", "注销账号");
        });
    }

    @Override
    public void initData() {
        if (YingJiHDSdJdgfsSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        YingJiHDSdJdgfsStatusBarUtil.setTransparent(this, false);
        commitBtn = findViewById(R.id.commit_btn);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        loadingFl = findViewById(R.id.loading_fl);
        rotateLoading = findViewById(R.id.rotate_loading);
        titleTv.setText("注销账号");
    }
}
