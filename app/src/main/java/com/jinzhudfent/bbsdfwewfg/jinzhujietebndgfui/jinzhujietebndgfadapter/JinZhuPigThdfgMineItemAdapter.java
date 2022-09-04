package com.jinzhudfent.bbsdfwewfg.jinzhujietebndgfui.jinzhujietebndgfadapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jinzhudfent.bbsdfwewfg.R;
import com.jinzhudfent.bbsdfwewfg.jinzhujietebndgfmodel.JinZhuPigThdfgMineItemModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class JinZhuPigThdfgMineItemAdapter extends BaseQuickAdapter<JinZhuPigThdfgMineItemModel, BaseViewHolder> {

    private ItemClickListener itemClickListener;

    public JinZhuPigThdfgMineItemAdapter(int layoutResId, @Nullable List<JinZhuPigThdfgMineItemModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, JinZhuPigThdfgMineItemModel item) {
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
