package com.asvsdfer.new_master_code.ui.activity

import android.os.Bundle
import android.widget.Toast
import com.asvsdfer.new_master_code.R
import com.asvsdfer.new_master_code.widget.ClickTextView
import com.asvsdfer.new_master_code.widget.CountDownTimerUtils
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.activity_login_page.*
import java.util.*

class LoginActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        get_verification_tv.setOnClickListener {
            val mCountDownTimerUtils = CountDownTimerUtils(get_verification_tv, 60000, 1000)
            mCountDownTimerUtils.start()
        }
        login_remind_tv.setText(createSpanTexts(), { position ->
            if (position === 1) {
                Toast.makeText(this, "点击第一个", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "点击第二个", Toast.LENGTH_SHORT).show()
            }
        }, "#ffffff")
    }

    private fun createSpanTexts(): List<ClickTextView.SpanModel>? {
        val spanModels: MutableList<ClickTextView.SpanModel> = ArrayList<ClickTextView.SpanModel>()
        var spanModel = ClickTextView.ClickSpanModel()
        val textSpanModel = ClickTextView.SpanModel()
        textSpanModel.str = "我已阅读并同意"
        spanModels.add(textSpanModel)
        spanModel.str = "《注册服务协议》"
        spanModels.add(spanModel)
        spanModel = ClickTextView.ClickSpanModel()
        spanModel.str = "《用户隐私协议》"
        spanModels.add(spanModel)
        return spanModels
    }

}