package com.ysuselfstudy.network;

import android.util.Log;

import com.example.ysuselfstudy.AllString;
import com.ysuselfstudy.database.StudentInfo;
import com.ysuselfstudy.gradeadapter.GradeBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class GradeNet {
    private static final String TAG = "GradeNet";
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
                    .data("ddlXQ","1")//这里调整学期
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

}
