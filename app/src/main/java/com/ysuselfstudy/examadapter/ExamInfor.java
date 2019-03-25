package com.ysuselfstudy.examadapter;

public class ExamInfor {
    public ExamInfor(String name,String time,String location,String number) {
        this.location=location;
        this.name=name;
        this.time=time;
        this.number=number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    private String name;
    private String time;
    private String location;
    private String number;

}
