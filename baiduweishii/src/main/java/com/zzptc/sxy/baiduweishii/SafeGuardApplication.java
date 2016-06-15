package com.zzptc.sxy.baiduweishii;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Administrator on 2016/4/26  0026.
 */
public class SafeGuardApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
