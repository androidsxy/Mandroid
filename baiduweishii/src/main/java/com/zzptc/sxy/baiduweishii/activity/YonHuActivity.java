package com.zzptc.sxy.baiduweishii.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.zzptc.sxy.baiduweishii.DialogFragment.DialogFragmentWuWang;
import com.zzptc.sxy.baiduweishii.DialogFragment.DialogFragmentXin;
import com.zzptc.sxy.baiduweishii.R;
import com.zzptc.sxy.baiduweishii.contant.Contant;
import com.zzptc.sxy.baiduweishii.contant.UpdateInfo;

public class YonHuActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;
    private long iten;
    private LinearLayout line1;
    private String version;
    private AlertDialog dialog;
    public static final int ENTER_MEIYOU = 2;
    public static final int ENTER_HOMP = 1;
    private String description;
    private String apkurl;
//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case ENTER_HOMP:
//                    DialogFragmentWuWang wuWang = DialogFragmentWuWang.newInstance("当前已是最新版本");
//                    wuWang.show(getFragmentManager(),"");
//                    break;
//                case ENTER_MEIYOU:
//                    DialogFragmentXin xin = DialogFragmentXin.newInstance(description,apkurl);
//                    xin.show(getFragmentManager(),"");
//                    break;
//
//            }
//            super.handleMessage(msg);
//        }
//    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yon_hu);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        line1 = (LinearLayout) findViewById(R.id.line1);
        line1.setOnClickListener(this);
        toolbar.inflateMenu(R.menu.menu1);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itm3:
                        startActivity(new Intent(YonHuActivity.this,GuanYuActivity.class));
                        overridePendingTransition(R.anim.danchu_up_anim, R.anim.danchu_int_anim);
                        break;
                }
                return false;
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.danchu_up_anim, R.anim.danchu_int_anim);
            }
        });
    }

    @Override
    public void onClick(View v) {
         switch (v.getId()) {
             case R.id.line1:
                 startActivityForResult(new Intent(this, LoadActivity.class), Contant.RZXC_OKMJ);
                 break;
         }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Contant.RZXC_OKMJ){
            switch (resultCode){
                case Contant.CDFD_DDFG:
                    DialogFragmentWuWang wuWang = DialogFragmentWuWang.newInstance("没有网络，请检查网络！！！");
                    wuWang.show(getFragmentManager(),"");
                    break;
                case Contant.NET_EXCEPTION:
                    DialogFragmentWuWang wuWang1 = DialogFragmentWuWang.newInstance("网络异常，请检查网路错误！！！");
                    wuWang1.show(getFragmentManager(),"");
                    break;
                case Contant.NOT_NEED_UPDATE:
                    DialogFragmentWuWang wuWang2 = DialogFragmentWuWang.newInstance("您当前为最新版本，无需更新");
                    wuWang2.show(getFragmentManager(),"");
                    break;
                case Contant.NEED_UPDATE:
                    if(data!=null) {
                        UpdateInfo info = (UpdateInfo) data.getSerializableExtra("info");
                         DialogFragmentXin xin = DialogFragmentXin.newInstance(info.getVersionCont(),info);
                         xin.show(getFragmentManager(),"");
                    }else{

                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
//    public void update(){
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                Message mes = Message.obtain();
//                try {
//                    URL uri = new URL("http://192.168.245.2:8080/update.html");
//                    HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
//                    connection.setRequestMethod("GET");
//                    connection.setConnectTimeout(3000);
//                    if(connection.getResponseCode()==200){
//                        String resmlt = new InputDemo().input(connection.getInputStream());
//                        //解析json
//                        JSONObject json = new JSONObject(resmlt);
//                        version = (String) json.get("version");
//                        description = (String) json.get("description");
//                        apkurl = (String) json.get("apkurl");
//                        if(getVersionName().equals(version)){
//
//                            mes.what = ENTER_HOMP;
//                        }else{
//                            DialogFragmentXin fragment1 = new DialogFragmentXin();
//                            Bundle bundle = new Bundle();
//                            bundle.putString("str", apkurl);
//                            fragment1.setArguments(bundle);
//                            System.out.println("//////////...................."+getVersionName()+apkurl);
//                            mes.what = ENTER_MEIYOU;
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }finally {
//                    handler.dispatchMessage(mes);
//                }
//
//            }
//        }).start();
//    }

}
