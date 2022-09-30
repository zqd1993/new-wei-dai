package com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.R;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgentity.PersonalCenterHaojjQianShsjHajduEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetItemHaojjQianShsjHajduAdapter extends RecyclerView.Adapter<SetItemHaojjQianShsjHajduAdapter.BottomTabHolder> {

    private Context context;

    private List<PersonalCenterHaojjQianShsjHajduEntity> mList;

    private OnItemClickListener onItemClickListener;

    public SetItemHaojjQianShsjHajduAdapter(Context context, List<PersonalCenterHaojjQianShsjHajduEntity> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public BottomTabHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_set_item_layout_hao_jie_she_qtdhfery, parent, false);
        return new BottomTabHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SetItemHaojjQianShsjHajduAdapter.BottomTabHolder holder, int position) {
        PersonalCenterHaojjQianShsjHajduEntity entity = mList.get(position);
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
