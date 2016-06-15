package com.zzptc.sxy.baiduweishii.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zzptc.sxy.baiduweishii.R;
import com.zzptc.sxy.baiduweishii.contant.Contant;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/11  0011.
 */
public class ListAdapterMoHu extends BaseAdapter {
    private ArrayList<Contant> list;
    private Context context;

    public ListAdapterMoHu(ArrayList<Contant> list, Context context) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_mohu_add, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
            viewHolder.tv_attri = (TextView) convertView.findViewById(R.id.tv_attri);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Contant contant = list.get(position);
        viewHolder.tv_name.setText(contant.getName());
        viewHolder.tv_phone.setText(contant.getPhone());
        viewHolder.tv_attri.setText(contant.getAttribute());


        return convertView;
    }
    class ViewHolder{
        TextView tv_name;
        TextView tv_phone;
        TextView tv_attri;
    }
}
