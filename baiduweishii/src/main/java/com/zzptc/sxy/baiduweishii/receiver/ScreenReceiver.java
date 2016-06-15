package com.zzptc.sxy.baiduweishii.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;

import com.zzptc.sxy.baiduweishii.service.LocationService;

public class ScreenReceiver extends BroadcastReceiver {
    private long screenOffTime = 0;
    private long screenOnTime = 0;
    //用户操作的次数
    private int  pressCount = 0;
    public ScreenReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals(Intent.ACTION_SCREEN_OFF)){
            screenOffTime = System.currentTimeMillis();
            System.out.println("00000000000000000000");
        }
        if(action.equals(Intent.ACTION_SCREEN_ON)){
            screenOnTime = System.currentTimeMillis();
            System.out.println("11111111111111111111");
        }
        if(screenOnTime - screenOffTime <= 1000){
            pressCount++;

            if(pressCount == 4){
                pressCount = 0;

                //震动
                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(1000);

                //启动服务：定位，发短信
                context.startService(new Intent(context, LocationService.class));

                Log.i("screenReceiver", "#################################");
            }
        }
    }
}
