package com.geihuawefvjelkfu.qwersdbn.ncopgeinihuaui;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.geihuawefvjelkfu.qwersdbn.R;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuabase.BaseNcOpGeiNiHuaActivity;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuautil.NcOpGeiNiHuaRemindDialog;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuautil.NcOpGeiNiHuaSharePreferencesUtil;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuautil.NcOpGeiNiHuaStatusBarUtil;
import com.victor.loading.rotate.RotateLoading;

public class NcOpGeiNiHuaCancellationNcOpGeiNiHuaActivity extends BaseNcOpGeiNiHuaActivity {

    private TextView titleTv, commitBtn;
    private View backImg, loadingFl;
    private RotateLoading rotateLoading;

    private NcOpGeiNiHuaRemindDialog mNcOpGeiNiHuaRemindDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cancellation;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            mNcOpGeiNiHuaRemindDialog = new NcOpGeiNiHuaRemindDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复", false);
            mNcOpGeiNiHuaRemindDialog.setBtnClickListener(new NcOpGeiNiHuaRemindDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mNcOpGeiNiHuaRemindDialog.dismiss();
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
                    mNcOpGeiNiHuaRemindDialog.dismiss();
                }
            });
            mNcOpGeiNiHuaRemindDialog.show();
            mNcOpGeiNiHuaRemindDialog.setBtnStr("取消", "注销账号");
        });
    }

    @Override
    public void initData() {
        if (NcOpGeiNiHuaSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        NcOpGeiNiHuaStatusBarUtil.setTransparent(this, false);
        commitBtn = findViewById(R.id.commit_btn);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        loadingFl = findViewById(R.id.loading_fl);
        rotateLoading = findViewById(R.id.rotate_loading);
        titleTv.setText("注销账号");
    }
}
