package com.zhaocaimaowerdfgf.nheradfert.zhaocaimaowegui;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.zhaocaimaowerdfgf.nheradfert.R;
import com.zhaocaimaowerdfgf.nheradfert.zhaocaimaowegbase.BaseZhaoCaiCatKfrtActivity;
import com.zhaocaimaowerdfgf.nheradfert.zhaocaimaowegutil.RemindZhaoCaiCatKfrtDialog;
import com.zhaocaimaowerdfgf.nheradfert.zhaocaimaowegutil.ZhaoCaiCatKfrtSharePreferencesUtil;
import com.zhaocaimaowerdfgf.nheradfert.zhaocaimaowegutil.ZhaoCaiCatKfrtStatusBarUtil;
import com.victor.loading.rotate.RotateLoading;

public class ZhaoCaiCatKfrtCancellationZhaoCaiCatKfrtActivity extends BaseZhaoCaiCatKfrtActivity {

    private TextView titleTv, commitBtn;
    private View backImg, loadingFl;
    private RotateLoading rotateLoading;

    private RemindZhaoCaiCatKfrtDialog mRemindZhaoCaiCatKfrtDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cancellation_zhao_cai_mao_dfget;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            mRemindZhaoCaiCatKfrtDialog = new RemindZhaoCaiCatKfrtDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复", false);
            mRemindZhaoCaiCatKfrtDialog.setBtnClickListener(new RemindZhaoCaiCatKfrtDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindZhaoCaiCatKfrtDialog.dismiss();
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
                    mRemindZhaoCaiCatKfrtDialog.dismiss();
                }
            });
            mRemindZhaoCaiCatKfrtDialog.show();
            mRemindZhaoCaiCatKfrtDialog.setBtnStr("取消", "注销账号");
        });
    }

    @Override
    public void initData() {
        if (ZhaoCaiCatKfrtSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        ZhaoCaiCatKfrtStatusBarUtil.setTransparent(this, false);
        commitBtn = findViewById(R.id.commit_btn);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        loadingFl = findViewById(R.id.loading_fl);
        rotateLoading = findViewById(R.id.rotate_loading);
        titleTv.setText("注销账号");
    }
}
