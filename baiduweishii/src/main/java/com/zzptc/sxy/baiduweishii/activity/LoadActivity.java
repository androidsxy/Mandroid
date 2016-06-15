package com.zzptc.sxy.baiduweishii.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.zzptc.sxy.baiduweishii.R;
import com.zzptc.sxy.baiduweishii.contant.Contant;
import com.zzptc.sxy.baiduweishii.contant.UpdateInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

//任务一：有返回值的Activity
//任务二：Fragment Dialog
//任务三：Activity 与Fragment 传递数据
//任务四：xutils3
public class LoadActivity extends Activity {
    private ImageView imageView,imageView1;
    private Animation animation,animation1;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        imageView = (ImageView) findViewById(R.id.iv_red);
        imageView1 = (ImageView) findViewById(R.id.iv_yellow);
        jianche();
        boolean fanl = panduan();
        if(fanl){
            //有网
            //连接服务器更新,
            //xutils3 如何集成到我们项目当中
            //判断版本号
            RequestParams params = new RequestParams("http://192.168.2.63:8090/BaiDuServic/servlet/UpdateServlet");
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    System.out.println(",,,,,,,,,,,,,,,,+++++++++1");
                    UpdateInfo info = new Gson().fromJson(result, UpdateInfo.class);
                    //服务器版本号
                    System.out.println(",,,,,,,,,,,,,,,,+++++++++2");
                    int serverVersionCode = info.getDescriPtion();
                    System.out.println(",,,,,,,,,,,,,,,,+++++++++3");
                    //客户端版本号
                    int  clientVersionCode = getVersionName();
                    if(serverVersionCode>clientVersionCode){
                        //服务器版本号大于客户端的版本号
                        Intent date = new Intent();
                        date.putExtra("info",info);
                        setResult(Contant.NEED_UPDATE,date);
                        finish();
                    }else{
                        setResult(Contant.NOT_NEED_UPDATE);
                        finish();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    setResult(Contant.NET_EXCEPTION);
                    finish();
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });

        }else{
            setResult(Contant.CDFD_DDFG);
            finish();
        }
      //  update();

    }
    public void jianche(){
        animation = AnimationUtils.loadAnimation(this, R.anim.load_donhua);
        animation1 = AnimationUtils.loadAnimation(this, R.anim.load_donhua);
        imageView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imageView1.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView1.setVisibility(View.VISIBLE);

                imageView1.startAnimation(animation1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.startAnimation(animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public boolean panduan(){
        //
       ConnectivityManager com = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //
        NetworkInfo info = com.getActiveNetworkInfo();

        if(info!=null) {
           return info.isAvailable();
        }

        return false;
    }
    public int getVersionName(){
        PackageManager packeae = getPackageManager();
        try {
            PackageInfo info =  packeae.getPackageInfo(getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
