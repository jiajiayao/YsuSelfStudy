package yuan.data;

import android.app.Notification;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.ysuselfstudy.AllString;
import com.example.ysuselfstudy.MainActivity;
import com.example.ysuselfstudy.ToastListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.jar.JarException;

import javax.security.auth.login.LoginException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Spider {
  private static final String TAG = "Spider";
  private  static  DateBaseManager dateBaseManager=new DateBaseManager();
  public static void  Search()
    {
        //保证每次写入前都将数据库清空

        dateBaseManager.delete_EmptyRoom();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    OkHttpClient okHttpClient=new OkHttpClient();
                    Request request=new Request.Builder()
                            .url("http://10.2.55.32:8080/SelfStudy/TransRoom")
                            .build();
                    Response response=okHttpClient.newCall(request).execute();
                    String res=response.body().string();
                    work(res);
                }catch (Exception e)
                {

                }
            }
        }).start();
    }

    /**
     * 向教室数据库写数据
     * @param res
     */
    private static void work(String res) {
        Gson gson=new Gson();
        List<EmptyRoom> list=gson.fromJson(res,new TypeToken<List<EmptyRoom>>(){}.getType());
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
