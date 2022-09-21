package com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.ui.fragment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.R
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.api.NetSimple
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.mode.BaseModel
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.mode.ConfigModel
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.mode.MineItemModel
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.ui.activity.AppVersionActivity
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.ui.activity.LoginActivity
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.ui.activity.UserAgreementActivity
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.ui.activity.ZhuXiaoActivity
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.ui.adapter.MineItemAdapter
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.util.SPUtil
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.util.StaticUtil
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.widget.UserDialog
import kotlinx.android.synthetic.main.fragment_wode.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class WodeFragment : BaseFragment(){

    private var mineItemAdapter: MineItemAdapter? = null

    private var mobileStr: String? = null
    private var list: MutableList<MineItemModel>? = ArrayList()
    private val imgRes = intArrayOf(
        R.drawable.kyuidf, R.drawable.xcvbsrt, R.drawable.luiptg,
        R.drawable.tydfgh, R.drawable.zvbrt, R.drawable.vbbgsr
    )
    private val tvRes = arrayOf("注册协议", "隐私协议", "客服邮箱", "关于我们", "个性化推荐", "注销账户")
    private var bundle: Bundle? = null
    private var mUserDialog: UserDialog? = null
    private var clipboard: ClipboardManager? = null

    private var clipData: ClipData? = null

    override fun getLayoutId(): Int = R.layout.fragment_wode

    override fun initData() {
        clipboard = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        mobileStr = SPUtil.getString("phone")
        customer_mobile_tv.text = mobileStr
        for (i in 0..5) {
            val model = MineItemModel()
            model.imgRes = imgRes[i]
            model.itemTitle = tvRes[i]
            list?.add(model)
        }
        setMineData()
    }

    override fun initListener() {
        logout_btn.setOnClickListener(View.OnClickListener { v: View? ->
            mUserDialog = activity?.let { UserDialog(it, "温馨提示", "确定退出当前登录", false) }
            mUserDialog?.setBtnClickListener(object : UserDialog.BtnClickListener {
                override fun leftClicked() {
                    mUserDialog?.dismiss()
                }

                override fun rightClicked() {
                    mUserDialog?.dismiss()
                    SPUtil.saveString("phone", "")
                    activity?.let { StaticUtil.startActivity(it, LoginActivity::class.java, null) }
                    activity!!.finish()
                }
            })
            mUserDialog?.show()
            mUserDialog?.setBtnStr("取消", "退出")
        })
    }

    private fun setMineData() {
        mineItemAdapter = activity?.let { MineItemAdapter(it, list as ArrayList<MineItemModel>) }
        mineItemAdapter!!.setHasStableIds(true)
        mineItemAdapter!!.setItemClickListener(object : MineItemAdapter.ItemClickListener{
            override fun onItemClicked(position: Int) {
                when (position) {
                    0 -> {
                        bundle = Bundle()
                        bundle?.putInt("tag", 1)
                        bundle?.putString("url", NetSimple.ZCXY)
                        StaticUtil.startActivity(activity!!, UserAgreementActivity::class.java, bundle)
                    }
                    1 -> {
                        bundle = Bundle()
                        bundle?.putInt("tag", 2)
                        bundle?.putString("url", NetSimple.YSXY)
                        StaticUtil.startActivity(activity!!, UserAgreementActivity::class.java, bundle)
                    }
                    2 -> getConfig()
                    3 -> StaticUtil.startActivity(activity!!, AppVersionActivity::class.java, null)
                    4 -> {
                        mUserDialog = activity?.let { UserDialog(it, "温馨提示", "关闭或开启推送", false) }
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
                        mUserDialog?.setBtnStr("开启", "关闭")
                    }
                    5 -> StaticUtil.startActivity(activity!!, ZhuXiaoActivity::class.java, null)
                }
            }
        })
        mine_list.setHasFixedSize(true)
        mine_list.layoutManager = LinearLayoutManager(activity)
        mine_list.adapter = mineItemAdapter
    }

    private fun getConfig() {
        val apiService = NetSimple.getRetrofitManager().apiService
        apiService.config.enqueue(object : Callback<BaseModel<ConfigModel>> {
            override fun onFailure(call: Call<BaseModel<ConfigModel>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<BaseModel<ConfigModel>>,
                response: Response<BaseModel<ConfigModel>>
            ) {
                val model = response.body() ?: return
                val configModel = model.data
                if (configModel != null) {
                    SPUtil.saveString("APP_MAIL", configModel.appMail)
                    mUserDialog = activity?.let { UserDialog(it, "温馨提示", configModel.appMail, false) }
                    mUserDialog?.setBtnClickListener(object : UserDialog.BtnClickListener {
                        override fun leftClicked() {
                            mUserDialog?.dismiss()
                        }

                        override fun rightClicked() {
                            Toast.makeText(activity, "复制成功", Toast.LENGTH_SHORT).show()
                            clipData = ClipData.newPlainText(null, configModel.appMail)
                            clipboard!!.setPrimaryClip(clipData!!)
                            mUserDialog?.dismiss()
                        }
                    })
                    mUserDialog?.show()
                    mUserDialog?.setBtnStr("取消", "复制")
                }
            }
        })
    }
}