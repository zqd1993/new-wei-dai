package com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteui.weifenqjtsdteadapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sdldsjqwbaszbdskdf.dfpddfgert.R;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdtemodel.RongjieSfFgdfMineItemModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class WeiFenQiadsfSsdMineItemAdapter extends BaseQuickAdapter<RongjieSfFgdfMineItemModel, BaseViewHolder> {

    private ItemClickListener itemClickListener;

    public WeiFenQiadsfSsdMineItemAdapter(int layoutResId, @Nullable List<RongjieSfFgdfMineItemModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, RongjieSfFgdfMineItemModel item) {
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
