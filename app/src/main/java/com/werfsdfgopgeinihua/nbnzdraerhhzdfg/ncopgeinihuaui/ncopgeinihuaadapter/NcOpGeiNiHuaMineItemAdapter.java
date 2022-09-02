package com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuaui.ncopgeinihuaadapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.R;
import com.werfsdfgopgeinihua.nbnzdraerhhzdfg.ncopgeinihuamodel.MineItemNcOpGeiNiHuaModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class NcOpGeiNiHuaMineItemAdapter extends BaseQuickAdapter<MineItemNcOpGeiNiHuaModel, BaseViewHolder> {

    private ItemClickListener itemClickListener;

    public NcOpGeiNiHuaMineItemAdapter(int layoutResId, @Nullable List<MineItemNcOpGeiNiHuaModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MineItemNcOpGeiNiHuaModel item) {
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
