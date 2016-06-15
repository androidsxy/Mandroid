package com.zzptc.sxy.baiduweishii.Uitls;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import com.zzptc.sxy.baiduweishii.R;
import com.zzptc.sxy.baiduweishii.contant.AppProcessInfo;
import com.zzptc.sxy.baiduweishii.contant.Contant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SXY on 2016/5/26.
 */

import android.support.v4.content.ContextCompat;


import org.xutils.x;


/**
 * Created by Administrator on 2016/5/25.
 */
public class MemoryCleanTools {
    //因为我们昨天已经准备好了RecyclerView,因此我们今天需要准备数据集合
    //因为我们做了是一个手机加速模块，因此我们需要扫描系统的进程，必须是用户进程，并且排除自己。
    //因为有些用户安装的用户比较多，因此进行数量比较大，需要开启异步任务去执行扫描的任务
    //得到进行集合？如何去获取进程？如何使用异步任务去执行这个获取的过程？
    //http://www.cnblogs.com/crazypebble/archive/2011/04/09/2010196.html

    private ActivityManager activityManager ;
    private Context context;
    private List<AppProcessInfo> list;
    private PackageManager pck ;

    public MemoryCleanTools(){
        context = x.app().getApplicationContext();

        activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        pck = context.getPackageManager();
    }

    public void scanProcess(){
        new ScanProcess().execute();
    }



    //添加监听接口  三个
    public interface OnProcessListener{
        void onScanStartListener(Context context);

        void onScanProcessLisener(Context context,int current,int total);

        void onScanCompleteListener(Context context,List<AppProcessInfo> list);

        void onCleanStarListener(Context context);

        void onCleanCompleteListener(Context context,long cleanMemory);

    }


    private OnProcessListener onProcessListener;

    public void setOnProcessListener(OnProcessListener onProcessListener){
        this.onProcessListener = onProcessListener;
    }


    class ScanProcess extends AsyncTask<Void,Integer,List<AppProcessInfo>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if(onProcessListener != null){
                onProcessListener.onScanStartListener(context);
            }
        }

        @Override
        protected List<AppProcessInfo> doInBackground(Void... params) {

            list = new ArrayList<>();
            int progress = 0;

            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
            for(ActivityManager.RunningAppProcessInfo processInfo:runningAppProcesses){
                //更新进度
                publishProgress(++progress,runningAppProcesses.size());


                AppProcessInfo contant = new AppProcessInfo();
                //得到进程名
                contant.setPakcageName(processInfo.processName);

                try {
                    ApplicationInfo appInfo = pck.getApplicationInfo(processInfo.processName,0);

                    //图标
                    contant.setIcon(appInfo.loadIcon(pck));
                    //应用程序的名称
                    contant.setYinName(appInfo.loadLabel(pck).toString());
                    //判断是否为系统进程
                    if((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0){
                        contant.isSystem = true;
                    }else{
                        contant.isSystem = false;
                    }

                    if(processInfo.processName.equals(context.getPackageName())){
                        contant.isCurrent = true;
                    }

                } catch (PackageManager.NameNotFoundException e) {
                    //分析 ： 第一个：系统进程它没有应用名称   第二种：远程服务     xxx.xxx.xxx:xxx
                    if(processInfo.processName.contains(":")){
                        ApplicationInfo applicationInfo = getApplication(processInfo.processName.split(":")[0]);
                        if(applicationInfo != null){
                            contant.setIcon(applicationInfo.loadIcon(pck));
                        }else{
                            contant.setIcon(ContextCompat.getDrawable(context, R.mipmap.ic_launcher));
                        }
                    }else{
                            contant.setIcon(ContextCompat.getDrawable(context, R.mipmap.ic_launcher));
                    }

                    contant.isSystem = true;
                    contant.setPakcageName(processInfo.processName);
                }
                //获取内存
                contant.setDaxiao(1024*(activityManager.getProcessMemoryInfo(new int[]{processInfo.pid})[0].getTotalPrivateDirty()));

                list.add(contant);
            }

            return list;
        }

        @Override
        protected void onPostExecute(List<AppProcessInfo> list) {
            super.onPostExecute(list);
            if(onProcessListener != null){
                onProcessListener.onScanCompleteListener(context,list);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if(onProcessListener != null){
                onProcessListener.onScanProcessLisener(context,values[0],values[1]);
            }
        }
    }
    public class CleanAsyncTask extends AsyncTask<List<AppProcessInfo>,Void,Long>{

        @Override
        protected Long doInBackground(List<AppProcessInfo>... params) {
            List<AppProcessInfo> contantList = params[0];
            List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos = activityManager.getRunningAppProcesses();

            long befo = getAvailMemory();
            if(runningAppProcessInfos!=null){
                for(ActivityManager.RunningAppProcessInfo runningAppProcessInfo:runningAppProcessInfos){
                    for(AppProcessInfo contant:contantList){
                        if(contant.isChecked && contant.getPakcageName().equals(runningAppProcessInfo.processName)){
                            killBackGroundProcess(contant.getPakcageName());
                        }
                    }
                }
            }
            long after = getAvailMemory();


            return after - befo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(onProcessListener != null){
                onProcessListener.onCleanStarListener(context);
            }
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            if(onProcessListener != null){
                onProcessListener.onCleanCompleteListener(context,aLong);
            }
        }
    }
    /**
     * 获取ApplicationInfo
     * @param processName
     * @return
     */
    private ApplicationInfo getApplication(String processName){
        if(processName == null){
            return null;
        }
        List<ApplicationInfo> allLists = pck.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        for(ApplicationInfo applicationInfo:allLists){
            if(processName.equals(applicationInfo.processName)){
                return applicationInfo;
            }
        }
        return null;
    }
    //获取内存大小

    private long getAvailMemory(){
        //获取android 当前可用内存
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }
    public void startClean(List<AppProcessInfo> contantList){
        new CleanAsyncTask().execute(contantList);
    }

    /**清除进程
     *
     * @param processName
     */
    public void killBackGroundProcess(String processName){
        String process = processName;
        if(processName.contains(":")){
            process = processName.split(":")[0];
        }
        activityManager.killBackgroundProcesses(process);
    }
}
