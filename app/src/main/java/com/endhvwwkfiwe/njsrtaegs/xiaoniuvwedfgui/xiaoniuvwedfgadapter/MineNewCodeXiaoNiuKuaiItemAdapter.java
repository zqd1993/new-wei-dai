package com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgui.xiaoniuvwedfgadapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.endhvwwkfiwe.njsrtaegs.R;
import com.endhvwwkfiwe.njsrtaegs.xiaoniuvwedfgmodel.NewCodeXiaoNiuKuaiMineItemModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MineNewCodeXiaoNiuKuaiItemAdapter extends BaseQuickAdapter<NewCodeXiaoNiuKuaiMineItemModel, BaseViewHolder> {

    private ItemClickListener itemClickListener;

    public MineNewCodeXiaoNiuKuaiItemAdapter(int layoutResId, @Nullable List<NewCodeXiaoNiuKuaiMineItemModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, NewCodeXiaoNiuKuaiMineItemModel item) {
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
