package com.asvsdfer.new_master_code_kotlin.ui.fragment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.asvsdfer.new_master_code_kotlin.R
import com.asvsdfer.new_master_code_kotlin.logic.model.MineItemModel
import com.asvsdfer.new_master_code_kotlin.logic.network.ServiceCreator
import com.asvsdfer.new_master_code_kotlin.ui.activity.*
import com.asvsdfer.new_master_code_kotlin.ui.adapter.MineItemAdapter
import com.asvsdfer.new_master_code_kotlin.ui.livedata.MainViewModel
import com.asvsdfer.new_master_code_kotlin.ui.view.UserDialog
import com.asvsdfer.new_master_code_kotlin.util.PreferencesOpenUtil
import com.asvsdfer.new_master_code_kotlin.util.jumpActivity
import kotlinx.android.synthetic.main.fragment_mine_center.*

class MineCenterFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    private var mineItemAdapter: MineItemAdapter? = null

    private var mobileStr: String? = null
    private var list: MutableList<MineItemModel>? = ArrayList()
    private val imgRes = intArrayOf(
        R.drawable.cvnr, R.drawable.luofgh, R.drawable.rtyx,
        R.drawable.eryfgh, R.drawable.klgyif, R.drawable.ftyujgh
    )
    private val tvRes = arrayOf("注册协议", "隐私协议", "客服邮箱", "关于我们", "个性化推荐", "注销账户")
    private var mUserDialog: UserDialog? = null
    private var clipboard: ClipboardManager? = null

    private var clipData: ClipData? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mine_center, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clipboard = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        mobileStr = PreferencesOpenUtil.getString("phone")
        customer_mobile_tv.text = mobileStr
        for (i in 0..5) {
            val model = MineItemModel(imgRes[i], tvRes[i])
            list?.add(model)
        }
        setMineData()
        viewModel.getConfig.observe(viewLifecycleOwner) {
            val model = it.getOrNull()
            if (model != null) {
                PreferencesOpenUtil.saveString("APP_MAIL", model.appMail)
                mUserDialog = activity?.let { UserDialog(it, "温馨提示", model.appMail, "取消", "复制") }
                mUserDialog?.setBtnClickListener(object : UserDialog.BtnClickListener {
                    override fun leftClicked() {
                        mUserDialog?.dismiss()
                    }

                    override fun rightClicked() {
                        Toast.makeText(activity, "复制成功", Toast.LENGTH_SHORT).show()
                        clipData = ClipData.newPlainText(null, model.appMail)
                        clipboard!!.setPrimaryClip(clipData!!)
                        mUserDialog?.dismiss()
                    }
                })
                mUserDialog?.show()
            }
        }
        logout_btn.setOnClickListener(View.OnClickListener { v: View? ->
            mUserDialog = activity?.let { UserDialog(it, "温馨提示", "确定退出当前登录", "取消", "退出") }
            mUserDialog?.setBtnClickListener(object : UserDialog.BtnClickListener {
                override fun leftClicked() {
                    mUserDialog?.dismiss()
                }

                override fun rightClicked() {
                    mUserDialog?.dismiss()
                    PreferencesOpenUtil.saveString("phone", "")
                    activity?.let {
                        jumpActivity<LoginActivity>(it)
                        it.finish()
                    }
                }
            })
            mUserDialog?.show()
        })
    }

    private fun setMineData() {
        mineItemAdapter = activity?.let { MineItemAdapter(it, list as ArrayList<MineItemModel>) }
        mineItemAdapter!!.setHasStableIds(true)
        mineItemAdapter!!.setItemClickListener(object : MineItemAdapter.ItemClickListener {
            override fun onItemClicked(position: Int) {
                when (position) {
                    0 -> {
                        activity?.let {
                            jumpActivity<WebViewActivity>(it) {
                                putExtra("tag", 1)
                                putExtra("url", ServiceCreator.ZCXY)
                            }
                        }
                    }
                    1 -> {
                        activity?.let {
                            jumpActivity<WebViewActivity>(it) {
                                putExtra("tag", 2)
                                putExtra("url", ServiceCreator.YSXY)
                            }
                        }
                    }
                    2 -> viewModel.getConfig()
                    3 -> activity?.let {
                        jumpActivity<AppInfoActivity>(it)
                    }
                    4 -> {
                        mUserDialog =
                            activity?.let { UserDialog(it, "温馨提示", "关闭或开启推送", "开启", "关闭") }
                        mUserDialog?.setBtnClickListener(object : UserDialog.BtnClickListener {
                            override fun leftClicked() {
                                Toast.makeText(activity, "开启成功", Toast.LENGTH_SHORT).show()
                                mUserDialog?.dismiss()
                            }

                            override fun rightClicked() {
                                Toast.makeText(activity, "关闭成功", Toast.LENGTH_SHORT).show()
                                mUserDialog?.dismiss()
                            }
                        })
                        mUserDialog?.show()
                    }
                    5 -> activity?.let {
                        jumpActivity<ZhuXiaoActivity>(it)
                    }
                }
            }
        })
        mine_list.setHasFixedSize(true)
        mine_list.layoutManager = GridLayoutManager(activity, 3)
        mine_list.adapter = mineItemAdapter
    }

}