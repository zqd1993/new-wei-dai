package com.asvsdfer.new_master_code_kotlin.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.asvsdfer.new_master_code_kotlin.R
import com.asvsdfer.new_master_code_kotlin.ui.adapter.BaseFragmentAdapter
import com.asvsdfer.new_master_code_kotlin.ui.fragment.HomePageFragment
import com.asvsdfer.new_master_code_kotlin.ui.fragment.MineCenterFragment
import com.asvsdfer.new_master_code_kotlin.ui.livedata.MainViewModel
import com.asvsdfer.new_master_code_kotlin.util.PreferencesOpenUtil
import com.asvsdfer.new_master_code_kotlin.util.StatusBarUtil
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    private var phone = ""
    private var ip = ""
    private var exitTime: Long = 0
    private var mFragments: MutableList<Fragment> = ArrayList()
    private var mTitles = arrayOf("首页", "精选", "我的")
    private val uncheckedIcon =
        intArrayOf(R.drawable.vbber, R.drawable.xcb, R.drawable.ljyhkfd)
    private val checkedIcon = intArrayOf(R.drawable.tydf, R.drawable.wersdtg, R.drawable.lyuig)
    private var customTabEntities: ArrayList<CustomTabEntity>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setTransparent(this, false)
        setContentView(R.layout.activity_main)
        phone = PreferencesOpenUtil.getString("phone").toString()
        ip = PreferencesOpenUtil.getString("ip").toString()
        viewModel.logins(phone, ip)
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
        HomePageFragment.newInstance(1)?.let { mFragments.add(it) }
        HomePageFragment.newInstance(2)?.let { mFragments.add(it) }
        mFragments.add(MineCenterFragment())

        home_view_pager.adapter = BaseFragmentAdapter(supportFragmentManager, lifecycle, mFragments)
        viewModel.getValue.observe(this, { result ->
            val model = result.getOrNull()
            if (model != null) {
                PreferencesOpenUtil.saveBool(
                    "NO_RECORD",
                    model.videoTape != "0"
                )
                if (PreferencesOpenUtil.getBool("NO_RECORD") == true) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getValue("VIDEOTAPE")
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