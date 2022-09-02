package com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdui.miaobaitiaosdfsdadapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yhbnwghjfdkdfet.tgvshdjg.R;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdmodel.MiaoBaiTiaoAdfFgsMineItemModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MineItemMiaoBaiTiaoAdfFgsAdapter extends BaseQuickAdapter<MiaoBaiTiaoAdfFgsMineItemModel, BaseViewHolder> {

    private ItemClickListener itemClickListener;

    public MineItemMiaoBaiTiaoAdfFgsAdapter(int layoutResId, @Nullable List<MiaoBaiTiaoAdfFgsMineItemModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MiaoBaiTiaoAdfFgsMineItemModel item) {
        helper.setText(R.id.item_title_tv, item.getItemTitle());
        ImageView itemImg = helper.getView(R.id.item_img);
        itemImg.setImageResource(item.getImgRes());
        helper.getView(R.id.parent_ll).setOnClickListener(v -> {
            if (itemClickListener != null){
                itemClickListener.onItemClicked(helper.getLayoutPosition());
            }
        });
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClicked(int position);
    }
}
