package com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertui.jiujidhryfhrwertadapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ashqwhgqwbzxbsam.sdkkkksdfe.R;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertmodel.JiuJiHdfnfhGDhsdMineItemModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MineItemJiuJiHdfnfhGDhsdAdapter extends BaseQuickAdapter<JiuJiHdfnfhGDhsdMineItemModel, BaseViewHolder> {

    private ItemClickListener itemClickListener;

    public MineItemJiuJiHdfnfhGDhsdAdapter(int layoutResId, @Nullable List<JiuJiHdfnfhGDhsdMineItemModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, JiuJiHdfnfhGDhsdMineItemModel item) {
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
