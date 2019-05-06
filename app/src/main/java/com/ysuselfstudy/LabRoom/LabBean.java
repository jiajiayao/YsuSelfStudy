package com.ysuselfstudy.LabRoom;

public class LabBean  implements Comparable<LabBean> {
    public String room;

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String time;
    public String content;
    public String project;

    @Override
    public int compareTo(LabBean o) {
        if(this.getTime().length()==o.getTime().length())
            return this.getTime().compareTo(o. getTime());
        else
            return this.getTime().length() - o.getTime().length();
    }
}
