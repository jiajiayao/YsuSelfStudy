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
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Spider {
  private static final String TAG = "Spider";
  public MainActivity.YsuHandler tt=new MainActivity.YsuHandler();
  private static DateBaseManager dateBaseManager=new DateBaseManager();

    /**
     * 页面爬取空教室
     */
  public void  Search(Context context)
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
            Message msg2=new Message();
            msg2.what=AllString.BEGIN_STORE;
            tt.sendMessage(msg2);
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
        Gson gson=new Gson();
        List<EmptyRoom> list=gson.fromJson(res, new TypeToken<List<EmptyRoom>>(){}.getType());
        Log.d(TAG, "work: 开始存储");
        for (EmptyRoom room:list)
        {
            room.save();
        }
        Log.d(TAG, "work:完成 "+list.size());
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

        }catch (Exception e)
        {
            String jiuji="http://cn.bing.com/th?id=OHR.SpainRioTinto_ZH-CN9632593185_1920x1080.jpg&rf=NorthMale_1920x1081920x1080.jpg";
            return jiuji;
        }
        return element.text();
    }

    /**
     * 获取服务器上最新版的信息
     * @return 服务器最新编号
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
