package com.zzptc.sxy.baiduweishii.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.zzptc.sxy.baiduweishii.R;

public class JianTingActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;
    private LinearLayout lin_Jt_1;
    private LinearLayout lin_Jt_2;
    private LinearLayout lin_Jt_3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jian_ting);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lin_Jt_1 = (LinearLayout) findViewById(R.id.lin_Jt_1);
        lin_Jt_2 = (LinearLayout) findViewById(R.id.lin_Jt_2);
        lin_Jt_3 = (LinearLayout) findViewById(R.id.lin_JT_3);
        lin_Jt_1.setOnClickListener(this);
        lin_Jt_2.setOnClickListener(this);
        lin_Jt_3.setOnClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.danchu_up_anim,R.anim.danchu_int_anim);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lin_Jt_1:
                startActivity(new Intent(this,OneKeyForHelpActivity.class));
                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.danchu_up_anim, R.anim.danchu_int_anim);
    }
}
