package com.zzptc.sxy.baiduweishii.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zzptc.sxy.baiduweishii.R;
import com.zzptc.sxy.baiduweishii.Uitls.ReadContats;
import com.zzptc.sxy.baiduweishii.adapter.ListAdapter;
import com.zzptc.sxy.baiduweishii.adapter.ListAdapterMoHu;
import com.zzptc.sxy.baiduweishii.contant.Contant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddLinXiActivity extends AppCompatActivity implements View.OnClickListener{
    private ListView listView,listView1;
    private List<Contant> list;
    private EditText editText;
    private ArrayList<Contant>  contantList;
    private ImageView image_XZ;
    private TextView xuanzhe;
    private TextView queding;
    private ListAdapterMoHu listAdapterMoHu;
    private ListAdapter listAdapter;
    private  ArrayList<Contant> contants;
    private  int contentInt=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lin_xi);

        Intent intent = getIntent();
        contants  = (ArrayList<Contant>) intent.getSerializableExtra("list");





        listView1 = (ListView) findViewById(R.id.lv_result);
        listView = (ListView) findViewById(R.id.list_xZ);
        image_XZ = (ImageView) findViewById(R.id.image_XZ);
        xuanzhe = (TextView) findViewById(R.id.text_xuanzei);
        queding = (TextView) findViewById(R.id.text_quding);
        editText = (EditText) findViewById(R.id.edit_add_mohu);
        ser();
        contantList = new ArrayList<Contant>();
        listAdapterMoHu =new ListAdapterMoHu(contantList,this);
        listView1.setAdapter(listAdapterMoHu);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    listView.setVisibility(View.GONE);
                    listView1.setVisibility(View.VISIBLE);
                    ser(s.toString());
                } else {
                    listView.setVisibility(View.VISIBLE);
                    listView1.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                contentInt = 1;
                listView1.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                //跳转到指定listview位置
                Contant c = contantList.get(position);
                int checkedPostion = getPosition(c.getPhone());
                listView.smoothScrollToPosition(checkedPostion + 4);
                //用户选择条目，修改数字
                if(!listAdapter.ieEent(c.getPhone())){
                    if(!c.getAttribute().equals("座机号码")){
                        Map<Integer, Boolean> checkedItems = listAdapter.getCheckedItems();
                        checkedItems.put(checkedPostion, true);

                        List<Contant> simcontant = listAdapter.getContants();
                        simcontant.add(c);
                        listAdapter.notifyDataSetChanged();
                        xuanzhe.setText("选择联系人（" + simcontant.size() + "）");
                        queding.setTextColor(Color.WHITE);
                        queding.setClickable(true);
                    }else{
                        Toast.makeText(AddLinXiActivity.this, "请选择手机号码", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(AddLinXiActivity.this, "请不要选择重复的号码", Toast.LENGTH_SHORT).show();
                }

                editText.setText("");
            }
        });

        image_XZ.setOnClickListener(this);
        queding.setOnClickListener(this);


    }

    public void ser(){

           list = new ArrayList<Contant>();
//            contantDao = new ContantDao(this);
//            if(contantDao.select()!=null) {
//               list = contantDao.select();
                list = ReadContats.getContacts();
                listAdapter = new ListAdapter(list, this,xuanzhe,queding,contants);
                listView.setAdapter(listAdapter);
//            }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_XZ:
                finish();
                break;
            case R.id.text_quding:
                Intent data = getIntent();

                ArrayList<Contant> contactList = new ArrayList<>();

                Map<Integer,Boolean> checkedItems = listAdapter.getCheckedItems();


                for(Integer key : checkedItems.keySet()){
                    boolean flag = checkedItems.get(key);

                    if(flag){
                        //选中  取出该位置的对象  放到一个新的集合当中
                        Contant contant = list.get(key);

                        contactList.add(contant);
                    }
                }
                data.putExtra("data",contactList);
                setResult(1, data);

                finish();

                break;
        }
    }
///根据用户输入查询

    private void ser(String keg){
        //清除数据
        contantList.clear();
        for(int i = 0 ; i<list.size();i++){
            Contant c = list.get(i);
            if(c.getName().contains(keg)||c.getPhone().contains(keg)){
                contantList.add(c);
            }
        }
        listAdapterMoHu.notifyDataSetChanged();
    }
    private int getPosition(String phone){
        int postion = 0;
        for(int i = 0;i < list.size();i++){
            Contant c = list.get(i);

            if(c.getPhone().equals(phone)){
                postion = i;

                break;
            }
        }

        return postion;
    }
}
