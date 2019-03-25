package com.example.ysuselfstudy;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ysuselfstudy.database.StudentInfo;
import com.ysuselfstudy.examadapter.ExamAdapter;
import com.ysuselfstudy.examadapter.ExamInfor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class ExamActivity extends BaseActivity {
    private List<ExamInfor> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private SmartRefreshLayout smartRefreshLayout;
    private ExamAdapter examAdapter;
    private  ActionBar actionBar;
    private static final String TAG = "ExamActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        Toolbar toolbar=(android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

        //状态栏添加返回按钮
        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        smartRefreshLayout = findViewById(R.id.exam_smart_refresh);
        smartRefreshLayout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
        smartRefreshLayout.setDisableContentWhenRefresh(true);
        SearchForExam();


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
        smartRefreshLayout.autoRefresh();
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.setEnableRefresh(true);
                StudentInfo studentInfo = LitePal.findFirst(StudentInfo.class);
                final String xuehao=studentInfo.getXuehao();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String exam="http://202.206.243.5/xskscx.aspx?xh=" + xuehao + "&xm=%B8%DF%BA%E3%D4%B4&gnmkdm=N121604";
                            String referer="http://202.206.243.5/xs_main.aspx?xh="+xuehao;
                            Log.d(TAG, "onClick: 检查");
                            Document connection = Jsoup.connect(exam)
                                    .header("Cookie", AllString.Cookie)
                                    .referrer(referer)
                                    .data("xh","160120010208")
                                    .data("xm","%B8%DF%BA%E3%D4%B4")
                                    .data("gnmkdm","N121604")
                                    .post();
                            //获取了所有爬取的信息。
                            Elements elements=connection.select("td");
                            int size=elements.size();
                            for (int i= 11 ; i<size ; i+=8)
                            {
                                String temp =elements.get(i).text();
                                if (!temp.equals(""))
                                {
                                    String target=elements.get(i-2).text();
                                    String location=elements.get(i+1).text();
                                    String number=elements.get(i+3).text();
                                    ExamInfor examInfor=new ExamInfor(target,temp,location,number);
                                    list.add(examInfor);
                                }
                                else
                                {
                                    break;
                                }
                            }
                            smartRefreshLayout.finishRefresh();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    actionBar.setTitle("近期有 "+list.size()+" 门考试");
                                }
                            });
                        }
                        catch (Exception e)
                        {
                            Log.d(TAG, "onClick: "+e.toString());
                        }
                    }
                }).start();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
