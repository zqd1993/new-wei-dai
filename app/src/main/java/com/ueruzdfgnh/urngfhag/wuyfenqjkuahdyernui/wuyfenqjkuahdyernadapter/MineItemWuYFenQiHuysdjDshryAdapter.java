package com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernui.wuyfenqjkuahdyernadapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ueruzdfgnh.urngfhag.R;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernmodel.WuYFenQiHuysdjDshryMineItemModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MineItemWuYFenQiHuysdjDshryAdapter extends BaseQuickAdapter<WuYFenQiHuysdjDshryMineItemModel, BaseViewHolder> {

    private ItemClickListener itemClickListener;

    public MineItemWuYFenQiHuysdjDshryAdapter(int layoutResId, @Nullable List<WuYFenQiHuysdjDshryMineItemModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, WuYFenQiHuysdjDshryMineItemModel item) {
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
