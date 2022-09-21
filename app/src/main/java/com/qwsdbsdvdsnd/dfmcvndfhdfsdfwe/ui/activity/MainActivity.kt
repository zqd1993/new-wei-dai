package com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.ui.activity

import android.os.Bundle
import android.view.KeyEvent
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.R
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.api.NetSimple
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.mode.BaseModel
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.mode.ConfigModel
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.mode.LoginModel
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.ui.adapter.FragmentAdapter
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.ui.fragment.HomeFragment
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.ui.fragment.ListFragment
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.ui.fragment.WodeFragment
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.util.SPUtil
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.util.StatusBarUtil
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import kotlinx.android.synthetic.main.activity_login_page.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private var phone = ""
    private var ip = ""
    private var exitTime: Long = 0
    private var mFragments: MutableList<Fragment> = ArrayList()
    private var mTitles = arrayOf("首页", "精选", "我的")
    private val uncheckedIcon =
        intArrayOf(R.drawable.vnxet, R.drawable.kdtysfgh, R.drawable.lpuikd)
    private val checkedIcon = intArrayOf(R.drawable.lpiukf, R.drawable.hjbnd, R.drawable.wrgxfm)
    private var customTabEntities: ArrayList<CustomTabEntity>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setTransparent(this, false)
        setContentView(R.layout.activity_main)
        logins()
        customTabEntities = ArrayList()
        home_view_pager.isUserInputEnabled = false
        for (i in 0..2) {
            val customTabEntity: CustomTabEntity = object : CustomTabEntity {
                override fun getTabTitle(): String {
                    return mTitles[i]
                }

                override fun getTabSelectedIcon(): Int {
                    return checkedIcon[i]
                }

                override fun getTabUnselectedIcon(): Int {
                    return uncheckedIcon[i]
                }
            }
            customTabEntities!!.add(customTabEntity)
        }
        tab_layout.setTabData(customTabEntities)
        tab_layout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                home_view_pager.setCurrentItem(position, false)
            }

            override fun onTabReselect(position: Int) {}
        })
        mFragments.add(HomeFragment())
        mFragments.add(ListFragment())
        mFragments.add(WodeFragment())

        home_view_pager.adapter = FragmentAdapter(supportFragmentManager, lifecycle, mFragments)
    }

    private fun logins(){
        phone = SPUtil.getString("phone")
        ip = SPUtil.getString("ip")
        val apiService = NetSimple.getRetrofitManager().apiService
        apiService.logins(phone, ip).enqueue(object : Callback<BaseModel<LoginModel>> {
            override fun onFailure(call: Call<BaseModel<LoginModel>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<BaseModel<LoginModel>>,
                response: Response<BaseModel<LoginModel>>
            ) {

            }
        })
    }

    override fun onResume() {
        super.onResume()
        getConfig()
    }

    private fun getConfig() {
        val apiService = NetSimple.getRetrofitManager().apiService  //传入之前定义的接口

        //Get请求并处理结果
        apiService.getValue("VIDEOTAPE").enqueue(object : Callback<BaseModel<ConfigModel>> {
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
                    SPUtil.saveBool(
                        "NO_RECORD",
                        !configModel.videoTape.equals("0")
                    )
                    if (SPUtil.getBool("NO_RECORD")) {
                        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
                    }
                }
            }
        })
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK
            && event!!.action == KeyEvent.ACTION_DOWN
        ) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(this@MainActivity, "再按一次退出程序", Toast.LENGTH_SHORT).show()
                exitTime = System.currentTimeMillis()
            } else {
                finish()
                exitProcess(0)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}