package com.fjsdkqwj.pfdioewjnsd.ui.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fjsdkqwj.pfdioewjnsd.R;
import com.fjsdkqwj.pfdioewjnsd.api.RetrofitManager;
import com.fjsdkqwj.pfdioewjnsd.model.GoodsModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class GoodsListAdapter extends BaseQuickAdapter<GoodsModel, BaseViewHolder> {

    private ItemClickListener itemClickListener;

    public GoodsListAdapter(int layoutResId, @Nullable List<GoodsModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, GoodsModel item) {
        helper.setText(R.id.goods_name_tv, item.getProductName());
        helper.setText(R.id.goods_amount_tv, String.valueOf(item.getMaxAmount()));
        helper.setText(R.id.zhouqi_tv, item.getDes());
        helper.setText(R.id.people_number_tv, String.valueOf(item.getPassingRate()));
        helper.setText(R.id.info_tv, item.getTag());
        ImageView goodsPic = helper.getView(R.id.goods_pic_img);
        Glide.with(mContext).load(RetrofitManager.API_BASE_URL + item.getProductLogo()).into(goodsPic);
        helper.getView(R.id.parent_sl).setOnClickListener(v -> {
            if (itemClickListener != null){
                itemClickListener.onItemClicked(item);
            }
        });
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener{
        void onItemClicked(GoodsModel item);
    }
}
