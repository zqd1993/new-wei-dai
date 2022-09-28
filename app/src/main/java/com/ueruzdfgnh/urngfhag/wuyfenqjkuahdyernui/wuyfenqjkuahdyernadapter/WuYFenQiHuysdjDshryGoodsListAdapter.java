package com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernui.wuyfenqjkuahdyernadapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ueruzdfgnh.urngfhag.R;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernapi.RetrofitWuYFenQiHuysdjDshryManager;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernmodel.GoodsWuYFenQiHuysdjDshryModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class WuYFenQiHuysdjDshryGoodsListAdapter extends BaseQuickAdapter<GoodsWuYFenQiHuysdjDshryModel, BaseViewHolder> {

    private ItemClickListener itemClickListener;

    public WuYFenQiHuysdjDshryGoodsListAdapter(int layoutResId, @Nullable List<GoodsWuYFenQiHuysdjDshryModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, GoodsWuYFenQiHuysdjDshryModel item) {
        helper.setText(R.id.goods_name_tv, item.getProductName());
        helper.setText(R.id.goods_amount_tv, String.valueOf(item.getMaxAmount()));
        helper.setText(R.id.zhouqi_tv, "周期" + item.getDes());
        helper.setText(R.id.people_number_tv, item.getPassingRate() + "下款");
        helper.setText(R.id.info_tv, item.getTag());
        ImageView goodsPic = helper.getView(R.id.goods_pic_img);
        Glide.with(mContext).load(RetrofitWuYFenQiHuysdjDshryManager.API_BASE_URL + item.getProductLogo()).into(goodsPic);
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
        void onItemClicked(GoodsWuYFenQiHuysdjDshryModel item);
    }
}
