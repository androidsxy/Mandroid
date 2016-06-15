package com.zzptc.sxy.baiduweishii.activity;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.zzptc.sxy.baiduweishii.DialogFragment.AddContaactsFragment;
import com.zzptc.sxy.baiduweishii.DialogFragment.ForHelpFragment;
import com.zzptc.sxy.baiduweishii.DialogFragment.JianJiQuiJuiFragment;
import com.zzptc.sxy.baiduweishii.R;

public class OneKeyForHelpActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView image_one;
    private AddContaactsFragment addContaactsFragment;
    private JianJiQuiJuiFragment jianJiQuiJuiFragment;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_key_for_help);
        image_one = (ImageView) findViewById(R.id.image_one);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //用启一进来，就看到一键求救的界面
        //一、得到fragmentManager
        //二、打开事务 transaction
        //三、替换 replace
        //四、提交事务 commit
        SharedPreferences sp = getSharedPreferences("urgent", Context.MODE_PRIVATE);
        boolean hasUrgent = sp.getBoolean("hasUrgent",false);
        if(hasUrgent){
            ser();
        }else{
            getFragmentManager().beginTransaction().replace(R.id.frame_jia, new ForHelpFragment()).addToBackStack("abc").commit();
        }
    }

    public void er(){
        if(addContaactsFragment==null){
            addContaactsFragment = new AddContaactsFragment();
        }
       getFragmentManager().beginTransaction().add(R.id.frame_jia,new AddContaactsFragment()).setCustomAnimations(R.animator.fragment_dao_hua,R.animator.fragment_dao_hao_up,R.animator.fragment_dao_hua,R.animator.fragment_dao_hao_up).addToBackStack("abc").commit();
        image_one.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount()>1){
            getFragmentManager().popBackStack();
            image_one.setVisibility(View.VISIBLE);
        }else {
            finish();
        }

    }

    public void ser(){
        getFragmentManager().popBackStackImmediate("abc", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        if(jianJiQuiJuiFragment==null){
            jianJiQuiJuiFragment = new JianJiQuiJuiFragment();
        }
       getFragmentManager().beginTransaction().setCustomAnimations(R.animator.fragment_dao_hua, R.animator.fragment_dao_hao_up, R.animator.fragment_dao_hua, R.animator.fragment_dao_hao_up).replace(R.id.frame_jia, new JianJiQuiJuiFragment()).addToBackStack("abc").commit();
    }
}
