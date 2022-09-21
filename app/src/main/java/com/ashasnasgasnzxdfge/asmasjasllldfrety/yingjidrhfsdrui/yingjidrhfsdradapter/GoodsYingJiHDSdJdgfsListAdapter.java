package com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrui.yingjidrhfsdradapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ashasnasgasnzxdfge.asmasjasllldfrety.R;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrapi.YingJiHDSdJdgfsRetrofitManager;
import com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrmodel.YingJiHDSdJdgfsGoodsModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class GoodsYingJiHDSdJdgfsListAdapter extends BaseQuickAdapter<YingJiHDSdJdgfsGoodsModel, BaseViewHolder> {

    private ItemClickListener itemClickListener;

    public GoodsYingJiHDSdJdgfsListAdapter(int layoutResId, @Nullable List<YingJiHDSdJdgfsGoodsModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, YingJiHDSdJdgfsGoodsModel item) {
        helper.setText(R.id.goods_name_tv, item.getProductName());
        helper.setText(R.id.goods_amount_tv, String.valueOf(item.getMaxAmount()));
        helper.setText(R.id.zhouqi_tv, "周期" + item.getDes());
        helper.setText(R.id.people_number_tv, item.getPassingRate() + "下款");
        helper.setText(R.id.info_tv, item.getTag());
        ImageView goodsPic = helper.getView(R.id.goods_pic_img);
        Glide.with(mContext).load(YingJiHDSdJdgfsRetrofitManager.API_BASE_URL + item.getProductLogo()).into(goodsPic);
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
        void onItemClicked(YingJiHDSdJdgfsGoodsModel item);
    }
}
