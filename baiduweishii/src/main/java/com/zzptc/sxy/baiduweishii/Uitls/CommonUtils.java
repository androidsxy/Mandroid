package com.zzptc.sxy.baiduweishii.Uitls;

import android.telephony.SmsManager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/13  0013.
 */
public class CommonUtils{

    /**
     * 给指定号码发送短信
     * @param phone  电话号码
     * @param message 短信内容
     */
    public static void sendMessage(String phone,String message){
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> sms = smsManager.divideMessage(message);
        for(String str:sms){
            smsManager.sendTextMessage(phone,null,str,null,null);
        }
    }


    //计算内存  以1024为单位进行计算
    public static String convertStorage(long memory){
        long kb = 1024;
        long mb = 1024 * kb;
        long gb = 1024 * mb;

        if(memory > 0){
            if(memory > gb){
                float size = (float)memory / gb;
                return String.format("%.1f GB",size);
            }else if(memory > mb){
                float size = (float)memory / mb;
                return String.format("%.1f MB",size);
            }else if(memory > kb){
                float size = (float)memory / kb;
                return String.format("%.1f KB",size);
            }else{
                return String.format("%.1f B",memory);
            }
        }else{
            return "0 B";
        }
    }
}
