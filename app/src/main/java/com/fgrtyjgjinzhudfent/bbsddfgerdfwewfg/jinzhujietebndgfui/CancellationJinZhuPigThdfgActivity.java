package com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfui;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.R;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfbase.BaseJinZhuPigThdfgActivity;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfutil.RemindJinZhuPigThdfgDialog;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfutil.JinZhuPigThdfgSharePreferencesUtil;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfutil.StatusJinZhuPigThdfgBarUtil;
import com.victor.loading.rotate.RotateLoading;

public class CancellationJinZhuPigThdfgActivity extends BaseJinZhuPigThdfgActivity {

    private TextView titleTv, commitBtn;
    private View backImg, loadingFl;
    private RotateLoading rotateLoading;

    private RemindJinZhuPigThdfgDialog mRemindJinZhuPigThdfgDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cancellation_jin_zhu_asf_pig;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            mRemindJinZhuPigThdfgDialog = new RemindJinZhuPigThdfgDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复", false);
            mRemindJinZhuPigThdfgDialog.setBtnClickListener(new RemindJinZhuPigThdfgDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindJinZhuPigThdfgDialog.dismiss();
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
                    mRemindJinZhuPigThdfgDialog.dismiss();
                }
            });
            mRemindJinZhuPigThdfgDialog.show();
            mRemindJinZhuPigThdfgDialog.setBtnStr("取消", "注销账号");
        });
    }

    @Override
    public void initData() {
        if (JinZhuPigThdfgSharePreferencesUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        StatusJinZhuPigThdfgBarUtil.setTransparent(this, false);
        commitBtn = findViewById(R.id.commit_btn);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        loadingFl = findViewById(R.id.loading_fl);
        rotateLoading = findViewById(R.id.rotate_loading);
        titleTv.setText("注销账号");
    }
}
