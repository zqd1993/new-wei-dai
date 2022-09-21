package com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.R
import kotlinx.android.synthetic.main.dialog_qi_dong_page.*
import java.util.*

class QiDongPageDialog : Dialog{

    private var onListener: OnListener? = null

    constructor(context: Context): super(context, R.style.tran_dialog)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_qi_dong_page)
        setCanceledOnTouchOutside(false)
        top_btn.setOnClickListener {
            onListener?.topBtnClicked()
        }

        bottom_btn.setOnClickListener {
            onListener?.bottomBtnClicked()
        }

        content_tv.setText(createSpanTexts(), { position ->
            when (position) {
                1 -> onListener?.clickedZcxy()
                else -> onListener?.clickedYsxy()
            }
        }, "#FDCA6E")
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

    fun createSpanTexts(): List<ClickTextView.SpanModel>? {
        val spanModels: MutableList<ClickTextView.SpanModel> = ArrayList<ClickTextView.SpanModel>()
        var spanModel = ClickTextView.ClickSpanModel()
        var textSpanModel = ClickTextView.SpanModel()
        textSpanModel.setStr(
            """
            为了保障软件服务的安全、运营的质量及效率，我们会收集您的设备信息和服务日志，具体内容请参照隐私条款；为了预防恶意程序，确保运营质量及效率，我们会收集安装的应用信息或正在进行的进程信息。如果未经您的授权，我们不会使用您的个人信息用于您未授权的其他途径或目的。
            
            我们非常重视对您个人信息的保护，您需要同意
            """.trimIndent()
        )
        spanModels.add(textSpanModel)
        spanModel.str = "《注册服务协议》"
        spanModels.add(spanModel)
        textSpanModel = ClickTextView.SpanModel()
        textSpanModel.str = "和"
        spanModels.add(textSpanModel)
        spanModel = ClickTextView.ClickSpanModel()
        spanModel.str = "《用户隐私协议》"
        spanModels.add(spanModel)
        textSpanModel = ClickTextView.SpanModel()
        textSpanModel.str = "，才能继续使用我们的服务。"
        spanModels.add(textSpanModel)
        return spanModels
    }
}