package com.zzptc.sxy.baiduweishii.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzptc.sxy.baiduweishii.R;
import com.zzptc.sxy.baiduweishii.receiver.ScreenReceiver;
import com.zzptc.sxy.baiduweishii.view.DanceWageTimer;
import com.zzptc.sxy.baiduweishii.view.RatingBar;
import com.zzptc.sxy.baiduweishii.view.RatingView;

import java.util.Random;

public class MainUiActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;
    private Animation  animation,animation1;
    private TextView ty_top_l;
    private ScreenReceiver screenReceiver;
    private TextView tv_top;
    private TextView tv_js;
    private TextView text_JT;
    private RelativeLayout rel_ja;
    private RatingView ratingView;
    private RatingBar sec_bar,flu_bar,clean_bar;
    private int fengshu = 0;
    private int sec = 1 + new Random().nextInt(10);
    private int flu = 1 + new Random().nextInt(10);
    private int clean = 1 + new Random().nextInt(10);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);
        ininView();
        fengshu = (int) (sec * 0.5+ flu *0.3+ clean *0.2)*10;

        DanceWageTimer danceWageTimer = new DanceWageTimer (DanceWageTimer.getTotalExecuteTime(fengshu, 50),50,ty_top_l,fengshu);
        danceWageTimer.start();
        //打分
        initRatngView();
        //注册屏幕的监听广播
        screenReceiver = new ScreenReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(screenReceiver, intentFilter);

        toolbar.inflateMenu(R.menu.menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MainUiActivity.this,YonHuActivity.class));
                overridePendingTransition(R.anim.danchu_up_anim,R.anim.danchu_int_anim);
                return false;
            }
        });
    }
    public void ininView(){
        ratingView = (RatingView) findViewById(R.id.rv_rate);
        ty_top_l = (TextView)findViewById(R.id.text_vs);
        tv_top = (TextView) findViewById(R.id.tv_top);
        tv_top.setOnClickListener(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        text_JT = (TextView) findViewById(R.id.text_jT);
        text_JT.setOnClickListener(this);
        rel_ja = (RelativeLayout) findViewById(R.id.rel_dong);
        tv_js = (TextView) findViewById(R.id.tv_js);
        tv_js.setOnClickListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.jia_ting_3);
        Animation animation1 = AnimationUtils.loadAnimation(this,R.anim.jia_ting);
        rel_ja.startAnimation(animation1);
        ratingView.startAnimation(animation);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_top:
                startActivity(new Intent(this, FunctionActivity.class));
                //在startActivity / finish 之后加上如下方法 ,实现Activity的跳转动画
//                overridePendingTransition(R.anim.fun_up_anim, 0);
                overridePendingTransition(R.anim.fun_up_anim, R.anim.bubian_anim);
                break;
            case R.id.text_jT:
                animation = AnimationUtils.loadAnimation(this,R.anim.jian_ting_1);
                animation1 = AnimationUtils.loadAnimation(this,R.anim.jia_ting_2);
                ratingView.startAnimation(animation);
                rel_ja.startAnimation(animation1);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        startActivity(new Intent(MainUiActivity.this,JianTingActivity.class));
                        overridePendingTransition(R.anim.danchu_up_anim,R.anim.danchu_int_anim);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                break;
            case R.id.tv_js:
                animation = AnimationUtils.loadAnimation(this,R.anim.jian_ting_1);
                animation1 = AnimationUtils.loadAnimation(this,R.anim.jia_ting_2);
                ratingView.startAnimation(animation);
                rel_ja.startAnimation(animation1);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                         startActivity(new Intent(MainUiActivity.this,PhoneJiaSuActivity.class));
                         overridePendingTransition(R.anim.danchu_up_anim, R.anim.danchu_int_anim);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                break;
        }
//        int a =1;
//
//       String  h = a >= 8 ?"haha":(a >= 4 ?"xhx":"jaja");
    }
    public void initRatngView(){

        String  r = sec>=8?"流畅度高":(sec>=4?"流畅度中":"流畅度低");
        String f  = flu>=8?"安全度高":(flu>=4?"安全度中":"安全度低");
        String c  = clean>=8?"清洁度高":(clean>=4?"清洁度中":"清洁度低");

        sec_bar = new RatingBar(sec,r);
        flu_bar = new RatingBar(flu,f);
        clean_bar = new RatingBar(clean,c);

        ratingView.addRatingBar(sec_bar);
        ratingView.addRatingBar(flu_bar);
        ratingView.addRatingBar(clean_bar);


        ratingView.postDelayed( new Runnable() {
            @Override
            public void run() {
                ratingView.show();
            }
        },DanceWageTimer.getTotalExecuteTime(fengshu,50)
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(screenReceiver!=null){
            unregisterReceiver(screenReceiver);
        }
    }
}