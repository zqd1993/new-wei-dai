package com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsui.wuyoufdghtyfsadapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.R;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsmodel.WuYouJieShdfnFjfuirMineItemModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MineItemWuYouJieShdfnFjfuirAdapter extends BaseQuickAdapter<WuYouJieShdfnFjfuirMineItemModel, BaseViewHolder> {

    private ItemClickListener itemClickListener;

    public MineItemWuYouJieShdfnFjfuirAdapter(int layoutResId, @Nullable List<WuYouJieShdfnFjfuirMineItemModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, WuYouJieShdfnFjfuirMineItemModel item) {
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
