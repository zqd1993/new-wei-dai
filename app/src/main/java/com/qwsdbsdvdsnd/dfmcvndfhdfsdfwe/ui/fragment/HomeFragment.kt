package com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.R
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.api.NetSimple
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.mode.BaseModel
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.mode.EmptyModel
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.mode.GoodsModel
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.ui.activity.ProductDetailsActivity
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.ui.adapter.ProductItemAdapter
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.util.SPUtil
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.util.StaticUtil
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : BaseFragment() {

    private var mobileType = 0
    private var phone: String? = null
    private var productItemAdapter: ProductItemAdapter? = null
    private var bundle: Bundle? = null
    private var mGoodsModel: GoodsModel? = null

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initData() {

    }

    override fun initListener() {
        swipe_refresh_layout.setOnRefreshListener { getProductList() }
        root_ll.setOnClickListener {
            mGoodsModel?.let { productClick(it) }
        }
        no_data_ll.setOnClickListener { getProductList() }
    }

    private fun setListData(mData: List<GoodsModel>) {
        productItemAdapter == null
        productItemAdapter =
            activity?.let { ProductItemAdapter(it, mData as ArrayList<GoodsModel>) }
        productItemAdapter?.setHasStableIds(true)
        productItemAdapter?.setItemClickListener(object : ProductItemAdapter.ItemClickListener {
            override fun onItemClicked(item: GoodsModel?) {
                if (item != null) {
                    productClick(item)
                }
            }
        })
        goods_list.setHasFixedSize(true)
        goods_list.layoutManager = LinearLayoutManager(activity)
        goods_list.adapter = productItemAdapter
    }

    override fun onResume() {
        super.onResume()
        getProductList()
    }

    private fun getProductList() {
        mobileType = SPUtil.getInt("mobileType")
        phone = SPUtil.getString("phone")
        mGoodsModel = null
        val apiService = NetSimple.getRetrofitManager().apiService
        apiService.getGoodsList(mobileType, phone)
            .enqueue(object : Callback<BaseModel<List<GoodsModel>>> {
                override fun onFailure(call: Call<BaseModel<List<GoodsModel>>>, t: Throwable) {
                    t.printStackTrace()
                    if (productItemAdapter == null) {
                        no_data_ll.visibility = View.VISIBLE
                        goods_list.visibility = View.GONE
                    }
                }

                override fun onResponse(
                    call: Call<BaseModel<List<GoodsModel>>>,
                    response: Response<BaseModel<List<GoodsModel>>>
                ) {
                    val model = response.body() ?: return
                    if (model != null) {
                        if (model.data != null && model.data.isNotEmpty()) {
                            no_data_ll.visibility = View.GONE
                            goods_list.visibility = View.VISIBLE
                            mGoodsModel = model.data[0]
                            setListData(model.getData())
                        } else {
                            no_data_ll.visibility = View.VISIBLE
                            goods_list.visibility = View.GONE
                        }
                    } else {
                        no_data_ll.visibility = View.VISIBLE
                        goods_list.visibility = View.GONE
                    }
                }
            })
    }

    private fun productClick(goodsModel: GoodsModel) {
        phone = SPUtil.getString("phone")
        val apiService = NetSimple.getRetrofitManager().apiService
        apiService.productClick(goodsModel!!.id, phone)
            .enqueue(object : Callback<BaseModel<EmptyModel>> {
                override fun onFailure(call: Call<BaseModel<EmptyModel>>, t: Throwable) {
                    t.printStackTrace()
                    if (productItemAdapter == null) {
                        no_data_ll.visibility = View.VISIBLE
                        goods_list.visibility = View.GONE
                    }
                }

                override fun onResponse(
                    call: Call<BaseModel<EmptyModel>>,
                    response: Response<BaseModel<EmptyModel>>
                ) {
                    bundle = Bundle()
                    bundle?.putString("title", goodsModel!!.productName)
                    bundle?.putString("url", goodsModel!!.url)
                    activity?.let { StaticUtil.startActivity(it, ProductDetailsActivity::class.java, bundle) }
                }
            })
    }
}