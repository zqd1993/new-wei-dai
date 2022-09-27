package com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrui.rydqhfnerhtradapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rtgjfjgwuett.rugjjdfgurj.R;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrmodel.RYDQHdhtTsdhfrMineItemModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MineItemRYDQHdhtTsdhfrAdapter extends BaseQuickAdapter<RYDQHdhtTsdhfrMineItemModel, BaseViewHolder> {

    private ItemClickListener itemClickListener;

    public MineItemRYDQHdhtTsdhfrAdapter(int layoutResId, @Nullable List<RYDQHdhtTsdhfrMineItemModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, RYDQHdhtTsdhfrMineItemModel item) {
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
