package com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.R
import kotlinx.android.synthetic.main.dialog_user.*

class UserDialog(context: Context, titleStr: String, contentStr: String, showOneBtn: Boolean) : Dialog(context){

    private var btnClickListener: BtnClickListener? = null
    private val titleStr = titleStr
    private val contentStr = contentStr
    private val showOneBtn = showOneBtn

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_user)
        title_tv.text = titleStr
        content_tv.text = contentStr

        if (showOneBtn) {
            btn_ll.visibility = View.GONE
            one_btn_ll.visibility = View.VISIBLE
        }
        left_btn.setOnClickListener(View.OnClickListener { v: View? ->
            if (btnClickListener != null) {
                btnClickListener!!.leftClicked()
            }
        })
        right_btn.setOnClickListener(View.OnClickListener { v: View? ->
            if (btnClickListener != null) {
                btnClickListener!!.rightClicked()
            }
        })
        sure_btn.setOnClickListener(View.OnClickListener { v: View? -> dismiss() })
    }

    fun setBtnClickListener(btnClickListener: BtnClickListener) {
        this.btnClickListener = btnClickListener
    }

    fun setBtnStr(leftStr: String?, rightStr: String?) {
        left_btn.text = leftStr
        right_btn.text = rightStr
    }

    interface BtnClickListener {
        fun leftClicked()
        fun rightClicked()
    }

}