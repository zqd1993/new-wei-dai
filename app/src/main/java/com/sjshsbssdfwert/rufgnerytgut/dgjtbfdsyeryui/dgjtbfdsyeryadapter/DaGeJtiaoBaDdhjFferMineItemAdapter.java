package com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryui.dgjtbfdsyeryadapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sjshsbssdfwert.rufgnerytgut.R;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyerymodel.DaGeJtiaoBaDdhjFferMineItemModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class DaGeJtiaoBaDdhjFferMineItemAdapter extends BaseQuickAdapter<DaGeJtiaoBaDdhjFferMineItemModel, BaseViewHolder> {

    private ItemClickListener itemClickListener;

    public DaGeJtiaoBaDdhjFferMineItemAdapter(int layoutResId, @Nullable List<DaGeJtiaoBaDdhjFferMineItemModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DaGeJtiaoBaDdhjFferMineItemModel item) {
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
