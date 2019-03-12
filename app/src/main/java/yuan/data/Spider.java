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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.jar.JarException;

import javax.security.auth.login.LoginException;

public class Spider {
  private static final String TAG = "Spider";
  public static void  Search(final ToastListener toastListener)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i=1;i<13;i+=2)
                    {
                        Document response=
                                Jsoup.connect(AllString.YSU)
                                        .userAgent("Mozilla/5.0")
                                        .timeout(10 * 1000)
                                        .data("yxid","")
                                        .data("sjd",i+"")//这是第几节课的意思
                                        //   .data("dqxn","2018-2019")//当前学年
                                        // .data("dqxq","2")//当前学期
                                        //  .data("djz","2")//第几周
                                        // .data("xqj","4")//这是星期
                                        .data("show2","1")
                                        .data("Submit","按条件显示")
                                        .followRedirects(true)
                                        .post();
                        work(response,i);

                        /**
                         * 随后应该找到一共有多少页。
                         */
                        Elements NumberPage=response.select("font[color=#ff0000]");
                        int page=Integer.parseInt(NumberPage.get(1).text());
                        if(page%30>0)
                            page=page/30+1;
                        else
                            page/=30;
                        System.out.println(page);
                        for(int k=2;k<=page;k++)
                        {
                            boolean temp =true;
                            Document tiaozhuan;
                            while (temp)
                            {
                                tiaozhuan=
                                        Jsoup.connect(AllString.YSU)
                                                .userAgent("Mozilla/5.0")
                                                .timeout(10 * 1000)
                                                .data("show2","1")
                                                .data("yxid","")
                                                .data("sjd",i+"")
                                                .data("page",k+"")
                                                .data("Submit","确定")
                                                .followRedirects(true)
                                                .post();
                                if(tiaozhuan!=null)
                                {
                                    temp=false;
                                    work(tiaozhuan,i);
                                }
                            }
                        }
                    }
             if(toastListener!=null)
             {
                 toastListener.showToast();
                 Log.d(TAG, "run: 更新完毕");
             }

                }catch (Exception e)
                {
                    /**
                     * 这里填写超时逻辑
                     */
                    Log.e(TAG, "run: 超时错误"+e.toString());
                    toastListener.OnError();
                }

            }
        }).start();

    }

    /**
     * 这是向数据库中添加的操作。
     * @param document
     * @param time
     */

    private static void work(Document document,int time) {
        Elements classroom=document.select("td[bgcolor=#FFFFFF][width=89]");//检索出来全部是教室名字
        Elements location=document.select("td[bgcolor=#FFFFFF][width=111]");//检索出来地点
        Elements number=document.select("td[bgcolor=#FFFFFF][width=75]");

        for (int j=0;j<classroom.size();j++)
        {
           EmptyRoom room;
            if(!number.get(j).text().equals(""))
            {
              room=new EmptyRoom(classroom.get(j).text(),Integer.parseInt(number.get(j).text()),location.get(j).text(),time);
               // Log.d(TAG, "work: "+classroom.get(j).text()+" "+99+" "+location.get(j).text());
            }
                //System.out.println(classroom.get(j).text()+" "+number.get(j).text()+" "+location.get(j).text());
            else{
               // Log.d(TAG, "work: "+classroom.get(j).text()+" "+number.get(j).text()+" "+location.get(j).text());

                room=new EmptyRoom(classroom.get(j).text(),99,location.get(j).text(),time);
            }
            room.save();
        }
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
