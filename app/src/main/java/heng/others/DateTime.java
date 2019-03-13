package heng.others;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.Calendar;

public class DateTime extends LitePalSupport {

    public DateTime() {
        Calendar calendar=Calendar.getInstance();
        this.Month=calendar.get(Calendar.MONTH)+1; //因为 Calender 返回的是以数组排列的。一月是 0
        this.Date=calendar.get(Calendar.DATE);
    }

    private int Month;
    private int Date;
    public int getMonth() {
        return Month;
    }

    public void setMonth(int month) {
        Month = month;
    }

    public int getDate() {
        return Date;
    }

    public void setDate(int date) {
        Date = date;
    }

}
