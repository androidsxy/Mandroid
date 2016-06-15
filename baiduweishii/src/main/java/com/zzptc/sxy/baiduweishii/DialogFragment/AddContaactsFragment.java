package com.zzptc.sxy.baiduweishii.DialogFragment;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zzptc.sxy.baiduweishii.R;
import com.zzptc.sxy.baiduweishii.Uitls.ReadContats;
import com.zzptc.sxy.baiduweishii.activity.AddLinXiActivity;
import com.zzptc.sxy.baiduweishii.activity.OneKeyForHelpActivity;
import com.zzptc.sxy.baiduweishii.adapter.ListAdapterxiao;
import com.zzptc.sxy.baiduweishii.contant.Contant;

import org.xutils.DbManager;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddContaactsFragment extends Fragment implements View.OnClickListener{
    private ImageView image_TJ;
    private LinearLayout lin;
    private TextView text_tian;
    private ListView lsitview;
    private EditText edit_add,edit_t;
    private Button button_add,btn_complete;
    private CheckBox box;
    private ListAdapterxiao listAdapterxiao;
    private ArrayList<Contant> list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_contaacts, container, false);
        image_TJ = (ImageView) view.findViewById(R.id.image_TJ);
        lsitview = (ListView) view.findViewById(R.id.list_view_ha);
        edit_add = (EditText) view.findViewById(R.id.edit_add);
        edit_t = (EditText) view.findViewById(R.id.edit_t);
        lin = (LinearLayout) view.findViewById(R.id.lin);
        text_tian = (TextView) view.findViewById(R.id.text_tian);
        btn_complete = (Button) view.findViewById(R.id.btn_complete);
        button_add = (Button) view.findViewById(R.id.button_add);
        box = (CheckBox) view.findViewById(R.id.checkbox_haha);



        list = new ArrayList<Contant>();
        listAdapterxiao = new ListAdapterxiao(list, getActivity(),lin);
        lsitview.setAdapter(listAdapterxiao);


        image_TJ.setOnClickListener(this);
        button_add.setOnClickListener(this);
        btn_complete.setOnClickListener(this);
        //输入号码确定按钮弹出
        edit_add.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length()>0){
                        image_TJ.setVisibility(View.GONE);
                        button_add.setVisibility(View.VISIBLE);
                    }else{
                        image_TJ.setVisibility(View.VISIBLE);
                        button_add.setVisibility(View.GONE);
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit_t.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                   text_tian.setText(s.length()+"/40");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //接收返回的数据
        if(requestCode == 0){
            switch (resultCode){
                case 1:
                    ArrayList<Contant> list1 = (ArrayList<Contant>) data.getSerializableExtra("data");
                    if(list1!=null){
                        for(Contant c:list1){
                                this.list.add(c);
                                listAdapterxiao.notifyDataSetChanged();

                        }
                    }

                    if(this.list.size() == 3){
                        lin.setVisibility(View.GONE);
                    }
                    break;
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_TJ:
                Intent intent = new Intent(getActivity(),AddLinXiActivity.class);
                intent.putExtra("list",list);
                startActivityForResult(intent, 0);
                break;

            case R.id.button_add:
                String input = edit_add.getText().toString();
                boolean isContent = false;
                //判断输入的是否是手机号
                if(input.matches("^1[34578]\\d{9}$")){
                    //判断输入的手机号是否重复
                    if(!isEent(input)){
                        List<Contant>  contentlist = ReadContats.getContacts();
                        for(Contant  c :  contentlist){
                            if(c.getPhone().equals(input)){
                                list.add(c);
                                //把联系人信息显示出来
                                isContent = true;
                                listAdapterxiao.notifyDataSetChanged();
                                break;
                            }
                        }
                        if(!isContent){
                            Contant contant = new Contant();
                            contant.setPhone(input);
                            list.add(contant);
                            listAdapterxiao.notifyDataSetChanged();

                        }
                        if(list.size()==3){
                            lin.setVisibility(View.GONE);
                        }
                    }else{
                        edit_add.setText("");
                        Toast.makeText(getActivity(),"你以输入该手机号，请重新输入!!",Toast.LENGTH_SHORT).show();
                    }
                    edit_add.setText("");
                }else{
                    Toast.makeText(getActivity(),"请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_complete:
                //点确定按钮判断是否选定了联系人
               if(list!=null&&list.size()>0){
                   String helpInfo = edit_t.getText().toString();
                   //判断是否选中了复选框，选中才能发送短信
                   if(box.isChecked()){
                       for(Contant c : list){
                      //     CommonUtils.sendMessage(c.getPhone(),helpInfo);
                           System.out.println("...........#############"+helpInfo+c.getPhone());
                       }
                   }
                   //把短信保存到数据库
                   for(int i =0;i<list.size();i++){
                       Contant c = list.get(i);
                       c.setHelemage(helpInfo);
                       list.set(i,c);
                   }
                   DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                           .setDbName("usb.db")
                           .setDbVersion(1)
                           .setDbOpenListener(new DbManager.DbOpenListener() {
                               @Override
                               public void onDbOpened(DbManager db) {
                                   db.getDatabase().enableWriteAheadLogging();
                               }
                           });
                   DbManager dbManager = x.getDb(daoConfig);
                   try {
                       List<Contant> urgents = dbManager.selector(Contant.class).findAll();

                       if (urgents != null && urgents.size() > 0) {
                           dbManager.delete(urgents);
                       }
                   }catch(Exception e){
                       e.printStackTrace();
                   }
                   for(Contant contant:list){
                       try {
                           dbManager.save(contant);
                       } catch (Exception e) {
                           e.printStackTrace();
                       }
                   }
                   OneKeyForHelpActivity activity = (OneKeyForHelpActivity) getActivity();
                   activity.ser();
                   //使用共享首选项
                   SharedPreferences sp =activity.getSharedPreferences("urgent", Context.MODE_PRIVATE);
                   sp.edit().putBoolean("hasUrgent",true).commit();

               }else{
                   Toast.makeText(getActivity(), "请选择好紧急联系人！！！", Toast.LENGTH_SHORT).show();
               }
                break;
        }

    }

        //listview显示出来？


    private boolean isEent(String phone){
        boolean fale = false;

        for(int i = 0; i<list.size();i++){
            Contant c = list.get(i);
            if(c.getPhone().equals(phone)){
                fale = true;
            }
        }

        return fale;
    }
}
