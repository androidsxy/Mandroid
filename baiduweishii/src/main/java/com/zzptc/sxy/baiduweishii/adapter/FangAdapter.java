package com.zzptc.sxy.baiduweishii.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzptc.sxy.baiduweishii.R;
import com.zzptc.sxy.baiduweishii.contant.AppProcessInfo;

import java.util.List;

/**
 * Created by SXY on 2016/6/9.
 */
public class FangAdapter extends RecyclerView.Adapter<FangAdapter.MyViewHolder> {

    private List<AppProcessInfo> list;
    private LayoutInflater inflater;
    private static final String SCHEME = "package";
    private Context context;
    public FangAdapter(Context context,List<AppProcessInfo> list){
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_re_tonzhid,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AppProcessInfo contant = list.get(position);
        final String packageName = contant.getPakcageName();
        holder.tv_name.setText(contant.getYinName());
        holder.iv_name.setImageDrawable(contant.getIcon());
        holder.bu_guanli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts(SCHEME, packageName, null);
                intent.setData(uri);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_name;
        TextView tv_name;
        Button bu_guanli;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_name = (ImageView) itemView.findViewById(R.id.iv_name);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            bu_guanli = (Button) itemView.findViewById(R.id.bu_guanli);

        }
    }
}
