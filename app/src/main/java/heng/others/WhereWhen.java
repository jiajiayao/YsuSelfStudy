package heng.others;

import java.io.Serializable;

public class WhereWhen implements Serializable {
    private String Where;

    public String getWhere() {
        return Where;
    }

    public void setWhere(String where) {
        Where = where;
    }

    public int getBegin_time() {
        return Begin_time;
    }

    public void setBegin_time(int begin_time) {
        Begin_time = begin_time;
    }

    public int getEnd_time() {
        return End_time;
    }

    public void setEnd_time(int end_time) {
        End_time = end_time;
    }

    private int Begin_time;
    private int End_time;

}
