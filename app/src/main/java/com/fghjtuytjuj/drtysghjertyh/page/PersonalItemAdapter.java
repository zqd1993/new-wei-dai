package com.fghjtuytjuj.drtysghjertyh.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fjsdkqwj.pfdioewjnsd.R;

import java.util.List;

public class PersonalItemAdapter extends RecyclerView.Adapter<PersonalItemAdapter.PersonalItemHolder> {

    private Context context;

    private List<PersonalCenterFragment.PersonalCenterEntity> mList;

    private OnItemClickListener onItemClickListener;

    public PersonalItemAdapter(Context context, List<PersonalCenterFragment.PersonalCenterEntity> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public PersonalItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapterpersonal_center_item_layout, parent, false);
        return new PersonalItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonalItemAdapter.PersonalItemHolder holder, int position) {
        PersonalCenterFragment.PersonalCenterEntity entity = mList.get(position);
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

    public class PersonalItemHolder extends RecyclerView.ViewHolder{

        ImageView setImg;
        TextView setTv;
        View clickView;

        public PersonalItemHolder(@NonNull View itemView) {
            super(itemView);
            setImg = itemView.findViewById(R.id.set_img);
            setTv = itemView.findViewById(R.id.set_tv);
            clickView = itemView.findViewById(R.id.click_view);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void itemClicked(int position);
    }

}
