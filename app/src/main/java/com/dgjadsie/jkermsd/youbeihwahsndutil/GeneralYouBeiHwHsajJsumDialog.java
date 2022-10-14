package com.dgjadsie.jkermsd.youbeihwahsndutil;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.dgjadsie.jkermsd.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GeneralYouBeiHwHsajJsumDialog extends Dialog {
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.content_tv)
    TextView contentTv;
    @BindView(R.id.left_btn)
    TextView leftBtn;
    @BindView(R.id.right_btn)
    TextView rightBtn;

    private BtnClickListener btnClickListener;
    private String titleStr, contentStr;
    private Context context;

    public GeneralYouBeiHwHsajJsumDialog(@NonNull Context context, String titleStr, String contentStr) {
        super(context, R.style.tran_dialog);
        this.titleStr = titleStr;
        this.contentStr = contentStr;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(context, R.layout.dialog_you_bei_he_dje_yrhr_general, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        titleTv.setText(titleStr);
        contentTv.setText(contentStr);
        leftBtn.setOnClickListener(v -> {
            if (btnClickListener != null) {
                btnClickListener.leftClicked();
            }
        });
        rightBtn.setOnClickListener(v -> {
            if (btnClickListener != null) {
                btnClickListener.rightClicked();
            }
        });
    }

    public void setBtnStr(String leftStr, String rightStr) {
        leftBtn.setText(leftStr);
        rightBtn.setText(rightStr);
    }

    public void setBtnClickListener(BtnClickListener btnClickListener) {
        this.btnClickListener = btnClickListener;
    }

    public interface BtnClickListener {
        void leftClicked();

        void rightClicked();
    }
}
