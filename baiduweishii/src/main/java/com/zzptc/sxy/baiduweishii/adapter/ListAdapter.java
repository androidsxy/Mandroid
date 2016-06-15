package com.zzptc.sxy.baiduweishii.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.zzptc.sxy.baiduweishii.R;
import com.zzptc.sxy.baiduweishii.contant.Contant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/1  0001.
 */
public class ListAdapter extends BaseAdapter {
    private List<Contant> list;
    private Context context;
    private TextView text_xuanzhe;
    private  TextView queding;
    private ArrayList<Contant>  contants;
    private Map<Integer,Boolean> contentInmt;
    private int contents = 0;

    public ListAdapter(List<Contant> list,Context context , TextView text_xuanzhe,TextView queding,ArrayList<Contant> contants){
        this.list = list;
        this.context = context;
        this.text_xuanzhe = text_xuanzhe;
        this.queding = queding;
        this.contants = contants;
        //保存复选框的状态
        contentInmt = new HashMap<>();
        for(int position = 0; position < list.size() ; position++){
            contentInmt.put(position,false);
        }
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_xuanzhe,parent,false);

            //convertView 上的控件  ==》 ViewHolder  200%
            viewHolder = new ViewHolder();
            viewHolder.imageView = (TextView) convertView.findViewById(R.id.text_view);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.text_name_xuan);
            viewHolder.tv_phone = (TextView) convertView.findViewById(R.id.text_phone_xuan);
            viewHolder.tv_attri = (TextView) convertView.findViewById(R.id.text_shen);
            viewHolder.cb_checked = (CheckBox) convertView.findViewById(R.id.check);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }


        final Contant contant = list.get(position);

            viewHolder.tv_name.setText(contant.getName());
            viewHolder.tv_phone.setText(contant.getPhone());
            viewHolder.tv_attri.setText(contant.getAttribute());
            viewHolder.imageView.setText(contant.getName().substring(contant.getName().length() - 1));
            viewHolder.cb_checked.setChecked(contentInmt.get(position));
        final ViewHolder finalViewHolder = viewHolder;

        viewHolder.cb_checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(!contant.getAttribute().equals("非手机号码")) {
                        //标记选中的复选框
                        if (finalViewHolder.cb_checked.isChecked()) {
                        //添加页面的是否重复判断
                        if(!ieEent(contant.getPhone())){

                                //最多只能选三个
                                if (contants.size()<3) {
                                    contentInmt.put(position, true);
                                    contants.add(contant);
                                    // contents++;
                                }else {
                                    Toast.makeText(context, "你以选择了三个紧急联系人", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                            Toast.makeText(context, "你以选择了该联系人！！", Toast.LENGTH_SHORT).show();
                        }
                        }
                     if(!finalViewHolder.cb_checked.isChecked()){
                         contentInmt.put(position, false);
                         contants.remove(contant);
                     }
                    }else{
                        Toast.makeText(context, "请选择手机号码", Toast.LENGTH_SHORT).show();
                    }
                text_xuanzhe.setText("选择联系人（"+contants.size() +"）");
                if(contants.size()>0){
                    queding.setTextColor(Color.WHITE);
                    queding.setClickable(true);
                }else{
                    queding.setTextColor(Color.GRAY);
                    queding.setClickable(false);
                }
          //通知适配器发生改变
                notifyDataSetChanged();
            }
        });

        return  convertView;
    }
    class ViewHolder{
        TextView imageView;
        TextView tv_name;
        TextView tv_phone;
        TextView tv_attri;
        CheckBox cb_checked;
    }
    public Map<Integer, Boolean> getCheckedItems() {
        return contentInmt;
    }
    public ArrayList<Contant> getContants(){
        return contants;
    }
    public boolean ieEent(String phone){
        boolean fale = false;
        for(int i =0;i<contants.size();i++){
            Contant c = contants.get(i);
            if(c.getPhone().equals(phone)){
                fale = true;
                break;
            }
        }
        return fale;
    }

}
