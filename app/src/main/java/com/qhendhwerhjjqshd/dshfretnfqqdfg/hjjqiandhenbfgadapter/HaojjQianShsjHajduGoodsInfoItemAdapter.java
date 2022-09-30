package com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.R;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgentity.GoodsHaojjQianShsjHajduEntity;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfghttp.MainHaojjQianShsjHajduApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HaojjQianShsjHajduGoodsInfoItemAdapter extends RecyclerView.Adapter<HaojjQianShsjHajduGoodsInfoItemAdapter.GoodsInfoItemHolder> {

    private Context context;
    private List<GoodsHaojjQianShsjHajduEntity> mList;
    private OnItemClickListener onItemClickListener;

    public HaojjQianShsjHajduGoodsInfoItemAdapter(Context context, List<GoodsHaojjQianShsjHajduEntity> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public GoodsInfoItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_hao_jie_she_qtdhfery_goods_info_item_layout, parent, false);
        return new GoodsInfoItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HaojjQianShsjHajduGoodsInfoItemAdapter.GoodsInfoItemHolder holder, int position) {
        GoodsHaojjQianShsjHajduEntity entity = mList.get(position);
        Glide.with(context).load(MainHaojjQianShsjHajduApi.API_BASE_URL + entity.getProductLogo()).into(holder.goodsPic);
        holder.goodsNameTv.setText(entity.getProductName());
        holder.goodsPriceTv.setText(String.valueOf(entity.getMaxAmount()));
        holder.timeTv.setText(entity.getDes());
        holder.parentCl.setOnClickListener(v -> {
            if (onItemClickListener != null){
                onItemClickListener.itemClicked(entity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class GoodsInfoItemHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.goods_pic)
        ImageView goodsPic;
        @BindView(R.id.goods_name_tv)
        TextView goodsNameTv;
        @BindView(R.id.goods_price_tv)
        TextView goodsPriceTv;
        @BindView(R.id.time_tv)
        TextView timeTv;
        @BindView(R.id.parent_cl)
        View parentCl;

        public GoodsInfoItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void itemClicked(GoodsHaojjQianShsjHajduEntity goodsHaojjQianShsjHajduEntity);
    }

}
