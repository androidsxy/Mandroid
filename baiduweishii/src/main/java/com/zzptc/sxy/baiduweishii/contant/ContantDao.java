package com.zzptc.sxy.baiduweishii.contant;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/1  0001.
 */
public class ContantDao {
    private Context context;
    private List<Contant> list;

    public ContantDao(Context context){
        this.context = context;
    }
    public  List<Contant> select(){
        list = new ArrayList<Contant>();
        Cursor cursor1 = null;
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri uri1 =Uri.parse("content://com.android.contacts/data");
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(uri,new String[]{"contact_id"},null,null,null);
        while (cursor.moveToNext()){
            String id = cursor.getString(0);
            if(id==null) {

            }else{
                cursor1 = resolver.query(uri1, new String[]{"data1", "mimetype"},
                        "raw_contact_id = ?", new String[]{id}, null);
                Contant contant = new Contant();
                while(cursor1.moveToNext()){
                    String data1 =cursor1.getString(0);
                    String mimetype = cursor1.getString(1);
                    if("vnd.android.cursor.item/name".equals(mimetype)){
                        contant.setName(data1);
                    }if("vnd.android.cursor.item/phone_v2".equals(mimetype)){
                        contant.setPhone(data1);
                    }
                }
                list.add(contant);
            }
        }
        return  list;
    }
}
