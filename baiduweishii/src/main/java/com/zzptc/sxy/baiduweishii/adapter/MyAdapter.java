package com.zzptc.sxy.baiduweishii.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


import com.zzptc.sxy.baiduweishii.R;
import com.zzptc.sxy.baiduweishii.Uitls.CommonUtils;
import com.zzptc.sxy.baiduweishii.contant.AppProcessInfo;



import java.util.List;


/**
 * Created by Administrator on 2016/5/18  0018.
 */


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    long contants = 0;
    private List<AppProcessInfo> lists;
    private LayoutInflater inflater;
    private Context context;
    private OnRecyclerViewItemClickListener mOnItemClickListener;




    public MyAdapter(List<AppProcessInfo> lists, Context context) {
        this.lists = lists;
        this.context = context;
        inflater = LayoutInflater.from(context);
        //保存复选框的状态
      //  contentInmt = new HashMap<>();
      //  for(int position = 0; position <lists.size() ; position++){
      //      contentInmt.put(position,false);
      //  }
    }

    //define interface
    public  interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , int position);
        void onCheckBoxChangedListener(View view,int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = inflater.inflate(R.layout.list_haha_jingche, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        AppProcessInfo contant = lists.get(position);
        holder.te_view.setText(contant.getYinName());
        holder.te_daxiao.setText(CommonUtils.convertStorage(contant.getDaxiao()));
        holder.imageView.setImageDrawable(contant.getIcon());

        if(contant.isChecked){
            holder.che_jingchen.setChecked(true);
        }else{
            holder.che_jingchen.setChecked(false);
        }

        if(mOnItemClickListener!=null){


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.imageView,position);
            }
        });
        holder.che_jingchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onCheckBoxChangedListener(holder.che_jingchen,position);
            }
        });
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView te_view;
        TextView te_daxiao;
        ImageView imageView;
        CheckBox che_jingchen;

        public MyViewHolder(View itemView) {
            super(itemView);
            te_view = (TextView) itemView.findViewById(R.id.te_view);
            te_daxiao = (TextView) itemView.findViewById(R.id.te_daxiao);
            imageView = (ImageView) itemView.findViewById(R.id.image_jincheng);
            che_jingchen = (CheckBox) itemView.findViewById(R.id.che_jingchen);

        }
    }
    public void removeItem(int position){
        lists.remove(position);
        notifyItemRemoved(position);
    }

}