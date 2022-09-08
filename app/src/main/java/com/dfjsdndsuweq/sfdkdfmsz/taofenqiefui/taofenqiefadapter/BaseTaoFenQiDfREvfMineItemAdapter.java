package com.dfjsdndsuweq.sfdkdfmsz.taofenqiefui.taofenqiefadapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dfjsdndsuweq.sfdkdfmsz.R;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefmodel.MineItemBaseTaoFenQiDfREvfModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class BaseTaoFenQiDfREvfMineItemAdapter extends BaseQuickAdapter<MineItemBaseTaoFenQiDfREvfModel, BaseViewHolder> {

    private ItemClickListener itemClickListener;

    public BaseTaoFenQiDfREvfMineItemAdapter(int layoutResId, @Nullable List<MineItemBaseTaoFenQiDfREvfModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MineItemBaseTaoFenQiDfREvfModel item) {
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
