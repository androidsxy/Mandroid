package com.zzptc.sxy.baiduweishii.DialogFragment;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.zzptc.sxy.baiduweishii.R;
import com.zzptc.sxy.baiduweishii.Uitls.CommonUtils;
import com.zzptc.sxy.baiduweishii.Uitls.MemoryCleanTools;
import com.zzptc.sxy.baiduweishii.activity.PhoneJiaSuActivity;
import com.zzptc.sxy.baiduweishii.adapter.MyAdapter;
import com.zzptc.sxy.baiduweishii.contant.AppProcessInfo;
import com.zzptc.sxy.baiduweishii.contant.Contant;

import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;

/**
 * Created by SXY on 2016/5/29.
 */
public class YingYongFragmentDialog extends DialogFragment {
    private TextView tv_add;
    private String packageName;
    private static final String SCHEME = "package";


    public  static YingYongFragmentDialog newInstance(AppProcessInfo contant,int position){
        YingYongFragmentDialog dialog = new YingYongFragmentDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable("contant", contant);
        bundle.putInt("position",position);
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_dialog_yonghu,container,false);
        Button but_quxiao = (Button) view.findViewById(R.id.but_quxiao);
        Button but_qingli = (Button) view.findViewById(R.id.but_qingli);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        TextView tv_leichun = (TextView) view.findViewById(R.id.tv_leichun);
        TextView tv_yinxianqi = (TextView) view.findViewById(R.id.tv_yinxianqi);
        tv_add = (TextView) view.findViewById(R.id.tv_add);
        final AppProcessInfo contant = getArguments().getParcelable("contant");
        if(contant!=null) {

            packageName = contant.getPakcageName();
            tv_name.setText(contant.getYinName());
            tv_leichun.setText("暂用内存" + contant.getDaxiao() / 1024 + "mb");
            if (contant.isChecked) {
                addWhiteList();
            } else {
                removeWhiteList();
            }
            tv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PhoneJiaSuActivity phoneJiaSuActivity = (PhoneJiaSuActivity) getActivity();
                    long contIms = phoneJiaSuActivity.getContIms();
                    MyAdapter adapter = phoneJiaSuActivity.getJianCheAdapter();
                    DbManager dbManager = phoneJiaSuActivity.getDbManager();

                    Button btn_clean = (Button) phoneJiaSuActivity.findViewById(R.id.btn_clean);

                    AppProcessInfo contant = getArguments().getParcelable("contant");

                    try {
                        if(contant.isChecked){
                            dbManager.save(contant);
                            removeWhiteList();
                            contant.isChecked = false;
                            contIms = contIms - contant.getDaxiao();

                        }else{
                            dbManager.delete(AppProcessInfo.class, WhereBuilder.b("yinName", "=", contant.getYinName()).and("pakcageName", "=", contant.getPakcageName()));
                            addWhiteList();
                            contant.isChecked = true;
                            contIms= contIms + contant.getDaxiao();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    phoneJiaSuActivity.setContIms(contIms);

                    btn_clean.setText("一键加速 " + CommonUtils.convertStorage(contIms));

                    adapter.notifyDataSetChanged();
                }
            });
            but_quxiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            tv_yinxianqi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    YingYongFragmentDialog.showInstalledAppDetails(getActivity(), packageName);
                }
            });
           //清理内存
            but_qingli.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getArguments().getInt("position");
                    PhoneJiaSuActivity phoneJiaSuActivity = (PhoneJiaSuActivity) getActivity();
                    long contIms = phoneJiaSuActivity.getContIms();
                    MyAdapter adapter = phoneJiaSuActivity.getJianCheAdapter();
                    adapter.removeItem(position);
                    AppProcessInfo contant = getArguments().getParcelable("contant");
                    MemoryCleanTools memoryCleanTools = new MemoryCleanTools();
                    memoryCleanTools.killBackGroundProcess(contant.getPakcageName());

                    if(contant.isChecked){
                        contIms = contIms - contant.getDaxiao();
                        Button bu_yijian = (Button) phoneJiaSuActivity.findViewById(R.id.btn_clean);
                        bu_yijian.setText("一键清理"+CommonUtils.convertStorage(contIms));
                    }
                    int seiz = phoneJiaSuActivity.getSeiz();
                    TextView tv_jishu = (TextView) phoneJiaSuActivity.findViewById(R.id.tv_count);
                    tv_jishu.setText("正在运行的进程（"+seiz+")");
                    dismiss();
                }
            });
            getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        dismiss();
                    }
                    return false;
                }
            });
        }
        return view;
    }
    public  static void showInstalledAppDetails(Context context,String packageName) {
        Intent intent = new Intent();

            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts(SCHEME, packageName, null);
            intent.setData(uri);

        context.startActivity(intent);
    }
    //加入白名单
    private void addWhiteList(){

        Drawable drawableLeft = ContextCompat.getDrawable(getActivity(), R.mipmap.add_white_list);
        drawableLeft.setBounds(0,0,drawableLeft.getMinimumWidth(),drawableLeft.getMinimumHeight());

        tv_add.setCompoundDrawables(drawableLeft,null,null,null);
        tv_add.setText("加入白名单");
    }

    //移除白名单
    private void removeWhiteList(){
        Drawable drawableLeft = ContextCompat.getDrawable(getActivity(), R.mipmap.remove_white_list);
        drawableLeft.setBounds(0,0,drawableLeft.getMinimumWidth(),drawableLeft.getMinimumHeight());

        tv_add.setCompoundDrawables(drawableLeft, null, null, null);
        tv_add.setText("移除白名单");
    }
}
