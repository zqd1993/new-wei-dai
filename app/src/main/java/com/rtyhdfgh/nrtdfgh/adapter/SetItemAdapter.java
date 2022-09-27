package com.rtyhdfgh.nrtdfgh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rtyhdfgh.nrtdfgh.R;
import com.rtyhdfgh.nrtdfgh.entity.PersonalCenterEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetItemAdapter extends RecyclerView.Adapter<SetItemAdapter.BottomTabHolder> {

    private Context context;

    private List<PersonalCenterEntity> mList;

    private OnItemClickListener onItemClickListener;

    public SetItemAdapter(Context context, List<PersonalCenterEntity> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public BottomTabHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_set_item_layout, parent, false);
        return new BottomTabHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SetItemAdapter.BottomTabHolder holder, int position) {
        PersonalCenterEntity entity = mList.get(position);
        holder.setTv.setText(entity.getItemTitle());
        Glide.with(context).load(entity.getImgRes()).into(holder.setImg);
        holder.clickView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.itemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class BottomTabHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.set_img)
        ImageView setImg;
        @BindView(R.id.set_tv)
        TextView setTv;
        @BindView(R.id.click_view)
        View clickView;

        public BottomTabHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void itemClicked(int position);
    }

}
