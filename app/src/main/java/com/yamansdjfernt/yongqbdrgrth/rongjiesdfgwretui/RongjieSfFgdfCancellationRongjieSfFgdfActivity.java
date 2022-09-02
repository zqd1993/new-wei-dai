package com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretui;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yamansdjfernt.yongqbdrgrth.R;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretbase.BaseRongjieSfFgdfActivity;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretutil.RemindRongjieSfFgdfDialog;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretutil.SharePreferencesUtilRongjieSfFgdf;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretutil.RongjieSfFgdfStatusBarUtil;
import com.victor.loading.rotate.RotateLoading;

public class RongjieSfFgdfCancellationRongjieSfFgdfActivity extends BaseRongjieSfFgdfActivity {

    private TextView titleTv, commitBtn;
    private View backImg, loadingFl;
    private RotateLoading rotateLoading;

    private RemindRongjieSfFgdfDialog mRemindRongjieSfFgdfDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_rong_jie_sdf_brty_cancellation;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            mRemindRongjieSfFgdfDialog = new RemindRongjieSfFgdfDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复", false);
            mRemindRongjieSfFgdfDialog.setBtnClickListener(new RemindRongjieSfFgdfDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindRongjieSfFgdfDialog.dismiss();
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
                    mRemindRongjieSfFgdfDialog.dismiss();
                }
            });
            mRemindRongjieSfFgdfDialog.show();
            mRemindRongjieSfFgdfDialog.setBtnStr("取消", "注销账号");
        });
    }

    @Override
    public void initData() {
        if (SharePreferencesUtilRongjieSfFgdf.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        RongjieSfFgdfStatusBarUtil.setTransparent(this, false);
        commitBtn = findViewById(R.id.commit_btn);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        loadingFl = findViewById(R.id.loading_fl);
        rotateLoading = findViewById(R.id.rotate_loading);
        titleTv.setText("注销账号");
    }
}
