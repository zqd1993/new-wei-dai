package com.asvsdfer.bjirmndf.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.asvsdfer.bjirmndf.model.GoodsModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class GoodsListAdapter extends BaseQuickAdapter<GoodsModel, BaseViewHolder> {

    public GoodsListAdapter(int layoutResId, @Nullable List<GoodsModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, GoodsModel item) {

    }
}
