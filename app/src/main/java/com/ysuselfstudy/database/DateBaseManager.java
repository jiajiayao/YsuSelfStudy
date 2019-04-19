package com.ysuselfstudy.database;

import android.util.Log;

import org.litepal.LitePal;

import java.util.List;

import com.ysuselfstudy.tencent.Personel;
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

    /**
     *更新数据库中学生的信心
     */
    public void UpdateStu(StudentInfo studentInfo)
    {
        LitePal.deleteAll(StudentInfo.class);
        studentInfo.save();
    }
    /**
     * 检查是否用填充密码
     */
    public boolean CheckPassword()
    {
        if(LitePal.count(StudentInfo.class)>0)
            return true;
        return false;
    }
    /**
     * 保存QQ登录的信息。
     */
    public void setTencent(String openid,String taken,String name,String urltouxiang,String ex)
    {
        Personel personel=new Personel();
        personel.setAccessToken(taken);
        personel.setOpenId(openid);
        personel.setQQname(name);
        personel.setExpires(ex);
        personel.setQQtouxiang(urltouxiang);
        personel.save();
    }
    /**
     * 检查QQ登录态时都有效
     */
    public boolean CheckQQ()
    {
        if (LitePal.count(Personel.class)>0)
            return true;
        return false;
    }

    /**
     * 删除QQ数据库
     */
    public void delete_QQ()
    {
        LitePal.deleteAll(Personel.class);
    }

    /**
     * 检查学生是否存了
     */
    public  boolean CheckStudent()
    {
        if (LitePal.count(StudentInfo.class) > 0)
            return true;
        return false;
    }
}
