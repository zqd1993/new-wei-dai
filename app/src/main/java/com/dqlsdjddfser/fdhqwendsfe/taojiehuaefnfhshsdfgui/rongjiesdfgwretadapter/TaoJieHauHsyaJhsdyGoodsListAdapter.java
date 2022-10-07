package com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgui.rongjiesdfgwretadapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dqlsdjddfser.fdhqwendsfe.R;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgapi.TaoJieHauHsyaJhsdyRetrofitManager;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgmodel.TaoJieHauHsyaJhsdyGoodsModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class TaoJieHauHsyaJhsdyGoodsListAdapter extends BaseQuickAdapter<TaoJieHauHsyaJhsdyGoodsModel, BaseViewHolder> {

    private ItemClickListener itemClickListener;

    public TaoJieHauHsyaJhsdyGoodsListAdapter(int layoutResId, @Nullable List<TaoJieHauHsyaJhsdyGoodsModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, TaoJieHauHsyaJhsdyGoodsModel item) {
        helper.setText(R.id.goods_name_tv, item.getProductName());
        helper.setText(R.id.goods_amount_tv, String.valueOf(item.getMaxAmount()));
        helper.setText(R.id.zhouqi_tv, item.getDes());
        helper.setText(R.id.people_number_tv, String.valueOf(item.getPassingRate()));
        helper.setText(R.id.info_tv, item.getTag());
        ImageView goodsPic = helper.getView(R.id.goods_pic_img);
        Glide.with(mContext).load(TaoJieHauHsyaJhsdyRetrofitManager.API_BASE_URL + item.getProductLogo()).into(goodsPic);
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
        void onItemClicked(TaoJieHauHsyaJhsdyGoodsModel item);
    }
}
