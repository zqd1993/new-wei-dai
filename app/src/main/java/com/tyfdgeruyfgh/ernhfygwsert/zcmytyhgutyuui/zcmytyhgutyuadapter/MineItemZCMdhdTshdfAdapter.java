package com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuui.zcmytyhgutyuadapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tyfdgeruyfgh.ernhfygwsert.R;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyumodel.ZCMdhdTshdfMineItemModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MineItemZCMdhdTshdfAdapter extends BaseQuickAdapter<ZCMdhdTshdfMineItemModel, BaseViewHolder> {

    private ItemClickListener itemClickListener;

    public MineItemZCMdhdTshdfAdapter(int layoutResId, @Nullable List<ZCMdhdTshdfMineItemModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ZCMdhdTshdfMineItemModel item) {
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
