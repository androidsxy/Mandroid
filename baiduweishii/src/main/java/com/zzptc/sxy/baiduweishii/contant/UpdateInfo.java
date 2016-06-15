package com.zzptc.sxy.baiduweishii.contant;

import java.io.Serializable;

public class UpdateInfo implements Serializable{
   private String downloadUrl;
   private String  versionCont;
   private int descriPtion;
public String getVersionCont() {
	return versionCont;
}
public void setVersionCont(String versionCont) {
	this.versionCont = versionCont;
}
public String getDownloadUrl() {
	return downloadUrl;
}
public void setDownloadUrl(String downloadUrl) {
	this.downloadUrl = downloadUrl;
}
public int getDescriPtion() {
	return descriPtion;
}
public void setDescriPtion(int descriPtion) {
	this.descriPtion = descriPtion;
}
   
}
