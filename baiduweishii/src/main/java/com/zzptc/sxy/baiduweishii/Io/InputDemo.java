package com.zzptc.sxy.baiduweishii.Io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/4/22  0022.
 */
public class InputDemo {
    public static String input(InputStream is) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte [] byes = new byte [1024];
        int ier = 0;
        while ((ier=is.read(byes))!=-1){
         out.write(byes,0,byes.length);
        }
        is.close();
        String resmlt = out.toString();
        out.close();
        return resmlt;
    }
}
