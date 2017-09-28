package com.example.leo.diarynew.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leo.diarynew.R;
import com.example.leo.diarynew.beans.DiaryBean;
import com.example.leo.diarynew.util.LitePalUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by leo on 2017/8/20.
 */

public class CategoryDiaryAdapter extends RecyclerView.Adapter<CategoryDiaryAdapter.DivideViewHolder> {

    private Context context;
    private List<DiaryBean> mData;
    private LayoutInflater ll;
    private int mEditPosition = 0;

    public CategoryDiaryAdapter(Context context , List<DiaryBean> list){
        this.context = context;
        this.mData = list;
        this.ll = LayoutInflater.from(context);
    }

    @Override
    public DivideViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DivideViewHolder(ll.inflate(R.layout.item_divide_diary ,parent ,false));
    }

    @Override
    public void onBindViewHolder(final DivideViewHolder holder, final int position) {
        holder.tv_time.setText(mData.get(position).getDate());
        holder.tv_title.setText(mData.get(position).getTitle());
        holder.tv_content.setText(mData.get(position).getContent());
        if(mEditPosition == position){
            holder.iv_delete.setVisibility(View.VISIBLE);
            holder.iv_edit.setVisibility(View.VISIBLE);
        }else {
            holder.iv_edit.setVisibility(View.GONE);
            holder.iv_delete.setVisibility(View.GONE);
        }
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.iv_delete.getVisibility() == View.VISIBLE){
                    holder.iv_delete.setVisibility(View.GONE);
                    holder.iv_edit.setVisibility(View.GONE);
                }else {
                    holder.iv_delete.setVisibility(View.VISIBLE);
                    holder.iv_edit.setVisibility(View.VISIBLE);
                }
                if(mEditPosition != position){
                    notifyItemChanged(mEditPosition);
                }
                mEditPosition = position;
            }
        });
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("删除该条日记 :(")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LitePalUtil.deleteDataByTitle(mData.get(position).getTitle());
                                mData.remove(mData.get(position));
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("取消" ,null)
                        .show();
            }
        });
        holder.iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(mData.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class DivideViewHolder extends RecyclerView.ViewHolder{

        LinearLayout ll;
        TextView tv_title;
        TextView tv_content;
        TextView tv_time;
        ImageView iv_delete;
        ImageView iv_edit;

        public DivideViewHolder(View itemView) {
            super(itemView);
            ll = (LinearLayout) itemView.findViewById(R.id.ll_divide);
            tv_title = (TextView) itemView.findViewById(R.id.tv_divide_mode_title);
            tv_content = (TextView) itemView.findViewById(R.id.tv_divide_mode_content);
            tv_time = (TextView) itemView.findViewById(R.id.tv_divide_mode_time);
            iv_delete = (ImageView) itemView.findViewById(R.id.tv_divide_mode_delete);
            iv_edit = (ImageView) itemView.findViewById(R.id.tv_divide_mode_edit);
        }
    }
}
