package com.youjiegherh.pocketqwrh.youjiewetdfhui.youjiewetdfhadapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.youjiegherh.pocketqwrh.R;
import com.youjiegherh.pocketqwrh.youjiewetdfhmodel.YouJieSDjdfiMineItemModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MineYouJieSDjdfiItemAdapter extends BaseQuickAdapter<YouJieSDjdfiMineItemModel, BaseViewHolder> {

    private ItemClickListener itemClickListener;

    public MineYouJieSDjdfiItemAdapter(int layoutResId, @Nullable List<YouJieSDjdfiMineItemModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, YouJieSDjdfiMineItemModel item) {
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
