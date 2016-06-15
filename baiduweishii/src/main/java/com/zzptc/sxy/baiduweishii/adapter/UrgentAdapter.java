package com.zzptc.sxy.baiduweishii.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zzptc.sxy.baiduweishii.R;
import com.zzptc.sxy.baiduweishii.contant.Contant;

import java.util.List;

/**
 * Created by Administrator on 2016/5/16  0016.
 */
public class UrgentAdapter extends BaseAdapter {
    private List<Contant> list;
    private Context context;

    public UrgentAdapter(List<Contant> list,Context context) {
        this.list = list;
        this.context = context;
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
        View view = LayoutInflater.from(context).inflate(R.layout.list_jinji_lianxiren,parent,false);
        TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        Contant contant = list.get(position);
        tv_phone.setText(contant.getPhone());
        tv_name.setText(contant.getName());
        return view;
    }
}
