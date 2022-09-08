package com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvui.quhuasdenvadapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.R;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvmodel.MineItemBaseQuHuaDjdfuVdhrModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class BaseQuHuaDjdfuVdhrMineItemAdapter extends BaseQuickAdapter<MineItemBaseQuHuaDjdfuVdhrModel, BaseViewHolder> {

    private ItemClickListener itemClickListener;

    public BaseQuHuaDjdfuVdhrMineItemAdapter(int layoutResId, @Nullable List<MineItemBaseQuHuaDjdfuVdhrModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MineItemBaseQuHuaDjdfuVdhrModel item) {
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
