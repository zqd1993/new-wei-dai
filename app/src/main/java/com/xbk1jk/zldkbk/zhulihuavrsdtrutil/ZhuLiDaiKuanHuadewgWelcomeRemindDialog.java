package com.xbk1jk.zldkbk.zhulihuavrsdtrutil;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xbk1jk.zldkbk.R;

public class ZhuLiDaiKuanHuadewgWelcomeRemindDialog extends Dialog {

    ClickZhuLiDaiKuanHuadewgTextView content_tv;
    TextView topBtn;
    TextView bottomBtn;

    private OnListener onListener;

    public ZhuLiDaiKuanHuadewgWelcomeRemindDialog(@NonNull Context context) {
        super(context, R.style.tran_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_welcome_remind_miao_bai_tiao_sdf);
        setCanceledOnTouchOutside(false);
        content_tv = findViewById(R.id.content_tv);
        topBtn = findViewById(R.id.top_btn);
        bottomBtn = findViewById(R.id.bottom_btn);
        topBtn.setOnClickListener(v -> {
            if (onListener != null) {
                onListener.topBtnClicked();
            }
        });

        bottomBtn.setOnClickListener(v -> {
            if (onListener != null) {
                onListener.bottomBtnClicked();
            }
        });

        content_tv.setText(StaticZhuLiDaiKuanHuadewgUtil.createSpanTexts(), position -> {
            switch (position) {
                case 1:
                    if (onListener != null) {
                        onListener.clickedZcxy();
                    }
                    break;
                default:
                    if (onListener != null) {
                        onListener.clickedYsxy();
                    }
                    break;
            }
        }, "#FF03DAC5");

    }

    @Override
    public void show() {
        super.show();
    }

    public void setOnListener(OnListener onListener) {
        this.onListener = onListener;
    }

    public interface OnListener {
        void topBtnClicked();

        void bottomBtnClicked();

        void clickedZcxy();

        void clickedYsxy();
    }

}
