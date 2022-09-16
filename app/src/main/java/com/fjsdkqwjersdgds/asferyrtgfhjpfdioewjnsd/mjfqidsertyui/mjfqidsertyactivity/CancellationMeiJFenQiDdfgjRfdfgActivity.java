package com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyui.mjfqidsertyactivity;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.R;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertybase.BaseMeiJFenQiDdfgjRfdfgActivity;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyutil.RemindMeiJFenQiDdfgjRfdfgDialog;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyutil.MeiJFenQiDdfgjRfdfgSharePreferencesUtil;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyutil.MeiJFenQiDdfgjRfdfgStatusBarUtil;
import com.victor.loading.rotate.RotateLoading;

public class CancellationMeiJFenQiDdfgjRfdfgActivity extends BaseMeiJFenQiDdfgjRfdfgActivity {

    private TextView titleTv, commitBtn;
    private View backImg, loadingFl;
    private RotateLoading rotateLoading;

    private RemindMeiJFenQiDdfgjRfdfgDialog mRemindMeiJFenQiDdfgjRfdfgDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cancellation_mei_jie_sfgh_ewyfhg;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            mRemindMeiJFenQiDdfgjRfdfgDialog = new RemindMeiJFenQiDdfgjRfdfgDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复", false);
            mRemindMeiJFenQiDdfgjRfdfgDialog.setBtnClickListener(new RemindMeiJFenQiDdfgjRfdfgDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindMeiJFenQiDdfgjRfdfgDialog.dismiss();
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
                    mRemindMeiJFenQiDdfgjRfdfgDialog.dismiss();
                }
            });
            mRemindMeiJFenQiDdfgjRfdfgDialog.show();
            mRemindMeiJFenQiDdfgjRfdfgDialog.setBtnStr("取消", "注销账号");
        });
    }

    @Override
    public void initData() {
        if (MeiJFenQiDdfgjRfdfgSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        MeiJFenQiDdfgjRfdfgStatusBarUtil.setTransparent(this, false);
        commitBtn = findViewById(R.id.commit_btn);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        loadingFl = findViewById(R.id.loading_fl);
        rotateLoading = findViewById(R.id.rotate_loading);
        titleTv.setText("注销账号");
    }
}
