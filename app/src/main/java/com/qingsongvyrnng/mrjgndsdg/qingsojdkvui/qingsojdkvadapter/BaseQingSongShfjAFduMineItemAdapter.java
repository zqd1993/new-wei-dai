package com.qingsongvyrnng.mrjgndsdg.qingsojdkvui.qingsojdkvadapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qingsongvyrnng.mrjgndsdg.R;
import com.qingsongvyrnng.mrjgndsdg.qingsojdkvmodel.MineItemBaseQingSongShfjAFduModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class BaseQingSongShfjAFduMineItemAdapter extends BaseQuickAdapter<MineItemBaseQingSongShfjAFduModel, BaseViewHolder> {

    private ItemClickListener itemClickListener;

    public BaseQingSongShfjAFduMineItemAdapter(int layoutResId, @Nullable List<MineItemBaseQingSongShfjAFduModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MineItemBaseQingSongShfjAFduModel item) {
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
