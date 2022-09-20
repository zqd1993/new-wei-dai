package com.asvsdfer.new_master_code.widget

import android.os.CountDownTimer
import android.widget.TextView
import com.asvsdfer.new_master_code.MineApp
import com.asvsdfer.new_master_code.R

class CountDownTimerUtils(textView: TextView, millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {

    private var textView: TextView = textView

    override fun onTick(millisUntilFinished: Long) {
        textView.isClickable = false //设置不可点击
        textView.text = ("${millisUntilFinished / 1000}秒后可重新发送")
        MineApp.getContext()?.resources?.getColor(R.color.color_999999)?.let {
            textView.setTextColor(
                it
            )
        }
    }

    override fun onFinish() {
        textView.text = "重新获取验证码"
        textView.isClickable = true
        MineApp.getContext()?.resources?.getColor(R.color.white)?.let { textView.setTextColor(it) } //还原背景色
    }
}