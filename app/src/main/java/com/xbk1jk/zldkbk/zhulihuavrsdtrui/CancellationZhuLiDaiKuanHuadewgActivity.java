package com.xbk1jk.zldkbk.zhulihuavrsdtrui;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.xbk1jk.zldkbk.R;
import com.xbk1jk.zldkbk.zhulihuavrsdtrbase.BaseZhuLiDaiKuanHuadewgFgsActivity;
import com.xbk1jk.zldkbk.zhulihuavrsdtrutil.RemindZhuLiDaiKuanHuadewgDialog;
import com.xbk1jk.zldkbk.zhulihuavrsdtrutil.SharePreferencesZhuLiDaiKuanHuadewgUtil;
import com.xbk1jk.zldkbk.zhulihuavrsdtrutil.ZhuLiDaiKuanHuadewgStatusBarUtil;
import com.victor.loading.rotate.RotateLoading;

public class CancellationZhuLiDaiKuanHuadewgActivity extends BaseZhuLiDaiKuanHuadewgFgsActivity {

    private TextView titleTv, commitBtn;
    private View backImg, loadingFl;
    private RotateLoading rotateLoading;

    private RemindZhuLiDaiKuanHuadewgDialog mRemindZhuLiDaiKuanHuadewgDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_zhu_li_dai_kuan_hua_setg_sertcancellation;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            mRemindZhuLiDaiKuanHuadewgDialog = new RemindZhuLiDaiKuanHuadewgDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复", false);
            mRemindZhuLiDaiKuanHuadewgDialog.setBtnClickListener(new RemindZhuLiDaiKuanHuadewgDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindZhuLiDaiKuanHuadewgDialog.dismiss();
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
                    mRemindZhuLiDaiKuanHuadewgDialog.dismiss();
                }
            });
            mRemindZhuLiDaiKuanHuadewgDialog.show();
            mRemindZhuLiDaiKuanHuadewgDialog.setBtnStr("取消", "注销账号");
        });
    }

    @Override
    public void initData() {
        if (SharePreferencesZhuLiDaiKuanHuadewgUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        ZhuLiDaiKuanHuadewgStatusBarUtil.setTransparent(this, false);
        commitBtn = findViewById(R.id.commit_btn);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        loadingFl = findViewById(R.id.loading_fl);
        rotateLoading = findViewById(R.id.rotate_loading);
        titleTv.setText("注销账号");
    }
}
