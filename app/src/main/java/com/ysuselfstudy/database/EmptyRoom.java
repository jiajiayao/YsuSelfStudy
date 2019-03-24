package com.ysuselfstudy.database;

import org.litepal.crud.LitePalSupport;

public class EmptyRoom extends LitePalSupport {

    private String room;
    private String location;
    private String xiaoqu;
    private String nums;
    private int time;
    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getXiaoqu() {
        return xiaoqu;
    }

    public void setXiaoqu(String xiaoqu) {
        this.xiaoqu = xiaoqu;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }




    public EmptyRoom(String name,String number,String location,int time) {
        this.room=name;
        this.nums=number;
        this.location=location;
        this.time=time;
    }
    public  EmptyRoom(String name,String number,String location)
    {
        this.room=name;
        this.nums=number;
        this.location=location;
    }


}