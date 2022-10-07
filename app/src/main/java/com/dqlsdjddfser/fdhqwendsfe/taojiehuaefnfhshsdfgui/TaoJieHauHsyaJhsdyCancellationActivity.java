package com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgui;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.dqlsdjddfser.fdhqwendsfe.R;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgbase.BaseTaoJieHauHsyaJhsdyActivity;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgutil.RemindTaoJieHauHsyaJhsdyDialog;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgutil.SharePreferencesUtilTaoJieHauHsyaJhsdy;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgutil.TaoJieHauHsyaJhsdyStatusBarUtil;
import com.victor.loading.rotate.RotateLoading;

public class TaoJieHauHsyaJhsdyCancellationActivity extends BaseTaoJieHauHsyaJhsdyActivity {

    private TextView titleTv, commitBtn;
    private View backImg, loadingFl;
    private RotateLoading rotateLoading;

    private RemindTaoJieHauHsyaJhsdyDialog mRemindTaoJieHauHsyaJhsdyDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_tao_jie_hua_djheru_fhentj_cancellation;
    }

    @Override
    public void initListener() {
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            mRemindTaoJieHauHsyaJhsdyDialog = new RemindTaoJieHauHsyaJhsdyDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复", false);
            mRemindTaoJieHauHsyaJhsdyDialog.setBtnClickListener(new RemindTaoJieHauHsyaJhsdyDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindTaoJieHauHsyaJhsdyDialog.dismiss();
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
                    mRemindTaoJieHauHsyaJhsdyDialog.dismiss();
                }
            });
            mRemindTaoJieHauHsyaJhsdyDialog.show();
            mRemindTaoJieHauHsyaJhsdyDialog.setBtnStr("取消", "注销账号");
        });
    }

    @Override
    public void initData() {
        if (SharePreferencesUtilTaoJieHauHsyaJhsdy.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        TaoJieHauHsyaJhsdyStatusBarUtil.setTransparent(this, false);
        commitBtn = findViewById(R.id.commit_btn);
        titleTv = findViewById(R.id.title_tv);
        backImg = findViewById(R.id.back_image);
        loadingFl = findViewById(R.id.loading_fl);
        rotateLoading = findViewById(R.id.rotate_loading);
        titleTv.setText("注销账号");
    }
}
