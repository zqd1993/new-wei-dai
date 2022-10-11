package com.asvsdfer.new_master_code_kotlin.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asvsdfer.new_master_code_kotlin.R
import com.asvsdfer.new_master_code_kotlin.logic.model.ProductModel
import com.asvsdfer.new_master_code_kotlin.logic.network.ServiceCreator
import com.bumptech.glide.Glide
import java.lang.String

class GoodsListAdapter(private val context: Context, private val goodsList: List<ProductModel>) :
    RecyclerView.Adapter<GoodsListAdapter.GoodsListHolder>() {

    private var itemClickListener: ItemClickListener? = null

    inner class GoodsListHolder(view: View) : RecyclerView.ViewHolder(view) {
        val product_pic_img: ImageView = itemView.findViewById(R.id.product_pic_img)
        val product_name_tv: TextView = itemView.findViewById(R.id.product_name_tv)
        val product_amount_tv: TextView = itemView.findViewById(R.id.product_amount_tv)
        val zhouqi_tv: TextView = itemView.findViewById(R.id.zhouqi_tv)
        val info_tv: TextView = itemView.findViewById(R.id.info_tv)
        val parent_ll: View = itemView.findViewById(R.id.parent_ll)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsListHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_goods_product_list,
            parent, false
        )
        return GoodsListHolder(view)
    }

    override fun onBindViewHolder(holder: GoodsListHolder, position: Int) {
        val productModel: ProductModel? = goodsList[position];
        productModel?.let {
            holder.product_name_tv.text = it.productName
            holder.product_amount_tv.text = String.valueOf(it.maxAmount)
            holder.zhouqi_tv.text = it.des
            holder.info_tv.text = it.tag
            if (context != null) {
                Glide.with(context).load(ServiceCreator.BASE_URL + it.productLogo)
                    .into(holder.product_pic_img)
            }
        }
        holder.parent_ll.setOnClickListener {
            if (productModel != null) {
                itemClickListener?.onItemClicked(productModel)
            }
        }
    }

    override fun getItemCount() = goodsList.size

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClicked(item: ProductModel)
    }
}