package com.asvsdfer.new_master_code_kotlin.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.asvsdfer.new_master_code_kotlin.R
import com.asvsdfer.new_master_code_kotlin.logic.model.BaseModel
import com.asvsdfer.new_master_code_kotlin.logic.model.EmptyModel
import com.asvsdfer.new_master_code_kotlin.logic.model.ProductModel
import com.asvsdfer.new_master_code_kotlin.logic.network.BaseNetWork
import com.asvsdfer.new_master_code_kotlin.ui.activity.GoodsDetailsActivity
import com.asvsdfer.new_master_code_kotlin.ui.adapter.GoodsListAdapter
import com.asvsdfer.new_master_code_kotlin.ui.livedata.MainViewModel
import com.asvsdfer.new_master_code_kotlin.util.PreferencesOpenUtil
import com.asvsdfer.new_master_code_kotlin.util.jumpActivity
import kotlinx.android.synthetic.main.fragment_home_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePageFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    private var mobileType = 0
    private var type = 0
    private var phone: String? = null

    private var goodsListAdapter: GoodsListAdapter? = null
    private var productModel: ProductModel? = null

    companion object {
        fun newInstance(type: Int): HomePageFragment? {
            val args = Bundle()
            args.putInt("type", type)
            val fragment = HomePageFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mobileType = PreferencesOpenUtil.getInt("mobileType")!!
        phone = PreferencesOpenUtil.getString("phone").toString()
        val arguments = arguments
        if (arguments != null) {
            type = arguments.getInt("type")
        }
        if (type == 1) {
            product_bg.visibility = View.GONE
            home_page_bg.visibility = View.VISIBLE
        } else {
            product_bg.visibility = View.VISIBLE
            home_page_bg.visibility = View.GONE
        }
        swipe_refresh_layout.setOnRefreshListener { getGoodsList() }
        root_ll.setOnClickListener {
            productModel?.let { productClick(it) }
        }
        no_data_ll.setOnClickListener { getGoodsList() }
        viewModel.getGoodsList.observe(viewLifecycleOwner) {
            swipe_refresh_layout.isRefreshing = false
            val model = it.getOrNull()
            if (model != null && model.isNotEmpty()) {
                no_data_ll.visibility = View.GONE
                goods_list.visibility = View.VISIBLE
                productModel = model[0]
                setListData(model)
            } else {
                no_data_ll.visibility = View.VISIBLE
                goods_list.visibility = View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getGoodsList()
    }

    private fun getGoodsList() {
        productModel = null
        phone?.let { viewModel.getGoodsList(mobileType, it) }
    }

    private fun setListData(mData: List<ProductModel>) {
        goodsListAdapter == null
        goodsListAdapter =
            activity?.let { GoodsListAdapter(it, mData) }
        goodsListAdapter?.setHasStableIds(true)
        goodsListAdapter?.setItemClickListener(object : GoodsListAdapter.ItemClickListener {
            override fun onItemClicked(item: ProductModel) {
                if (item != null) {
                    productClick(item)
                }
            }
        })
        goods_list.setHasFixedSize(true)
        goods_list.layoutManager = LinearLayoutManager(activity)
        goods_list.adapter = goodsListAdapter
    }

    private fun productClick(productModel: ProductModel) {
        BaseNetWork.placeService.productClick(productModel!!.id, phone!!)
            .enqueue(object : Callback<BaseModel<EmptyModel>> {
                override fun onFailure(call: Call<BaseModel<EmptyModel>>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<BaseModel<EmptyModel>>,
                    response: Response<BaseModel<EmptyModel>>
                ) {
                    activity?.let { it ->
                        jumpActivity<GoodsDetailsActivity>(it) {
                            putExtra("title", productModel.productName)
                            putExtra("url", productModel.url)
                        }
                    }
                }
            })
    }
}