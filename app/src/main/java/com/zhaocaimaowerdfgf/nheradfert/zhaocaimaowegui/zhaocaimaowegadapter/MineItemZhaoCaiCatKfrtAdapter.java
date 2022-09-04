package com.zhaocaimaowerdfgf.nheradfert.zhaocaimaowegui.zhaocaimaowegadapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zhaocaimaowerdfgf.nheradfert.R;
import com.zhaocaimaowerdfgf.nheradfert.zhaocaimaowegmodel.ZhaoCaiCatKfrtMineItemModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MineItemZhaoCaiCatKfrtAdapter extends BaseQuickAdapter<ZhaoCaiCatKfrtMineItemModel, BaseViewHolder> {

    private ItemClickListener itemClickListener;

    public MineItemZhaoCaiCatKfrtAdapter(int layoutResId, @Nullable List<ZhaoCaiCatKfrtMineItemModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ZhaoCaiCatKfrtMineItemModel item) {
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
