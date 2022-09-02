package com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteui;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.sdldsjqwbaszbdskdf.dfpddfgert.R;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdtebase.BaseRongjieSfFgdfActivity;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteutil.RemindWeiFenQiadsfSsdDialog;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteutil.SharePreferencesUtilWeiFenQiadsfSsd;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteutil.WeiFenQiadsfSsdStatusBarUtil;
import com.victor.loading.rotate.RotateLoading;

public class WeiFenQiadsfSsdCancellationActivity extends BaseRongjieSfFgdfActivity {

    private TextView titleTv, commitBtn;
    private View backImg, loadingFl;
    private RotateLoading rotateLoading;

    private RemindWeiFenQiadsfSsdDialog mRemindWeiFenQiadsfSsdDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wei_fen_qi_dfger_vjkrty_cancellation;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            mRemindWeiFenQiadsfSsdDialog = new RemindWeiFenQiadsfSsdDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复", false);
            mRemindWeiFenQiadsfSsdDialog.setBtnClickListener(new RemindWeiFenQiadsfSsdDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindWeiFenQiadsfSsdDialog.dismiss();
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
                    mRemindWeiFenQiadsfSsdDialog.dismiss();
                }
            });
            mRemindWeiFenQiadsfSsdDialog.show();
            mRemindWeiFenQiadsfSsdDialog.setBtnStr("取消", "注销账号");
        });
    }

    @Override
    public void initData() {
        if (SharePreferencesUtilWeiFenQiadsfSsd.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        WeiFenQiadsfSsdStatusBarUtil.setTransparent(this, false);
        commitBtn = findViewById(R.id.commit_btn);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        loadingFl = findViewById(R.id.loading_fl);
        rotateLoading = findViewById(R.id.rotate_loading);
        titleTv.setText("注销账号");
    }
}
