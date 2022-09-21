package com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.R
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.api.NetSimple
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.mode.GoodsModel
import com.bumptech.glide.Glide
import java.lang.String


class ProductItemAdapter(context: Context, mList: ArrayList<GoodsModel>) :
    RecyclerView.Adapter<ProductItemAdapter.ProductItemViewHolder>() {

    private val context: Context? = context

    private val mList: MutableList<GoodsModel>? = mList

    private var itemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.adapter_product_item_layout, parent, false)
        return ProductItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductItemViewHolder, position: Int) {
        val goodsModel: GoodsModel? = mList?.get(position);
        goodsModel?.let {
            holder.product_name_tv.text = it.productName
            holder.product_amount_tv.text = String.valueOf(it.maxAmount)
            holder.zhouqi_tv.text = it.des
            holder.info_tv.text = it.tag
            if (context != null) {
                Glide.with(context).load(NetSimple.API_BASE_URL + it.productLogo)
                    .into(holder.product_pic_img)
            }
        }
        holder.parent_sl.setOnClickListener {
            if (goodsModel != null) {
                itemClickListener?.onItemClicked(goodsModel)
            }
        }
    }

    override fun getItemCount(): Int = mList!!.size

    inner class ProductItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val product_pic_img: ImageView = itemView.findViewById(R.id.product_pic_img)
        val product_name_tv: TextView = itemView.findViewById(R.id.product_name_tv)
        val product_amount_tv: TextView = itemView.findViewById(R.id.product_amount_tv)
        val zhouqi_tv: TextView = itemView.findViewById(R.id.zhouqi_tv)
        val info_tv: TextView = itemView.findViewById(R.id.info_tv)
        val parent_sl: View = itemView.findViewById(R.id.parent_sl)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClicked(item: GoodsModel?)
    }
}