package com.zzptc.sxy.baiduweishii.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.premnirmal.textcounter.CounterView;
import com.github.premnirmal.textcounter.formatters.IntegerFormatter;
import com.zzptc.sxy.baiduweishii.DialogFragment.YingYongFragmentDialog;
import com.zzptc.sxy.baiduweishii.R;
import com.zzptc.sxy.baiduweishii.Uitls.CommonUtils;
import com.zzptc.sxy.baiduweishii.Uitls.MemoryCleanTools;
import com.zzptc.sxy.baiduweishii.adapter.MyAdapter;
import com.zzptc.sxy.baiduweishii.contant.AppProcessInfo;


import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.x;


import java.util.ArrayList;
import java.util.List;

public class PhoneJiaSuActivity extends Activity implements AppBarLayout.OnOffsetChangedListener,MemoryCleanTools.OnProcessListener, MyAdapter.OnRecyclerViewItemClickListener,View.OnClickListener{
    private Toolbar toolbar;
    private Button btn_clean;
    long contIms = 0;
    private RelativeLayout rl_load;
    private RecyclerView view;
    private MemoryCleanTools memoryCleanTools;
    private List<AppProcessInfo>  list1;
    private TextView tv_saomiao;
    private TextView tv_count;
    private RelativeLayout rl_result;
    private RelativeLayout rl_clean;
    private TextView tv_toodar;
    private CounterView coun_veid;
    private AppBarLayout appbar;
    private MyAdapter jianCheAdapter;
    private DbManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_jia_su);

        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("AppProcessInfo.db")
                .setDbVersion(1)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        db.getDatabase().enableWriteAheadLogging();
                    }
                });
        dbManager = x.getDb(daoConfig);


        info();
        toolbar.inflateMenu(R.menu.menu2);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void info() {
        list1 = new ArrayList<>();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_toodar = (TextView) findViewById(R.id.tv_toodar);
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        view = (RecyclerView) findViewById(R.id.recyler_view);
        tv_saomiao = (TextView) findViewById(R.id.tv_saomiao);
        rl_result = (RelativeLayout) findViewById(R.id.rl_result);
        rl_clean = (RelativeLayout) findViewById(R.id.rl_clean);
        rl_load = (RelativeLayout) findViewById(R.id.rl_load);
        tv_count = (TextView) findViewById(R.id.tv_count);
        btn_clean = (Button) findViewById(R.id.btn_clean);
        coun_veid = (CounterView) findViewById(R.id.coun_veid);
        appbar.addOnOffsetChangedListener(this);
        btn_clean.setOnClickListener(this);
        jianCheAdapter= new MyAdapter(list1, this);
        view.setAdapter(jianCheAdapter);
        jianCheAdapter.setOnItemClickListener(this);
        view.setLayoutManager(new LinearLayoutManager(this));
        //监听
        memoryCleanTools = new MemoryCleanTools();
        memoryCleanTools.setOnProcessListener(this);
        memoryCleanTools.scanProcess();
    }


    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //一起来观察偏移量的值的变化
        if(verticalOffset < -275){
            tv_toodar.setText(contIms/1024+"MB可清理");
        }else{
            tv_toodar.setText("手机加速");
        }
    }

    @Override
    public void onScanStartListener(Context context) {

    }
    @Override
    public void onScanProcessLisener(Context context, int current, int total) {
        tv_saomiao.setText("正在扫描" + current + "/" + total);
    }

    @Override
    public void onScanCompleteListener(Context context, final List<AppProcessInfo> list) {

        rl_load.setVisibility(View.GONE);
        rl_result.setVisibility(View.VISIBLE);
        rl_clean.setVisibility(View.VISIBLE);
       for(AppProcessInfo contant : list){
            if(!contant.isSystem && !contant.isCurrent) {
                try {
                    AppProcessInfo processInfo = dbManager.selector(AppProcessInfo.class).where(WhereBuilder.b("yinName", "=", contant.getYinName()).and("pakcageName", "=", contant.getPakcageName())).findFirst();
                    if (processInfo != null) {
                        contant.isChecked = false;
                    } else {
                        contant.isChecked = true;
                    }
                    list1.add(contant);
                    if (contant.isChecked) {
                        contIms = contIms + contant.getDaxiao();
                    }

                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
       }

        jianCheAdapter.notifyDataSetChanged();

        tv_count.setText("正在运行的进程（" + list1.size() + "个）");
        btn_clean.setText("一键加速 " + CommonUtils.convertStorage(contIms));
        countUp(contIms);

    }

    @Override
    public void onCleanStarListener(Context context) {
        btn_clean.startAnimation(AnimationUtils.loadAnimation(this, R.anim.main_ui_down_down));

    }

    @Override
    public void onCleanCompleteListener(Context context, long cleanMemory) {
         while(list1.size()>0){
             jianCheAdapter.removeItem(0);
         }
        countDown(contIms, contIms - cleanMemory);
    }


    @Override
    public void onItemClick(View view, int position) {
        AppProcessInfo contant = list1.get(position);
        YingYongFragmentDialog fagmentDialog =YingYongFragmentDialog.newInstance(contant, position);
        fagmentDialog.show(getFragmentManager(), null);
    }

    @Override
    public void onCheckBoxChangedListener(View view, int position) {
        CheckBox checkBox = (CheckBox) view;
        AppProcessInfo contant = list1.get(position);
        try {
        if (checkBox.isChecked()) {
            dbManager.delete(AppProcessInfo.class, WhereBuilder.b("yinName", "=", contant.getYinName()).and("pakcageName", "=", contant.getPakcageName()));
            contant.isChecked = true;
            contIms = contIms + contant.getDaxiao();

        //    contentInmt.put(position, true);
        } else {
            dbManager.save(contant);
            contant.isChecked = false;
            contIms = contIms - contant.getDaxiao();
       //     contentInmt.put(position, false);
        }
        } catch (DbException e) {
            e.printStackTrace();
        }
        jianCheAdapter.notifyDataSetChanged();
        btn_clean.setText("一键加速 " + CommonUtils.convertStorage(contIms));

    }

    public MyAdapter getJianCheAdapter() {
        return jianCheAdapter;
    }

    public void setJianCheAdapter(MyAdapter jianCheAdapter) {
        this.jianCheAdapter = jianCheAdapter;
    }

    public long getContIms() {
        return contIms;
    }

    public void setContIms(long contIms) {
        this.contIms = contIms;
    }

    public DbManager getDbManager() {
        return dbManager;
    }

    public int getSeiz(){
        return list1.size();
    }
    public MemoryCleanTools getMeoryCleanTools(){
        return memoryCleanTools;
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case  R.id.btn_clean:
            memoryCleanTools.startClean(list1);
            break;

        }
    }

    /**
     * 值变大
     * @param memory
     */
    private void countUp(long memory){
        coun_veid.setAutoFormat(false);
        coun_veid.setAutoStart(false);
        coun_veid.setStartValue(0f);

        String size = CommonUtils.convertStorage(memory);
        String[] strs = size.split(" ");
        coun_veid.setEndValue(Float.valueOf(strs[0]));
        coun_veid.setIncrement(1);
        coun_veid.setTimeInterval(50);
        coun_veid.start();
    }

    private void countDown(long beforeMemory,long afterMemory){
        coun_veid.setAutoFormat(false);
        coun_veid.setAutoStart(false);
        coun_veid.setFormatter(new IntegerFormatter());

        String beforeSize = CommonUtils.convertStorage(beforeMemory);
        String[] befores = beforeSize.split(" ");
        coun_veid.setStartValue(Float.valueOf(befores[0]));

        String afterSize = CommonUtils.convertStorage(afterMemory);
        String[] afters = afterSize.split(" ");
        coun_veid.setEndValue(Float.valueOf(afters[0]));

        coun_veid.setIncrement(-1);
        coun_veid.setTimeInterval(50);
        coun_veid.start();
    }

}
