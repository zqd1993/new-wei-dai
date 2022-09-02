package com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdui;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yhbnwghjfdkdfet.tgvshdjg.R;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdbase.BaseMiaoBaiTiaoAdfFgsActivity;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdutil.RemindMiaoBaiTiaoAdfFgsDialog;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdutil.SharePreferencesMiaoBaiTiaoAdfFgsUtil;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdutil.MiaoBaiTiaoAdfFgsStatusBarUtil;
import com.victor.loading.rotate.RotateLoading;

public class MiaoBaiTiaoAdfFgsCancellationMiaoBaiTiaoAdfFgsActivity extends BaseMiaoBaiTiaoAdfFgsActivity {

    private TextView titleTv, commitBtn;
    private View backImg, loadingFl;
    private RotateLoading rotateLoading;

    private RemindMiaoBaiTiaoAdfFgsDialog mRemindMiaoBaiTiaoAdfFgsDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_miao_bai_tiao_sdf_cancellation;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            mRemindMiaoBaiTiaoAdfFgsDialog = new RemindMiaoBaiTiaoAdfFgsDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复", false);
            mRemindMiaoBaiTiaoAdfFgsDialog.setBtnClickListener(new RemindMiaoBaiTiaoAdfFgsDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindMiaoBaiTiaoAdfFgsDialog.dismiss();
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
                    mRemindMiaoBaiTiaoAdfFgsDialog.dismiss();
                }
            });
            mRemindMiaoBaiTiaoAdfFgsDialog.show();
            mRemindMiaoBaiTiaoAdfFgsDialog.setBtnStr("取消", "注销账号");
        });
    }

    @Override
    public void initData() {
        if (SharePreferencesMiaoBaiTiaoAdfFgsUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        MiaoBaiTiaoAdfFgsStatusBarUtil.setTransparent(this, false);
        commitBtn = findViewById(R.id.commit_btn);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        loadingFl = findViewById(R.id.loading_fl);
        rotateLoading = findViewById(R.id.rotate_loading);
        titleTv.setText("注销账号");
    }
}
