package com.asvsdfer.new_master_code_kotlin.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class BaseFragmentAdapter: FragmentStateAdapter {
    private var mFragmentList: List<Fragment>? = null

    constructor(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle, fragments: List<Fragment>
    ) : super(fragmentManager, lifecycle) {
        mFragmentList = fragments
    }

    override fun createFragment(position: Int): Fragment {
        return mFragmentList!![position]
    }

    override fun getItemCount(): Int {
        return mFragmentList!!.size
    }
}