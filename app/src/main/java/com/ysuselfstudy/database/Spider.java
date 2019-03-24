package com.ysuselfstudy.database;

import android.util.Log;

import com.example.ysuselfstudy.AllString;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ysuselfstudy.database.DateBaseManager;
import com.ysuselfstudy.database.EmptyRoom;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Spider {
  private static final String TAG = "Spider";
  private  static DateBaseManager dateBaseManager=new DateBaseManager();
  public static void  Search()
    {
        //保证每次写入前都将数据库清空

        dateBaseManager.delete_EmptyRoom();
        Log.d(TAG, "Search: 爬虫开始工作");
        try
        {
            OkHttpClient okHttpClient=new OkHttpClient();
            Request request=new Request.Builder()
                    .url(AllString.YSU)
                    .build();
            Response response=okHttpClient.newCall(request).execute();
            String res=response.body().string();
            work(res);
        }catch (Exception e)
        {

        }
    }

    /**
     * 向教室数据库写数据
     * @param res
     */
    private static void work(String res) {
        Log.d(TAG, "work: 进来了");
        Gson gson=new Gson();
        List<EmptyRoom> list=gson.fromJson(res, new TypeToken<List<EmptyRoom>>(){}.getType());
        int zongliang=0;
        for (EmptyRoom room:list)
        {
            zongliang++;
            room.save();
        }
        Log.d(TAG, "work:完成 "+zongliang);
        dateBaseManager.setDate();
    }

    /**
     * 必应的图片
     * @return 必应图片的链接
     */
    public static String SearchForBiYing()
    {
        Elements element = null;
        try {
            Document document=Jsoup.connect(AllString.BiYing).get();
            element=document.select("body");
            Log.d(TAG, "SearchForBiYing: "+element.text());

        }catch (Exception e)
        {
            String jiuji="http://cn.bing.com/th?id=OHR.SpainRioTinto_ZH-CN9632593185_1920x1080.jpg&rf=NorthMale_1920x1081920x1080.jpg";
            return jiuji;
        }
        return element.text();
    }



}
