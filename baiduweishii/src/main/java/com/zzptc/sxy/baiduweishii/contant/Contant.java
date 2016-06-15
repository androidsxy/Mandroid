package com.zzptc.sxy.baiduweishii.contant;

import android.graphics.drawable.Drawable;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/25  0025.
 */
@Table(name = "usb")
public class Contant implements Serializable {
    @Column(name = "id",isId = true)
    private int id;
    @Column(name = "name")
    private String name ;
    @Column(name = "helemage")
    private String helemage;
    @Column(name = "phone")
    private String phone;
    private String attribute;
    @Column(name = "helpMessage")
    private String helpMessage;
    private int headColor;



    public static final int RZXC_OKMJ = 0;
    //没网
    public static final int CDFD_DDFG = 1;
    //网络异常
    public static final int NET_EXCEPTION = 2;
    //取消
    public static final int NET_CANCEL = 3;

    //需要更新
    public static final int NEED_UPDATE = 4;

    //不需要更新
    public static final int NOT_NEED_UPDATE = 5;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public String toString() {
        return "Contant{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", phone='" + phone + '\'' +
                ", sheng='"  + '\'' +
                '}';
    }

    public String getHelemage() {
        return helemage;
    }

    public void setHelemage(String helemage) {
        this.helemage = helemage;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getHelpMessage() {
        return helpMessage;
    }

    public void setHelpMessage(String helpMessage) {
        this.helpMessage = helpMessage;
    }

    public int  getHeadColor() {
        return headColor;
    }

    public void setHeadColor(int headColor) {
        this.headColor = headColor;
    }


}
