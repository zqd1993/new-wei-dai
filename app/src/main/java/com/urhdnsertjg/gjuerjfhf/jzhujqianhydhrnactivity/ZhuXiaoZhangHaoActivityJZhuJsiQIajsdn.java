package com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnactivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.urhdnsertjg.gjuerjfhf.R;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnutil.JZhuJsiQIajsdnGeneralDialog;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnutil.MyJZhuJsiQIajsdnPreferences;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnutil.JZhuJsiQIajsdnStatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZhuXiaoZhangHaoActivityJZhuJsiQIajsdn extends AppCompatActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_image)
    View backImg;
    @BindView(R.id.commit_btn)
    View commitBtn;

    private JZhuJsiQIajsdnGeneralDialog JZhuJsiQIajsdnGeneralDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JZhuJsiQIajsdnStatusBarUtil.setTransparent(this, false);
        if (MyJZhuJsiQIajsdnPreferences.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_zhuxiao_zhanghao_jin_zhu_jqi_djrufn_dfke);
        ButterKnife.bind(this);
        titleTv.setText("注销账号");
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            JZhuJsiQIajsdnGeneralDialog = new JZhuJsiQIajsdnGeneralDialog(this, "温馨提示", "是否注销账号？注销后将不能恢复");
            JZhuJsiQIajsdnGeneralDialog.setBtnClickListener(new JZhuJsiQIajsdnGeneralDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    Toast.makeText(ZhuXiaoZhangHaoActivityJZhuJsiQIajsdn.this, "提交成功", Toast.LENGTH_SHORT).show();
                    JZhuJsiQIajsdnGeneralDialog.dismiss();
                    finish();
                }

                @Override
                public void rightClicked() {
                    JZhuJsiQIajsdnGeneralDialog.dismiss();
                }
            });
            JZhuJsiQIajsdnGeneralDialog.show();
            JZhuJsiQIajsdnGeneralDialog.setBtnStr("注销账号", "取消");
        });
    }
}
