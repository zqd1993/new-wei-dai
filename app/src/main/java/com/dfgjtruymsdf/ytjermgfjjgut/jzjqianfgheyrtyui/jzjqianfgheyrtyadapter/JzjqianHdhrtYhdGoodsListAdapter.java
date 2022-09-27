package com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyui.jzjqianfgheyrtyadapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dfgjtruymsdf.ytjermgfjjgut.R;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyapi.JzjqianHdhrtYhdRetrofitManager;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtymodel.GoodsJzjqianHdhrtYhdModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class JzjqianHdhrtYhdGoodsListAdapter extends BaseQuickAdapter<GoodsJzjqianHdhrtYhdModel, BaseViewHolder> {

    private ItemClickListener itemClickListener;

    public JzjqianHdhrtYhdGoodsListAdapter(int layoutResId, @Nullable List<GoodsJzjqianHdhrtYhdModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, GoodsJzjqianHdhrtYhdModel item) {
        helper.setText(R.id.goods_name_tv, item.getProductName());
        helper.setText(R.id.goods_amount_tv, String.valueOf(item.getMaxAmount()));
        helper.setText(R.id.zhouqi_tv, item.getDes());
        helper.setText(R.id.people_number_tv, String.valueOf(item.getPassingRate()));
        helper.setText(R.id.info_tv, item.getTag());
        ImageView goodsPic = helper.getView(R.id.goods_pic_img);
        Glide.with(mContext).load(JzjqianHdhrtYhdRetrofitManager.API_BASE_URL + item.getProductLogo()).into(goodsPic);
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
        void onItemClicked(GoodsJzjqianHdhrtYhdModel item);
    }
}
