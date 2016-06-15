package com.zzptc.sxy.baiduweishii.contant;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by SXY on 2016/5/31.
 */
@Table(name = "AppProcessInfo")
public class AppProcessInfo implements Parcelable {
    //id
    @Column(name = "_id",isId = true)
    private Integer processId;
    //图标
    private  Drawable icon;
    //包名
    @Column(name = "pakcageName")
    private String pakcageName;
    private int color;
    @Column(name = "yinName")
    private String yinName;
    private long daxiao;

    //是否系统进程
    public boolean isSystem;
    //是否为当前进程
    public boolean isCurrent = false;
    //复选框是否被选中
    @Column(name ="isChecked")
    public boolean isChecked = true;

    public AppProcessInfo() {
    }
    public static final Creator<AppProcessInfo> CREATOR = new Creator<AppProcessInfo>() {
        @Override
        public AppProcessInfo createFromParcel(Parcel in) {
            return new AppProcessInfo(in);
        }

        @Override
        public AppProcessInfo[] newArray(int size) {
            return new AppProcessInfo[size];
        }
    };

    public String getPakcageName() {
        return pakcageName;
    }

    public void setPakcageName(String pakcageName) {
        this.pakcageName = pakcageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }
    public String getYinName() {
        return yinName;
    }

    public void setYinName(String yinName) {
        this.yinName = yinName;
    }

    public long getDaxiao() {
        return daxiao;
    }

    public void setDaxiao(long daxiao) {
        this.daxiao = daxiao;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    protected AppProcessInfo(Parcel in) {
        color = in.readInt();
        yinName = in.readString();
        daxiao = in.readLong();
        pakcageName = in.readString();
        isSystem = in.readByte() != 0;
        isCurrent = in.readByte() != 0;
        isChecked = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(color);
        dest.writeString(yinName);
        dest.writeLong(daxiao);
        dest.writeString(pakcageName);
        dest.writeByte((byte) (isSystem ? 1 : 0));
        dest.writeByte((byte) (isCurrent ? 1 : 0));
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }
}
