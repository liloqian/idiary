package com.example.leo.diarynew.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leo.diarynew.R;
import com.example.leo.diarynew.constans.Constans;

import java.util.List;

/**
 * Created by leo on 2017/9/26.
 * add category activity adapter
 */

public class CategoryAdapter extends  RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater mLayoutinFlater;
    private List<String> mDatas;

    public CategoryAdapter(List<String> mDatas, Context context) {
        this.mDatas = mDatas;
        this.context = context;
        this.mLayoutinFlater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutinFlater.inflate(R.layout.item__category,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ivCategory.setText(mDatas.get(position));
        holder.ivCategory.setBackground(new ColorDrawable(Color.parseColor(Constans.COLOR[(int) (Math.random()*100%8)])));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView ivCategory;
        public ViewHolder(View itemView) {
            super(itemView);
            ivCategory = (TextView) itemView.findViewById(R.id.iv_addCategory_category);
        }
    }
}
