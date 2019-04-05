package com.example.ysuselfstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ysuselfstudy.adapter.SpacesItemDecoration;
import com.ysuselfstudy.gradeadapter.GradeBean;
import com.ysuselfstudy.gradeadapter.MarkAdapter;
import com.ysuselfstudy.network.GradeNet;

import java.util.ArrayList;
import java.util.List;

public class GradeActivity extends AppCompatActivity {
    private static final String TAG = "GradeActivity";
    public List<GradeBean> list = new ArrayList<>();
    GradeNet gradeNet = new GradeNet();
    RecyclerView recyclerView;
    MarkAdapter markAdapter;
    ActionBar actionBar;
    SmartRefreshLayout smartRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);
        Toolbar  toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        smartRefreshLayout = findViewById(R.id.grade_smart_refresh);
        smartRefreshLayout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
        smartRefreshLayout.setDisableContentWhenRefresh(true);
        SearchForGrade();

        recyclerView = findViewById(R.id.grade_RecyclrerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(50));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id .home:
                onBackPressed();
                break;
                default:
                    break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        list.clear();
        markAdapter.notifyDataSetChanged();
        super.onBackPressed();
    }

    private void SearchForGrade() {
        smartRefreshLayout.autoRefresh();
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.setEnableRefresh(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        list= gradeNet.Connect_Mark();
                        smartRefreshLayout.finishRefresh();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                markAdapter = new MarkAdapter(list, GradeActivity.this);
                                recyclerView.setAdapter(markAdapter);
                                actionBar.setTitle("出了 "+list.size()+" 门成绩");
                            }
                        });
                    }
                }).start();

            }
        });
    }
}
