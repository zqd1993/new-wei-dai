package com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyui;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.R;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhybase.CaiJieTongYshVdjrytBaseActivity;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyutil.CaiJieTongYshVdjrytRemindDialog;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyutil.CaiJieTongYshVdjrytSharePreferencesUtil;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyutil.CaiJieTongYshVdjrytStatusBarUtil;
import com.victor.loading.rotate.RotateLoading;

public class CancellationActivityCaiJieTongYshVdjryt extends CaiJieTongYshVdjrytBaseActivity {

    private TextView titleTv, commitBtn;
    private View backImg, loadingFl;
    private RotateLoading rotateLoading;

    private CaiJieTongYshVdjrytRemindDialog mCaiJieTongYshVdjrytRemindDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cai_jie_tong_drt_etfnh_cancellation;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            mCaiJieTongYshVdjrytRemindDialog = new CaiJieTongYshVdjrytRemindDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复", false);
            mCaiJieTongYshVdjrytRemindDialog.setBtnClickListener(new CaiJieTongYshVdjrytRemindDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mCaiJieTongYshVdjrytRemindDialog.dismiss();
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
                    mCaiJieTongYshVdjrytRemindDialog.dismiss();
                }
            });
            mCaiJieTongYshVdjrytRemindDialog.show();
            mCaiJieTongYshVdjrytRemindDialog.setBtnStr("取消", "注销账号");
        });
    }

    @Override
    public void initData() {
        if (CaiJieTongYshVdjrytSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        CaiJieTongYshVdjrytStatusBarUtil.setTransparent(this, false);
        commitBtn = findViewById(R.id.commit_btn);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        loadingFl = findViewById(R.id.loading_fl);
        rotateLoading = findViewById(R.id.rotate_loading);
        titleTv.setText("注销账号");
    }
}
