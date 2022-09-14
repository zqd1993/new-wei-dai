package com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsui.wuyoufdghtyfsadapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.R;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsapi.RetrofitWuYouJieShdfnFjfuirManager;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsmodel.GoodsWuYouJieShdfnFjfuirModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class WuYouJieShdfnFjfuirGoodsListAdapter extends BaseQuickAdapter<GoodsWuYouJieShdfnFjfuirModel, BaseViewHolder> {

    private ItemClickListener itemClickListener;

    public WuYouJieShdfnFjfuirGoodsListAdapter(int layoutResId, @Nullable List<GoodsWuYouJieShdfnFjfuirModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, GoodsWuYouJieShdfnFjfuirModel item) {
        helper.setText(R.id.goods_name_tv, item.getProductName());
        helper.setText(R.id.goods_amount_tv, String.valueOf(item.getMaxAmount()));
        helper.setText(R.id.zhouqi_tv, "周期" + item.getDes());
        helper.setText(R.id.people_number_tv, item.getPassingRate() + "下款");
        helper.setText(R.id.info_tv, item.getTag());
        ImageView goodsPic = helper.getView(R.id.goods_pic_img);
        Glide.with(mContext).load(RetrofitWuYouJieShdfnFjfuirManager.API_BASE_URL + item.getProductLogo()).into(goodsPic);
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
        void onItemClicked(GoodsWuYouJieShdfnFjfuirModel item);
    }
}
