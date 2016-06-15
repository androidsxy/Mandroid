package com.zzptc.sxy.baiduweishii.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;

import com.zzptc.sxy.baiduweishii.R;
import com.zzptc.sxy.baiduweishii.contant.UpdateInfo;
import com.zzptc.sxy.baiduweishii.download.DownloadManager;

import org.xutils.ex.DbException;

/**
 * Created by Administrator on 2016/4/23  0023.
 */
public class DialogFragmentXin extends DialogFragment {
    @Nullable

    private DownloadManager downloadManager;
    public static DialogFragmentXin newInstance(String  description,UpdateInfo info){

        DialogFragmentXin xin = new DialogFragmentXin();
        Bundle bundle = new Bundle();
        bundle.putString("description",description);
        bundle.putSerializable("info",info);
        xin.setArguments(bundle);
        return xin;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        downloadManager = DownloadManager.getInstance();
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {


//        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
//        View v1 = layoutInflater.inflate(R.layout.activity_fragment_gentxi, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.mipmap.icon_large)
                .setTitle(R.string.baidu)
                .setMessage(getArguments().getString("description"))
                .setPositiveButton("现在升级", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //得到用户中心发送过来的数据
                        UpdateInfo updateInfo = (UpdateInfo) getArguments().getSerializable("info");
                        if (updateInfo!=null) {
                            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                                try {
                                    System.out.println(updateInfo.getDownloadUrl());
                                    downloadManager.startDownload(updateInfo.getDownloadUrl(),"safeguar","/sdcard/download/safeguar.apk",true,true,null);

                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        dismiss();
                    }
                })
        .setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });


        return builder.create();
    }
//   public void installApk(File f){
//       Intent intent = new Intent();
//       intent.setAction("android.intent.action.VIEW");
//       intent.setDataAndType(Uri.fromFile(f), "application/vnd.android.package-archive");
//       startActivity(intent);
//   }


}
