package com.zzptc.sxy.baiduweishii.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzptc.sxy.baiduweishii.R;
import com.zzptc.sxy.baiduweishii.contant.Contant;

import java.util.List;

/**
 * Created by Administrator on 2016/5/9  0009.
 */
public class ListAdapterxiao extends BaseAdapter {
    private List<Contant> contantlist;
    private Context context;
    private LinearLayout lin;
    public ListAdapterxiao(List<Contant> contantlist,Context context ,LinearLayout lin){
        this.context = context;
        this.contantlist = contantlist;
        this.lin = lin;
    }



    @Override
    public int getCount() {
        return contantlist.size();
    }

    @Override
    public Object getItem(int position) {
        return contantlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_haha_androm,parent,false);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.text_name_haha);
            viewHolder.tv_phone = (TextView) convertView.findViewById(R.id.text_phone_haha);
            viewHolder.image_puchu = (ImageView) convertView.findViewById(R.id.image_puchu);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Contant contant = contantlist.get(position);

        viewHolder.tv_name.setText(contant.getName());
        viewHolder.tv_phone.setText(contant.getPhone());
        viewHolder.image_puchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contantlist.remove(position);
                notifyDataSetChanged();
                //条目小于3就显示输入框
                if(contantlist.size()<3){
                    lin.setVisibility(View.VISIBLE);
                }
            }
        });

        return convertView;
    }
    class ViewHolder{
        TextView tv_name;
        TextView tv_phone;
        ImageView image_puchu;
    } 
}
