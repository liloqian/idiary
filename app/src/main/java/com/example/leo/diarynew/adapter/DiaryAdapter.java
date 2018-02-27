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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.leo.diarynew.R;
import com.example.leo.diarynew.beans.DiaryBean;
import com.example.leo.diarynew.util.GetTimeUtil;
import com.example.leo.diarynew.util.LitePalUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by leo on 2017/8/19.
 */

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {
    private Context mContext;
    private List<DiaryBean> mDataList;
    private LayoutInflater mLayoutInFlater;
    private int mEditPosition = -1;

    public DiaryAdapter(Context context , List<DiaryBean> list) {
        this.mContext = context;
        this.mDataList = list;
        this.mLayoutInFlater = LayoutInflater.from(context);
    }

    @Override
    public DiaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DiaryViewHolder(mLayoutInFlater.inflate(R.layout.item_rv_diary,parent,false));
    }

    @Override
    public void onBindViewHolder(final DiaryViewHolder holder, final int position) {
        String currentTimt = GetTimeUtil.getData();
        if(currentTimt.equals(mDataList.get(position).getDate())){
            holder.mIvCircle.setImageResource(R.drawable.circle_orange);
        }
        holder.mTvDate.setText(mDataList.get(position).getDate());
        holder.mTvTitle.setText(mDataList.get(position).getTitle());
        holder.mTvContent.setText("     " + mDataList.get(position).getContent());
        holder.mIvEdit.setVisibility(View.INVISIBLE);
        if ( mEditPosition == position ){
            holder.mIvEdit.setVisibility(View.VISIBLE);
            holder.mIvDelete.setVisibility(View.VISIBLE);
        }else {
            holder.mIvEdit.setVisibility(View.GONE);
            holder.mIvDelete.setVisibility(View.GONE);
        }
        holder.mLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.mIvEdit.getVisibility() == View.VISIBLE){
                    holder.mIvEdit.setVisibility(View.GONE);
                    holder.mIvDelete.setVisibility(View.GONE);
                }else {
                    holder.mIvEdit.setVisibility(View.VISIBLE);
                    holder.mIvDelete.setVisibility(View.VISIBLE);
                }
                if(mEditPosition != position){
                    notifyItemChanged(mEditPosition);
                }
                mEditPosition = position;
            }
        });
//        holder.mLl.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                CheckActivity.startActivity(mContext,mDataList.get(position).getTitle());
//                return false;
//            }
//        });
        holder.mIvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(mContext,SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("删除")
                        .setContentText("您确定要删除这条日记吗？")
                        .setConfirmText("确定删除")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                LitePalUtil.deleteDataByTitle(mDataList.get(position).getTitle());
                                mDataList.remove(mDataList.get(position));
                                notifyDataSetChanged();
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .setCancelText("取消删除")
                        .show();
            }
        });
        holder.mIvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(mDataList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    public static class DiaryViewHolder extends RecyclerView.ViewHolder{
        TextView mTvDate;
        TextView mTvTitle;
        TextView mTvContent;
        ImageView mIvEdit;
        LinearLayout mLlTitle;
        LinearLayout mLl;
        ImageView mIvCircle;
        LinearLayout mLlControl;
        RelativeLayout mRlEdit;
        ImageView mIvDelete;

        public DiaryViewHolder(View view) {
            super(view);
            mIvCircle = (ImageView) view.findViewById(R.id.main_iv_circle);
            mTvDate = (TextView) view.findViewById(R.id.main_tv_date);
            mTvTitle = (TextView) view.findViewById(R.id.main_tv_title);
            mTvContent = (TextView) view.findViewById(R.id.main_tv_content);
            mIvEdit = (ImageView) view.findViewById(R.id.main_iv_edit);
            mLlTitle = (LinearLayout) view.findViewById(R.id.main_ll_title);
            mLl = (LinearLayout) view.findViewById(R.id.item_ll);
            mLlControl = (LinearLayout) view.findViewById(R.id.item_ll_control);
            mRlEdit = (RelativeLayout) view.findViewById(R.id.item_rl_edit);
            mIvDelete = (ImageView) view.findViewById(R.id.main_iv_delete);
        }
    }

}
