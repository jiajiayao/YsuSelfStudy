package com.ysuselfstudy.database;

import com.ysuselfstudy.tencent.Personel;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

/**
 * 这是 推送的消息类。分为日期和时间
 */
public class MipushInfor extends LitePalSupport {
    public MipushInfor(String time,String information){
        this.time=time;
        this.information=information;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String time;
    public String information;
}
