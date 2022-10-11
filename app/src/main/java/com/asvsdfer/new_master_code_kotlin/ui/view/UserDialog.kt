package com.asvsdfer.new_master_code_kotlin.ui.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.asvsdfer.new_master_code_kotlin.R
import kotlinx.android.synthetic.main.dialog_user.*

class UserDialog(context: Context, private val titleStr: String, private val contentStr: String,
                 private val leftStr: String, private val rightStr: String) : Dialog(context) {

    private var btnClickListener: BtnClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_user)
        title_tv.text = titleStr
        content_tv.text = contentStr
        left_btn.text = leftStr
        right_btn.text = rightStr
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