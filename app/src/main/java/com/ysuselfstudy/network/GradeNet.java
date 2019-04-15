package com.ysuselfstudy.network;

import android.util.Log;

import com.example.ysuselfstudy.AllString;
import com.example.ysuselfstudy.LoginOffice;
import com.ysuselfstudy.LabRoom.LabBean;
import com.ysuselfstudy.database.StudentInfo;
import com.ysuselfstudy.gradeadapter.GradeBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GradeNet {
    private static final String TAG = "GradeNet";
    public OkHttpClient client;
    public LoginOffice loginOffice=new LoginOffice();
    int count=9;
    public  List<LabBean> list_lab = new ArrayList<>();
    public List<GradeBean> Connect_Mark()
    {
        StudentInfo studentInfo = LitePal.findFirst(StudentInfo.class);
        final String xuehao=studentInfo.getXuehao();
        List<GradeBean> list = new ArrayList<>();
        String URL_mark = "http://202.206.243.5/xscj_gc2.aspx?xh="+xuehao+"&xm=%B8%DF%BA%E3%D4%B4&gnmkdm=N121605";
        String referer = "http://202.206.243.5/xscj_gc2.aspx?xh="+xuehao+"&xm=%B8%DF%BA%E3%D4%B4&gnmkdm=N121605";
        try{
            Document document= Jsoup.connect(URL_mark)
                    .header("Cookie", AllString.Cookie)
                    .referrer(referer)
                    .header("Host","202.206.243.5")
                    .data("xh",xuehao)
                    .data("xm","%B8%DF%BA%E3%D4%B4")
                    .data("gnmkdm","N121605")
                    .data("ddlXN","2018-2019")
                    .data("ddlXQ","2")//这里调整学期
                    .data("Button1","%B0%B4%D1%A7%C6%DA%B2%E9%D1%AF")
                    .data("__VIEWSTATE","dDwxODI2NTc3MzMwO3Q8cDxsPHhoOz47bDwxNjAxMjAwMTAyMDg7Pj47bDxpPDE+Oz47bDx0PDtsPGk8MT47aTwzPjtpPDU+O2k8Nz47aTw5PjtpPDExPjtpPDEzPjtpPDE2PjtpPDI2PjtpPDI3PjtpPDI4PjtpPDM1PjtpPDM3PjtpPDM5PjtpPDQxPjtpPDQ1Pjs+O2w8dDxwPHA8bDxUZXh0Oz47bDzlrablj7fvvJoxNjAxMjAwMTAyMDg7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOWnk+WQje+8mumrmOaBkua6kDs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w85a2m6Zmi77ya5L+h5oGv56eR5a2m5LiO5bel56iL5a2m6ZmiOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzkuJPkuJrvvJo7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOi9r+S7tuW3peeoizs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w86KGM5pS/54+t77ya6L2v5Lu25bel56iLMTYtNzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8MjAxNjIwMDE7Pj47Pjs7Pjt0PHQ8cDxwPGw8RGF0YVRleHRGaWVsZDtEYXRhVmFsdWVGaWVsZDs+O2w8WE47WE47Pj47Pjt0PGk8ND47QDxcZTsyMDE4LTIwMTk7MjAxNy0yMDE4OzIwMTYtMjAxNzs+O0A8XGU7MjAxOC0yMDE5OzIwMTctMjAxODsyMDE2LTIwMTc7Pj47Pjs7Pjt0PHA8O3A8bDxvbmNsaWNrOz47bDx3aW5kb3cucHJpbnQoKVw7Oz4+Pjs7Pjt0PHA8O3A8bDxvbmNsaWNrOz47bDx3aW5kb3cuY2xvc2UoKVw7Oz4+Pjs7Pjt0PHA8cDxsPFZpc2libGU7PjtsPG88dD47Pj47Pjs7Pjt0PEAwPDtAMDw7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7OztAMDxwPGw8VmlzaWJsZTs+O2w8bzx0Pjs+Pjs7Ozs+Ozs+Ozs7Ozs7Ozs7Pjs7Pjt0PEAwPDs7Ozs7Ozs7Ozs+Ozs+O3Q8QDA8Ozs7Ozs7Ozs7Oz47Oz47dDw7bDxpPDA+O2k8MT47aTwyPjtpPDQ+Oz47bDx0PDtsPGk8MD47aTwxPjs+O2w8dDw7bDxpPDA+O2k8MT47PjtsPHQ8QDA8Ozs7Ozs7Ozs7Oz47Oz47dDxAMDw7Ozs7Ozs7Ozs7Pjs7Pjs+Pjt0PDtsPGk8MD47aTwxPjs+O2w8dDxAMDw7Ozs7Ozs7Ozs7Pjs7Pjt0PEAwPDs7Ozs7Ozs7Ozs+Ozs+Oz4+Oz4+O3Q8O2w8aTwwPjs+O2w8dDw7bDxpPDA+Oz47bDx0PEAwPDs7Ozs7Ozs7Ozs+Ozs+Oz4+Oz4+O3Q8O2w8aTwwPjtpPDE+Oz47bDx0PDtsPGk8MD47PjtsPHQ8QDA8cDxwPGw8VmlzaWJsZTs+O2w8bzxmPjs+Pjs+Ozs7Ozs7Ozs7Oz47Oz47Pj47dDw7bDxpPDA+Oz47bDx0PEAwPHA8cDxsPFZpc2libGU7PjtsPG88Zj47Pj47Pjs7Ozs7Ozs7Ozs+Ozs+Oz4+Oz4+O3Q8O2w8aTwwPjs+O2w8dDw7bDxpPDA+Oz47bDx0PHA8cDxsPFRleHQ7PjtsPFlTRFg7Pj47Pjs7Pjs+Pjs+Pjs+Pjt0PEAwPDs7Ozs7Ozs7Ozs+Ozs+Oz4+Oz4+Oz5cUcajVlzNRKZoEPUJnH2GxMYewQ==")
                    .post();
            String b = document.html();
            Elements info = document.select("table[class=datelist][width=100%][id=Datagrid1] td");

            for (int i=19;i<info.size();i+=16)
            {
               // Log.d(TAG, "Connect_Mark: "+i+"  " + info.get(i).text());
                GradeBean bean=new GradeBean();
                bean.setKemu(info.get(i).text());
                bean.setMark(info.get(i+4).text());
                list.add(bean);
            }

           // Log.d(TAG, "Connect_Mark:信息 " + b);
        }
        catch (Exception e)
        {
            Log.d(TAG, "出错了: " + e);
            return null;
        }
        return list;
    }

    public void PostJudgeMent(List<String> url,String refer)
    {
       client=loginOffice.back();
       FormBody.Builder builder=new FormBody.Builder();
        builder.add("xkkh", url.get(0));
               builder .add("xh","160120010205");
               builder .add("gnmkdm","N12141");
                FormBody formBody=builder.build();
        Request da=new Request.Builder()
                .post(formBody)
                .url(refer)
                .build();
        Call haha=client.newCall(da);
        haha.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String temp = response.body().string();
                Document document = Jsoup.parse(temp);
                Elements elements=document.select("input[name='__VIEWSTATE']");
                String VIEWSTATE=elements.get(0).attr("value");
                log(VIEWSTATE);
            }
        });
       /*     try {
                Document document = Jsoup.connect(refer)
                        .userAgent("Mozilla")
                        .header("Cookie", AllString.Cookie)
                        .header("Host", "202.206.243.5")
                        .referrer("http://202.206.243.5/xs_main.aspx?xh=160120010205")
                        .data("xkkh",url.get(0))
                        .data("xh","160120010205")
                        .data("gnmkdm","N12141")
                        .post();

                Elements elements=document.select("input[name='__VIEWSTATE']");
                String VIEWSTATE=elements.get(0).attr("value");
               log(document.html());
                //获得网络的VIEWSTATE
                Log.d(TAG, "PostJudgeMent: 提交后");
                Document document1 = Jsoup.connect(refer)
                        .userAgent("Mozilla")
                        .header("Cookie", AllString.Cookie)
                        .data("Upgrade-Insecure-Requests","1")
                        .header("Host", "202.206.243.5")
                        .referrer(refer)
                        .data("xkkh", url.get(0))
                        .data("xh", "160120010205")
                        .data("gnmkdm", "N12141")
                        .data("__EVENTTARGET", "")
                        .data("__EVENTARGUMENT","")
                        .data("__VIEWSTATE",VIEWSTATE)
                        .data("pjkc",url.get(0))
                        .data("DropDownList1","1__")
                        .data("DataGrid1:_ctl2:JS1","%C2%FA%D2%E2")
                        .data("DataGrid1:_ctl2:txtjs1","")
                        .data("DataGrid1:_ctl3:JS1","%BA%DC%C2%FA%D2%E2")
                        .data("DataGrid1:_ctl3:txtjs1","")
                        .data("DataGrid1:_ctl4:JS1","%C2%FA%D2%E2")
                        .data("DataGrid1:_ctl4:txtjs1","")
                        .data("DataGrid1:_ctl5:JS1","%BA%DC%C2%FA%D2%E2")
                        .data("DataGrid1:_ctl5:txtjs1","")
                        .data("DataGrid1:_ctl6:JS1","%BA%DC%C2%FA%D2%E2")
                        .data("DataGrid1:_ctl6:txtjs1","")
                        .data("DataGrid1:_ctl7:JS1","%C2%FA%D2%E2")
                        .data("DataGrid1:_ctl7:txtjs1","")
                        .data("DataGrid1:_ctl8:JS1","%BA%DC%C2%FA%D2%E2")
                        .data("DataGrid1:_ctl8:txtjs1","")
                        .data("pjxx","")
                        .data("txt1","")
                        .data("TextBox1","0")
                        .data("Button1","%B1%A3++%B4%E6")
                        .post();

                //提交网络的评价
                Elements elements1=document1.select("input[name='__VIEWSTATE']");
                String VIEWSTATE2=elements1.get(0).attr("value");
                log(document1.html());
                //获得界面的VIEWSTATE

                //提交界面的评价
           /*    
                Document document2 = Jsoup.connect(refer)
                        .header("Cookie", AllString.Cookie)
                        .header("Host", "202.206.243.5")
                        .referrer(refer)
                        .data("xkkh", url.get(0))
                        .data("xh", "160120010205")
                        .data("gnmkdm", "N12141")
                        .data("__EVENTTARGET", "")
                        .data("__EVENTARGUMENT","")
                        .data("__VIEWSTATE",VIEWSTATE2)
                        .data("pjkc",url.get(1))
                        .data("DropDownList1","1__")
                        .data("DataGrid1:_ctl2:JS1","%C2%FA%D2%E2")
                        .data("DataGrid1:_ctl2:txtjs1","")
                        .data("DataGrid1:_ctl3:JS1","%BA%DC%C2%FA%D2%E2")
                        .data("DataGrid1:_ctl3:txtjs1","")
                        .data("DataGrid1:_ctl4:JS1","%BA%DC%C2%FA%D2%E2")
                        .data("DataGrid1:_ctl4:txtjs1","")
                        .data("DataGrid1:_ctl5:JS1","%C2%FA%D2%E2")
                        .data("DataGrid1:_ctl5:txtjs1","")
                        .data("DataGrid1:_ctl6:JS1","%BA%DC%C2%FA%D2%E2")
                        .data("DataGrid1:_ctl6:txtjs1","")
                        .data("DataGrid1:_ctl7:JS1","%C2%FA%D2%E2")
                        .data("DataGrid1:_ctl7:txtjs1","")
                        .data("DataGrid1:_ctl8:JS1","%BA%DC%C2%FA%D2%E2")
                        .data("DataGrid1:_ctl8:txtjs1","")
                        .data("pjxx","")
                        .data("txt1","")
                        .data("TextBox1","0")
                        .data("Button1","%B1%A3++%B4%E6")
                        .post();

                //获得Py的VIEWSTATE
                Elements elements2=document2.select("input[name='__VIEWSTATE']");
                String VIEWSTATE3=elements2.get(0).attr("value");
                log(VIEWSTATE3);

                Document document3 = Jsoup.connect(refer)
                        .header("Cookie", AllString.Cookie)
                        .header("Host", "202.206.243.5")
                        .referrer(refer)
                        .data("xkkh", url.get(0))
                        .data("xh", "160120010205")
                        .data("gnmkdm", "N12141")
                        .data("__EVENTTARGET", "")
                        .data("__EVENTARGUMENT","")
                        .data("__VIEWSTATE",VIEWSTATE3)
                        .data("pjkc",url.get(2))
                        .data("DropDownList1","1__")
                        .data("DataGrid1:_ctl2:JS1","%C2%FA%D2%E2")
                        .data("DataGrid1:_ctl2:txtjs1","")
                        .data("DataGrid1:_ctl3:JS1","%C2%FA%D2%E2")
                        .data("DataGrid1:_ctl3:txtjs1","")
                        .data("DataGrid1:_ctl4:JS1","%C2%FA%D2%E2")
                        .data("DataGrid1:_ctl4:txtjs1","")
                        .data("DataGrid1:_ctl5:JS1","%BA%DC%C2%FA%D2%E2")
                        .data("DataGrid1:_ctl5:txtjs1","")
                        .data("DataGrid1:_ctl6:JS1","%BA%DC%C2%FA%D2%E2")
                        .data("DataGrid1:_ctl6:txtjs1","")
                        .data("DataGrid1:_ctl7:JS1","%BA%DC%C2%FA%D2%E2")
                        .data("DataGrid1:_ctl7:txtjs1","")
                        .data("DataGrid1:_ctl8:JS1","%BA%DC%C2%FA%D2%E2")
                        .data("DataGrid1:_ctl8:txtjs1","")
                        .data("pjxx","")
                        .data("txt1","")
                        .data("TextBox1","0")
                        .data("Button1","%B1%A3++%B4%E6")
                        .post();
                //保存了Py的评价、
                Elements elements3=document3.select("input[name='__VIEWSTATE']");
                String VIEWSTATE4=elements3.get(0).attr("value");
                log(VIEWSTATE4);
                Document document4 = Jsoup.connect(refer)
                        .header("Cookie", AllString.Cookie)
                        .header("Host", "202.206.243.5")
                        .referrer(refer)
                        .data("xkkh", url.get(0))
                        .data("xh", "160120010205")
                        .data("gnmkdm", "N12141")
                        .data("__EVENTTARGET", "")
                        .data("__EVENTARGUMENT","")
                        .data("__VIEWSTATE",VIEWSTATE4)
                        .data("pjkc",url.get(2))
                        .data("DropDownList1","1__")
                        .data("DataGrid1:_ctl2:JS1","%C2%FA%D2%E2")
                        .data("DataGrid1:_ctl2:txtjs1","")
                        .data("DataGrid1:_ctl3:JS1","%C2%FA%D2%E2")
                        .data("DataGrid1:_ctl3:txtjs1","")
                        .data("DataGrid1:_ctl4:JS1","%C2%FA%D2%E2")
                        .data("DataGrid1:_ctl4:txtjs1","")
                        .data("DataGrid1:_ctl5:JS1","%BA%DC%C2%FA%D2%E2")
                        .data("DataGrid1:_ctl5:txtjs1","")
                        .data("DataGrid1:_ctl6:JS1","%BA%DC%C2%FA%D2%E2")
                        .data("DataGrid1:_ctl6:txtjs1","")
                        .data("DataGrid1:_ctl7:JS1","%BA%DC%C2%FA%D2%E2")
                        .data("DataGrid1:_ctl7:txtjs1","")
                        .data("DataGrid1:_ctl8:JS1","%BA%DC%C2%FA%D2%E2")
                        .data("DataGrid1:_ctl8:txtjs1","")
                        .data("pjxx","")
                        .data("txt1","")
                        .data("TextBox1","0")
                        .data("Button2","+%CC%E1++%BD%BB+")
                        .post();
                        */
        //    }*/
          //  catch (Exception e)
          //  {

        //    }
        //    */


    }

    public void log(String out)
    {

        if (out.length() > 4000) {
            for (int i = 0; i < out.length(); i += 4000) {
                //当前截取的长度<总长度则继续截取最大的长度来打印
                if (i + 4000 < out.length()) {
                    Log.i("msg" + i, out.substring(i, i + 4000));
                } else {
                    //当前截取的长度已经超过了总长度，则打印出剩下的全部信息
                    Log.i("msg" + i, out.substring(i, out.length()));
                }
            }
        } else {
            //直接打印
            Log.i("msg", out);
        }
    }

    /**
     * 查询实验课的函数
     * @return 实验课List
     */
    public List<LabBean>  lab()
    {
        count=9;
        list_lab.clear();
        final OkHttpClient okHttpClient=new OkHttpClient.Builder().cookieJar(new CookieJar() {
            private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(url.host(), cookies);

            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url.host());
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        }).build();

        final Request request=new Request.Builder()
                .url(AllString.LAB_URL)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                return;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                for (int i = 1; i <10; i++) {
                    FormBody.Builder builder=new FormBody.Builder();
                    builder.add("currYearterm","2018-2019-2");//当前学期
                    builder.add("currTeachCourseCode", "%");
                    builder.add("page", i+"");
                    FormBody formBody=builder.build();

                    Request da=new Request.Builder()
                            .post(formBody)
                            .url("http://202.206.243.7/StuExpbook/book/bookResult.jsp")
                            .build();
                    Call haha=okHttpClient.newCall(da);
                    haha.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                                count--;
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String html=response.body().string();
                            Document document = Jsoup.parse(html);
                            Elements elements = document.select("table[class=table1] td");
                            //这不是线程。这是Jsoup处理的函数。
                            for (int i = 0; i < elements.size(); i+=12) {
                                Log.d(TAG, "onResponse: "+i+" "+elements.get(i).text());
                                LabBean labBean = new LabBean();
                                labBean.setProject(elements.get(i).text());
                                labBean.setContent(elements.get(i+1).text());
                                labBean.setTime(elements.get(i + 2).text());
                                labBean.setRoom(elements.get(i + 3).text());
                                list_lab.add(labBean);
                            }
                            count--;
                        }
                    });
                }
            }
        });

        while (count!=0)
        {
            Log.d(TAG, "lab: "+count);
        }
        Log.d(TAG, "最后"+list_lab.size());
        count=9;
        return list_lab;
    }
}
