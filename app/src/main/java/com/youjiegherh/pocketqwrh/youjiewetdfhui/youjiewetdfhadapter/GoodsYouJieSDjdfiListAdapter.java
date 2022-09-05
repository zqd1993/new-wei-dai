package com.youjiegherh.pocketqwrh.youjiewetdfhui.youjiewetdfhadapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.youjiegherh.pocketqwrh.R;
import com.youjiegherh.pocketqwrh.youjiewetdfhapi.NewCodeXiaoNiuKuaiRetrofitManager;
import com.youjiegherh.pocketqwrh.youjiewetdfhmodel.GoodsYouJieSDjdfiModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class GoodsYouJieSDjdfiListAdapter extends BaseQuickAdapter<GoodsYouJieSDjdfiModel, BaseViewHolder> {

    private ItemClickListener itemClickListener;

    public GoodsYouJieSDjdfiListAdapter(int layoutResId, @Nullable List<GoodsYouJieSDjdfiModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, GoodsYouJieSDjdfiModel item) {
        helper.setText(R.id.goods_name_tv, item.getProductName());
        helper.setText(R.id.goods_amount_tv, String.valueOf(item.getMaxAmount()));
        helper.setText(R.id.zhouqi_tv, item.getDes());
        helper.setText(R.id.people_number_tv, String.valueOf(item.getPassingRate()));
        helper.setText(R.id.info_tv, item.getTag());
        ImageView goodsPic = helper.getView(R.id.goods_pic_img);
        Glide.with(mContext).load(NewCodeXiaoNiuKuaiRetrofitManager.API_BASE_URL + item.getProductLogo()).into(goodsPic);
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
        void onItemClicked(GoodsYouJieSDjdfiModel item);
    }
}
