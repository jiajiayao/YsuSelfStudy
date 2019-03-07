package com.example.ysuselfstudy;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Spider {
  private static final String TAG = "Spider";
    public void Search()
    {
        try {
            /*
            * Jsoup 的更新功能
            * 因为涉及到网络，因此需要开启一个新的线程
            *
             */
            Document response=
                    Jsoup.connect(AllString.YSU)
                            .userAgent("Mozilla/5.0")
                            .timeout(10 * 1000)
                            .data("yxid","")
                            .data("sjd","1") //这是第几节课的意思
                            .data("show2","1")
                            .data("Submit","按条件显示")
                            .followRedirects(true)
                            .post();
        }
        catch (Exception e)
        {

        }
    }

    public static String SearchForBiYing()
    {
        Elements element = null;
        try {
            Document document=Jsoup.connect(AllString.BiYing).get();
            element=document.select("body");
            Log.d(TAG, "SearchForBiYing: "+element.text());

        }catch (Exception e)
        {

        }
        return element.text();
    }
}
