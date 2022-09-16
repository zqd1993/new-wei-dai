package com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyui.mjfqidsertyadapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.R;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertymodel.MeiJFenQiDdfgjRfdfgMineItemModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MeiJFenQiDdfgjRfdfgMineItemAdapter extends BaseQuickAdapter<MeiJFenQiDdfgjRfdfgMineItemModel, BaseViewHolder> {

    private ItemClickListener itemClickListener;

    public MeiJFenQiDdfgjRfdfgMineItemAdapter(int layoutResId, @Nullable List<MeiJFenQiDdfgjRfdfgMineItemModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MeiJFenQiDdfgjRfdfgMineItemModel item) {
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
