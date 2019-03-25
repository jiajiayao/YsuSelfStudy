package com.example.ysuselfstudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ysuselfstudy.examadapter.ExamAdapter;
import com.ysuselfstudy.examadapter.ExamInfor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ExamActivity extends AppCompatActivity {
    private List<ExamInfor> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private ExamAdapter examAdapter;
    private static final String TAG = "ExamActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        //只是测试
        ExamInfor examInfor=new ExamInfor("政治考试","下午三四节","西区教学楼","10");
        list.add(examInfor);
        recyclerView = findViewById(R.id.exam_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        examAdapter=new ExamAdapter(list);
        recyclerView.setAdapter(examAdapter);
    }

    @Override
    public void onBackPressed() {
        list.clear();
        examAdapter.notifyDataSetChanged();
        super.onBackPressed();
    }
    public void SearchForExam()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String kaoshi="http://202.206.243.5/xskscx.aspx?xh=160120010208&xm=%B8%DF%BA%E3%D4%B4&gnmkdm=N121604";
                    String shouye="http://202.206.243.5/xs_main.aspx?xh=160120010208";
                    Log.d(TAG, "onClick: 检查");
                    Document connection = Jsoup.connect(kaoshi)
                            .header("Cookie", AllString.Cookie)
                            .referrer(shouye)
                            .data("xh","160120010208")
                            .data("xm","%B8%DF%BA%E3%D4%B4")
                            .data("gnmkdm","N121604")
                            .post();
                    Log.d(TAG, "onClick: "+connection.body());

                    Elements elements=connection.select("td");
                    int size=elements.size();
                    for (int i= 0 ; i<size ; i++)
                    {
                        String temp =elements.get(i).text();
                        //  if (!temp.equals(""))
                        Log.d(TAG, "run: "+temp);
                    }
                }
                catch (Exception e)
                {
                    Log.d(TAG, "onClick: "+e.toString());
                }
            }
        }).start();


    }
}
