package com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernui;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.ueruzdfgnh.urngfhag.R;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernbase.BaseWuYFenQiHuysdjDshryActivity;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernutil.RemindWuYFenQiHuysdjDshryDialog;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernutil.WuYFenQiHuysdjDshrySharePreferencesUtil;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernutil.WuYFenQiHuysdjDshryStatusBarUtil;
import com.victor.loading.rotate.RotateLoading;

public class CancellationWuYFenQiHuysdjDshryActivity extends BaseWuYFenQiHuysdjDshryActivity {

    private TextView titleTv, commitBtn;
    private View backImg, loadingFl;
    private RotateLoading rotateLoading;

    private RemindWuYFenQiHuysdjDshryDialog mRemindWuYFenQiHuysdjDshryDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cancellation_wu_yfen_qijai_dfjrt;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            mRemindWuYFenQiHuysdjDshryDialog = new RemindWuYFenQiHuysdjDshryDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复", false);
            mRemindWuYFenQiHuysdjDshryDialog.setBtnClickListener(new RemindWuYFenQiHuysdjDshryDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindWuYFenQiHuysdjDshryDialog.dismiss();
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
                    mRemindWuYFenQiHuysdjDshryDialog.dismiss();
                }
            });
            mRemindWuYFenQiHuysdjDshryDialog.show();
            mRemindWuYFenQiHuysdjDshryDialog.setBtnStr("取消", "注销账号");
        });
    }

    @Override
    public void initData() {
        if (WuYFenQiHuysdjDshrySharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        WuYFenQiHuysdjDshryStatusBarUtil.setTransparent(this, false);
        commitBtn = findViewById(R.id.commit_btn);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        loadingFl = findViewById(R.id.loading_fl);
        rotateLoading = findViewById(R.id.rotate_loading);
        titleTv.setText("注销账号");
    }
}
