package com.ysuselfstudy.database;

import android.util.Log;

import org.litepal.LitePal;

import java.util.List;

import com.ysuselfstudy.time.DateTime;

public class DateBaseManager {
    private static final String TAG = "DateBaseManager";
    public boolean ChecekDate(){
        List<DateTime> dateTimes=LitePal.findAll(DateTime.class);
        if (LitePal.count(DateTime.class)==1)
        {
            DateTime time=new DateTime();
            DateTime check=LitePal.findFirst(DateTime.class);
            Log.d(TAG, "ChecekDate: "+"是否相同？");
            if(check.getDate()!=time.getDate()||check.getMonth()!=time.getMonth())
                return false;
            else
            {
                Log.d(TAG, "ChecekDate: 相同");
                return true;
            }

        }
        Log.d(TAG, "ChecekDate: 准备新建");

            return false;
    }

    /**
     * 删除日期表
     */
    public void delete_Date()
    {
        LitePal.deleteAll(DateTime.class);
    }

    /**
     * 设置日期表
     */
    public void setDate()
    {
        delete_Date();
        DateTime time=new DateTime();
        time.save();
    }

    /**
     * 删除空教室表
     */
    public void delete_EmptyRoom()
    {
        LitePal.deleteAll(EmptyRoom.class);
    }
}