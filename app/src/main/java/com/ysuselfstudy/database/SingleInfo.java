package com.ysuselfstudy.database;

import org.litepal.crud.LitePalSupport;

public class SingleInfo extends LitePalSupport {

    public SingleInfo(String time, String info) {
        this.info=info;
        this.time=time;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    String time;
    String info;
}
