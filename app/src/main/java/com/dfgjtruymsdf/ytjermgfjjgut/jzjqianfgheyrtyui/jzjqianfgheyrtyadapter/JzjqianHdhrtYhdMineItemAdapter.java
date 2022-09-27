package com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyui.jzjqianfgheyrtyadapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dfgjtruymsdf.ytjermgfjjgut.R;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtymodel.JzjqianHdhrtYhdMineItemModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class JzjqianHdhrtYhdMineItemAdapter extends BaseQuickAdapter<JzjqianHdhrtYhdMineItemModel, BaseViewHolder> {

    private ItemClickListener itemClickListener;

    public JzjqianHdhrtYhdMineItemAdapter(int layoutResId, @Nullable List<JzjqianHdhrtYhdMineItemModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, JzjqianHdhrtYhdMineItemModel item) {
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
