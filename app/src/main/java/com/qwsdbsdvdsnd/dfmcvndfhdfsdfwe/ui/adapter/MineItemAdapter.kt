package com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.R
import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.mode.MineItemModel

class MineItemAdapter(context: Context, mList: ArrayList<MineItemModel>) :
    RecyclerView.Adapter<MineItemAdapter.MineItemViewHolder>() {

    private val context: Context? = context

    private val mList: MutableList<MineItemModel>? = mList

    private var itemClickListener: MineItemAdapter.ItemClickListener? = null

    inner class MineItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item_title_tv: TextView = itemView.findViewById(R.id.item_title_tv)
        val item_img: ImageView = itemView.findViewById(R.id.item_img);
        val parent_ll: View = itemView.findViewById(R.id.parent_ll);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MineItemViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.adapter_mine_list_litm, parent, false)
        return MineItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MineItemViewHolder, position: Int) {
        val model: MineItemModel? = mList?.get(position)
        model?.let {
            holder.item_title_tv.text = it.itemTitle
            holder.item_img.setImageResource(it.imgRes)
        }
        holder.parent_ll.setOnClickListener {
            if (itemClickListener != null) {
                itemClickListener!!.onItemClicked(position)
            }
        }
    }

    override fun getItemCount(): Int = mList!!.size

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClicked(position: Int)
    }

}