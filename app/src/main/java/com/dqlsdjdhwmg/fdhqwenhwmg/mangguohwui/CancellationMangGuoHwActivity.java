package com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwui;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.dqlsdjdhwmg.fdhqwenhwmg.R;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwbase.BaseMangGuoHwActivity;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwutil.RemindMangGuoHwDialog;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwutil.MangGuoHwSharePreferencesUtils;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwutil.StatusBarUtilMangGuoHw;
import com.victor.loading.rotate.RotateLoading;

public class CancellationMangGuoHwActivity extends BaseMangGuoHwActivity {

    private TextView titleTv, commitBtn;
    private View backImg, loadingFl;
    private RotateLoading rotateLoading;

    private RemindMangGuoHwDialog mRemindMangGuoHwDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mang_guo_hw_cancellation;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            mRemindMangGuoHwDialog = new RemindMangGuoHwDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复", false);
            mRemindMangGuoHwDialog.setBtnClickListener(new RemindMangGuoHwDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindMangGuoHwDialog.dismiss();
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
                    mRemindMangGuoHwDialog.dismiss();
                }
            });
            mRemindMangGuoHwDialog.show();
            mRemindMangGuoHwDialog.setBtnStr("取消", "注销账号");
        });
    }

    @Override
    public void initData() {
        if (MangGuoHwSharePreferencesUtils.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        StatusBarUtilMangGuoHw.setTransparent(this, false);
        commitBtn = findViewById(R.id.commit_btn);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        loadingFl = findViewById(R.id.loading_fl);
        rotateLoading = findViewById(R.id.rotate_loading);
        titleTv.setText("注销账号");
    }
}
