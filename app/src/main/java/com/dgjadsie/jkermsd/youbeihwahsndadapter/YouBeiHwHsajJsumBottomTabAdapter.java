package com.dgjadsie.jkermsd.youbeihwahsndadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dgjadsie.jkermsd.R;
import com.dgjadsie.jkermsd.youbeihwahsndactivity.YouBeiHwHsajJsumHomePageActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YouBeiHwHsajJsumBottomTabAdapter extends RecyclerView.Adapter<YouBeiHwHsajJsumBottomTabAdapter.BottomTabHolder> {

    private Context context;

    private List<YouBeiHwHsajJsumHomePageActivity.TabEntity> mList;

    private OnItemClickListener onItemClickListener;

    public YouBeiHwHsajJsumBottomTabAdapter(Context context, List<YouBeiHwHsajJsumHomePageActivity.TabEntity> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public BottomTabHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_bottom_you_bei_he_dje_yrhr_tab, parent, false);
        return new BottomTabHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YouBeiHwHsajJsumBottomTabAdapter.BottomTabHolder holder, int position) {
        YouBeiHwHsajJsumHomePageActivity.TabEntity entity = mList.get(position);
        holder.tabName.setText(entity.getName());
        if (entity.isChecked()){
            Glide.with(context).load(entity.getSelectedIcon()).into(holder.tabImg);
            holder.tabName.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            Glide.with(context).load(entity.getIcon()).into(holder.tabImg);
            holder.tabName.setTextColor(context.getResources().getColor(R.color.white));
        }
        holder.clickView.setOnClickListener(v -> {
            if (!entity.isChecked()){
                entity.setChecked(true);
                for (int i = 0; i < mList.size(); i++){
                    if (i != position){
                        mList.get(i).setChecked(false);
                    }
                }
                if (onItemClickListener != null){
                    onItemClickListener.itemClicked(position);
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class BottomTabHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tab_img)
        ImageView tabImg;
        @BindView(R.id.tab_name)
        TextView tabName;
        @BindView(R.id.click_view)
        View clickView;

        public BottomTabHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void itemClicked(int position);
    }

}