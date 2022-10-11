package com.asvsdfer.new_master_code_kotlin.ui.view

import android.os.CountDownTimer
import android.widget.TextView
import com.asvsdfer.new_master_code_kotlin.MyApp
import com.asvsdfer.new_master_code_kotlin.R

class CountDownTimerUtils(textView: TextView, millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {

    private var textView: TextView = textView

    override fun onTick(millisUntilFinished: Long) {
        textView.isClickable = false //设置不可点击
        textView.text = ("${millisUntilFinished / 1000}秒后可重新发送")
        MyApp.context?.resources?.getColor(R.color.color_999999)?.let {
            textView.setTextColor(
                it
            )
        }
    }

    override fun onFinish() {
        textView.text = "重新获取验证码"
        textView.isClickable = true
        MyApp.context?.resources?.getColor(R.color.colorPrimary)?.let { textView.setTextColor(it) } //还原背景色
    }
}