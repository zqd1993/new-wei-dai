package com.qingsongvyrnng.mrjgndsdg.qingsojdkvutil;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.qingsongvyrnng.mrjgndsdg.R;

public class BaseQingSongShfjAFduWelcomeRemindDialog extends Dialog {

    BaseQingSongShfjAFduClickTextView content_tv;
    TextView topBtn;
    TextView bottomBtn;

    private OnListener onListener;

    public BaseQingSongShfjAFduWelcomeRemindDialog(@NonNull Context context) {
        super(context, R.style.tran_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_welcome_remind_qing_song_sh_udj);
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

        content_tv.setText(StaticBaseQingSongShfjAFduUtil.createSpanTexts(), position -> {
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
        }, "#262765");

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
