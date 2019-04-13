package com.example.ysuselfstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.ysuselfstudy.adapter.SpacesItemDecoration;
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
    private List<ExamInfor> ExamList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SmartRefreshLayout smartRefreshLayout;
    private ExamAdapter examAdapter;
    private  ActionBar actionBar;
    private static final String TAG = "ExamActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        Toolbar toolbar=(androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);

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
        recyclerView.addItemDecoration(new SpacesItemDecoration(50));
        examAdapter=new ExamAdapter(ExamList);
        recyclerView.setAdapter(examAdapter);
    }

    @Override
    public void onBackPressed() {
        ExamList.clear();
        if(examAdapter!=null)
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
                ExamList.clear();
                StudentInfo studentInfo = LitePal.findFirst(StudentInfo.class);
                final String xuehao=studentInfo.getXuehao();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String exam="http://202.206.243.5/xskscx.aspx?xh=" + xuehao + "&xm=%B8%DF%BA%E3%D4%B4&gnmkdm=N121604";
                            String referer="http://202.206.243.5/xs_main.aspx?xh="+xuehao;
                            Document connection = Jsoup.connect(exam)
                                    .header("Cookie", AllString.Cookie)
                                    .referrer(referer)
                                    .data("xh",xuehao)
                                    .data("xm","%B8%DF%BA%E3%D4%B4")
                                    .data("gnmkdm","N121604")
                                    .post();
                            //如果还未评价。这是一个优化点
                            String aab=connection.body().text();
                            if(aab.length()==0)
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ExamActivity.this,"请登录教务系统进行教学评价",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                            //获取了所有爬取的信息。
                            Elements elements=connection.select("td");

                            int size=elements.size();
                            for (int i= 11 ; i<size ; i+=8)
                            {
                                String temp =elements.get(i).text();
                                if (!temp.equals(""))
                                {
                                    String target=elements.get(i-2).text();
                                    Log.d(TAG, "run: "+target);
                                    String location=elements.get(i+1).text();
                                    Log.d(TAG, "run: "+location);
                                    String number=elements.get(i+3).text();
                                    Log.d(TAG, "run: "+number);
                                    ExamInfor examInfor=new ExamInfor(target,temp,location,number);
                                    ExamList.add(examInfor);

                                }
                                else
                                {
                                    Log.d(TAG, "run: 停止了");
                                    break;
                                }
                            }
                            smartRefreshLayout.finishRefresh();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    actionBar.setTitle("本学期有 "+ExamList.size()+" 门考试");
                                    examAdapter.notifyDataSetChanged();
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
