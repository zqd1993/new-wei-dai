package com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnutil;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.urhdnsertjg.gjuerjfhf.R;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnapp.MainJZhuJsiQIajsdnApp;

public class JZhuJsiQIajsdnCountDownTimerTextView extends CountDownTimer {
    private TextView mTextView;

    public JZhuJsiQIajsdnCountDownTimerTextView(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false); //设置不可点击
        mTextView.setText(millisUntilFinished / 1000 + "秒后可重新发送");  //设置倒计时时间
        mTextView.setTextColor(MainJZhuJsiQIajsdnApp.getContext().getResources().getColor(R.color.color_999999)); //设置按钮为灰色，这时是不能点击的
    }

    @Override
    public void onFinish() {
        mTextView.setText("重新获取验证码");
        mTextView.setClickable(true);//重新获得点击
        mTextView.setTextColor(MainJZhuJsiQIajsdnApp.getContext().getResources().getColor(R.color.white));  //还原背景色
    }

}
