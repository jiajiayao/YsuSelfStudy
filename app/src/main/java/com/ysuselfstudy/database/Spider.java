package com.ysuselfstudy.database;

import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.ysuselfstudy.AllString;
import com.example.ysuselfstudy.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.litepal.LitePal;

import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Spider {
  private static final String TAG = "Spider";
  public MainActivity.YsuHandler tt=new MainActivity.YsuHandler();
  private DateBaseManager dateBaseManager=new DateBaseManager();

    /**
     * 从服务器爬取空教室
     */
  public void  Search()
    {
        //保证每次写入前都将数据库清空
        dateBaseManager.delete_EmptyRoom();

        Message msg1=new Message();
        msg1.what=AllString.BEGIN_ACCESS;
        tt.sendMessage(msg1);
        try
        {
            OkHttpClient okHttpClient=new OkHttpClient();
            Request request=new Request.Builder()
                    .url(AllString.YSU)
                    .build();
            Log.d(TAG, "Search: 准备执行");
            Response response=okHttpClient.newCall(request).execute();
            String res=response.body().string();
            work(res);
        }catch (Exception e)
        {
            Log.d(TAG, "Search: "+e.toString());
        }
    }

    /**
     * 向教室数据库写数据
     * @param res
     */
    private void work(String res) {
        SearchForBiYing();
        Gson gson=new Gson();
        List<EmptyRoom> list=gson.fromJson(res, new TypeToken<List<EmptyRoom>>(){}.getType());
        Log.d(TAG, "work: 开始存储");
        LitePal.saveAll(list);
        Log.d(TAG, "work:完成 "+list.size());
        dateBaseManager.setDate();
    }

    /**
     * 必应的图片
     * @return 必应图片的链接
     */
    public static void SearchForBiYing()
    {
        Elements element = null;
        try {
            Document document=Jsoup.connect(AllString.BiYing).get();
            element=document.select("body");

        }catch (Exception e)
        {
            Log.d(TAG, "SearchForBiYing: "+e.toString());
        }
        BiyingPic biyingPic=new BiyingPic();
       if(element==null) return;
        biyingPic.setUrl(element.text());
        biyingPic.save();
    }

    /**
     * 获取服务器上最新版的信息
     * @return 服务器最新编号
     * 目前已经被废弃
     */
    public static int GetVersion()
    {
        int version=1;
        try
        {
            OkHttpClient okHttpClient=new OkHttpClient();
            Request request=new Request.Builder()
                    .url(AllString.UpdateUrl)
                    .build();
            Response response=okHttpClient.newCall(request).execute();
            String temp=response.body().string();
            JSONObject jsonObject=new JSONObject(temp);
            version= jsonObject.getInt("version");
        }catch (Exception e)
        {
            Log.d(TAG, "GetVersion: "+e.toString());
        }
        return version;
    }

}
