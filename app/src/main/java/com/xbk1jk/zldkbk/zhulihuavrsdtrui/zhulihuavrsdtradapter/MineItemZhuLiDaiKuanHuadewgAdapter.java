package com.xbk1jk.zldkbk.zhulihuavrsdtrui.zhulihuavrsdtradapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xbk1jk.zldkbk.R;
import com.xbk1jk.zldkbk.zhulihuavrsdtrmodel.ZhuLiDaiKuanHuadewgMineItemModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MineItemZhuLiDaiKuanHuadewgAdapter extends BaseQuickAdapter<ZhuLiDaiKuanHuadewgMineItemModel, BaseViewHolder> {

    private ItemClickListener itemClickListener;

    public MineItemZhuLiDaiKuanHuadewgAdapter(int layoutResId, @Nullable List<ZhuLiDaiKuanHuadewgMineItemModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ZhuLiDaiKuanHuadewgMineItemModel item) {
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
