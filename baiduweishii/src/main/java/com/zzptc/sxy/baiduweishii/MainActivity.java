package com.zzptc.sxy.baiduweishii;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.zzptc.sxy.baiduweishii.Uitls.ReadContats;
import com.zzptc.sxy.baiduweishii.activity.GuideActivity;
import com.zzptc.sxy.baiduweishii.activity.WelcomeActivity;
import com.zzptc.sxy.baiduweishii.contant.Contant;

import org.xutils.x;

import java.util.List;

public class MainActivity extends Activity {

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("panduan",MODE_PRIVATE);

        boolean  isfist = sharedPreferences.getBoolean("isfist",true);

        if(isfist){
            sharedPreferences.edit().putBoolean("isfist",false).commit();

            startActivity(new Intent(this, GuideActivity.class));
        }else{
            startActivity(new Intent(this, WelcomeActivity.class));
        }
        finish();
        x.task().run(new Runnable() {
            @Override
            public void run() {
                ReadContats.copyDatabase(MainActivity.this);

                List<Contant> contantList= ReadContats.getContacts();
                if(contantList==null){
                   contantList = ReadContats.queryAllContacts(MainActivity.this);
                    ReadContats.setContacts(contantList);
                }
            }
        });
    }


}
