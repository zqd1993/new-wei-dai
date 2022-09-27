package com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuui;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.tyfdgeruyfgh.ernhfygwsert.R;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyubase.BaseZCMdhdTshdfActivity;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuutil.RemindZCMdhdTshdfDialog;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuutil.ZCMdhdTshdfSharePreferencesUtil;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuutil.ZCMdhdTshdfStatusBarUtil;
import com.victor.loading.rotate.RotateLoading;

public class CancellationZCMdhdTshdfActivity extends BaseZCMdhdTshdfActivity {

    private TextView titleTv, commitBtn;
    private View backImg, loadingFl;
    private RotateLoading rotateLoading;

    private RemindZCMdhdTshdfDialog mRemindZCMdhdTshdfDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cancellation_zcm_fhgetr_tqttry;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            mRemindZCMdhdTshdfDialog = new RemindZCMdhdTshdfDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复", false);
            mRemindZCMdhdTshdfDialog.setBtnClickListener(new RemindZCMdhdTshdfDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindZCMdhdTshdfDialog.dismiss();
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
                    mRemindZCMdhdTshdfDialog.dismiss();
                }
            });
            mRemindZCMdhdTshdfDialog.show();
            mRemindZCMdhdTshdfDialog.setBtnStr("取消", "注销账号");
        });
    }

    @Override
    public void initData() {
        if (ZCMdhdTshdfSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        ZCMdhdTshdfStatusBarUtil.setTransparent(this, false);
        commitBtn = findViewById(R.id.commit_btn);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        loadingFl = findViewById(R.id.loading_fl);
        rotateLoading = findViewById(R.id.rotate_loading);
        titleTv.setText("注销账号");
    }
}
