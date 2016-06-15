package com.zzptc.sxy.baiduweishii.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zzptc.sxy.baiduweishii.R;

public class FunctionActivity extends Activity implements View.OnClickListener{
    private TextView textView;
    private TextView tv_fang;
    //activity 的对话框
    //fragment  对话框
  //带有返回值的Activity
    //判断是否有网

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        ser();
    }
    public void ser(){
        textView = (TextView) findViewById(R.id.tv_top_down);
        tv_fang = (TextView) findViewById(R.id.tv_fang);
        textView.setOnClickListener(this);
        tv_fang.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_top_down:
                finish();
                overridePendingTransition(0,R.anim.fun_int_anim);
                break;
            case R.id.tv_fang:
                startActivity(new Intent(this,FanTongZhiDaRao.class));
                break;
        }
    }
}
