package com.asvsdfer.new_master_code_kotlin.ui.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import com.asvsdfer.new_master_code_kotlin.R
import kotlinx.android.synthetic.main.dialog_welcome_page.*


class WelcomePageDialog : Dialog {

    private var onListener: OnListener? = null

    constructor(context: Context) : super(context, R.style.tran_dialog)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_welcome_page)
        setCanceledOnTouchOutside(false)
        top_btn.setOnClickListener {
            onListener?.topBtnClicked()
        }

        bottom_btn.setOnClickListener {
            onListener?.bottomBtnClicked()
        }

        val text = arrayOf(
            "为了保障软件服务的安全、运营的质量及效率，我们会收集您的设备信息和服务日志，具体内容请参照隐私条款；" +
                    "为了预防恶意程序，确保运营质量及效率，我们会收集安装的应用信息或正在进行的进程信息。如果未经您的授权，" +
                    "我们不会使用您的个人信息用于您未授权的其他途径或目的。 我们非常重视对您个人信息的保护，您需要同意",
            "《注册服务协议》",
            "和",
            "《用户隐私协议》",
            "，才能继续使用我们的服务。"
        )
        val stringBuffer = StringBuffer()
        for (i in text.indices) {
            stringBuffer.append(text[i])
        }
        content_tv.text = stringBuffer.toString()
        val spannableString = SpannableString(content_tv.text.toString().trim())
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                if (onListener != null) {
                    onListener!!.clickedZcxy()
                }
            }
        }
        if (text.size > 2) {
            val startNum =
                spannableString.length - text[4].length - text[3].length - text[2].length - text[1].length
            val endNum = spannableString.length - text[4].length - text[3].length - text[2].length
            spannableString.setSpan(
                clickableSpan,
                startNum,
                endNum,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
            spannableString.setSpan(
                ForegroundColorSpan(Color.parseColor("#C99F7A")),
                startNum,
                endNum,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
        }
        if (text.size > 4) {
            val clickableSpan1: ClickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    if (onListener != null) {
                        onListener!!.clickedYsxy()
                    }
                }
            }
            val startNum = spannableString.length - text[4].length - text[3].length
            val endNum = spannableString.length - text[4].length
            spannableString.setSpan(
                clickableSpan1,
                startNum,
                endNum,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
            spannableString.setSpan(
                ForegroundColorSpan(Color.parseColor("#C99F7A")),
                startNum,
                endNum,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
        }
        content_tv.text = spannableString
        content_tv.movementMethod = LinkMovementMethod.getInstance()
    }

    fun setOnListener(onListener: OnListener) {
        this.onListener = onListener
    }

    interface OnListener {
        fun topBtnClicked()
        fun bottomBtnClicked()
        fun clickedZcxy()
        fun clickedYsxy()
    }

}