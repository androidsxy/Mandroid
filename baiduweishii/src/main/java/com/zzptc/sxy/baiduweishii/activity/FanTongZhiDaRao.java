package com.zzptc.sxy.baiduweishii.activity;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzptc.sxy.baiduweishii.R;
import com.zzptc.sxy.baiduweishii.Uitls.MemoryCleanTools;
import com.zzptc.sxy.baiduweishii.adapter.FangAdapter;
import com.zzptc.sxy.baiduweishii.contant.AppProcessInfo;

import java.util.ArrayList;
import java.util.List;

public class FanTongZhiDaRao extends AppCompatActivity implements MemoryCleanTools.OnProcessListener{
    private ImageView image_one;
    private MemoryCleanTools memoryCleanTools;
    private ImageView image_two;
    private List<AppProcessInfo> lists;
    private RelativeLayout re_vse,rv_relt;
    private TextView tv_text;
    private RecyclerView re_view;
    private Animation red_anim;
    private TextView tv_sizs;
    private Toolbar tv_toolbar;
    private FangAdapter adapter;
    private Animation yellow_anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan_tong_zhi_da_rao);
        inio();
    }

    private void inio() {
        image_one = (ImageView) findViewById(R.id.image_one);
        image_two = (ImageView) findViewById(R.id.image_tow);

        tv_text = (TextView) findViewById(R.id.tv_text);
        re_vse = (RelativeLayout) findViewById(R.id.re_vse);
        rv_relt = (RelativeLayout)findViewById(R.id.rv_relt);
        tv_sizs = (TextView) findViewById(R.id.tv_sizs);
        re_view = (RecyclerView) findViewById(R.id.re_view);
        tv_toolbar = (Toolbar) findViewById(R.id.tv_toobar);
        tv_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        red_anim = AnimationUtils.loadAnimation(this, R.anim.load_scale_set1);
        yellow_anim = AnimationUtils.loadAnimation(this,R.anim.load_scale_set1);
        startAnimation();
        lists = new ArrayList<AppProcessInfo>();
        adapter =new FangAdapter(this,lists);
        re_view.setAdapter(adapter);
        re_view.setLayoutManager(new LinearLayoutManager(this));
        memoryCleanTools = new MemoryCleanTools();
        memoryCleanTools.setOnProcessListener(this);
        memoryCleanTools.scanProcess();
    }

    private void startAnimation() {
        image_one.startAnimation(red_anim);

        red_anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                image_two.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                image_two.setVisibility(View.VISIBLE);
                image_two.startAnimation(yellow_anim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        yellow_anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                image_one.startAnimation(red_anim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onScanStartListener(Context context) {

    }

    @Override
    public void onScanProcessLisener(Context context, int current, int total) {
        for(int i=1;i<=4;i++){
            int a = current/total*100;
            tv_text.setText("正在扫描"+a+"%");
        }


    }

    @Override
    public void onScanCompleteListener(Context context, List<AppProcessInfo> list) {
        re_vse.setVisibility(View.GONE);
        rv_relt.setVisibility(View.VISIBLE);
        re_view.setVisibility(View.VISIBLE);

        //获取手机中所有已安装的应用，并判断是否系统应用
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);

        for(int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            AppProcessInfo info = new AppProcessInfo();
            info.setYinName(packageInfo.applicationInfo.loadLabel(getPackageManager()).toString());
            info.setIcon(packageInfo.applicationInfo.loadIcon(getPackageManager()));
            info.setPakcageName(packageInfo.packageName);

            //判断是否系统应用
            if((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0 & !packageInfo.packageName.equals(context.getPackageName())) {
                //非系统应用
                lists.add(info);
            }

        }

        tv_sizs.setText("已安装("+lists.size()+")");
    }

    @Override
    public void onCleanStarListener(Context context) {

    }

    @Override
    public void onCleanCompleteListener(Context context, long cleanMemory) {

    }
}
