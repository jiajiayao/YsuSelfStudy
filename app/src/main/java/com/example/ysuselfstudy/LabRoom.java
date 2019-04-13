package com.example.ysuselfstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ysuselfstudy.LabRoom.LabAdapter;
import com.ysuselfstudy.LabRoom.LabBean;
import com.ysuselfstudy.adapter.SpacesItemDecoration;
import com.ysuselfstudy.network.GradeNet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class LabRoom extends AppCompatActivity {
    public List<LabBean> list_lab = new ArrayList<>();
    public SmartRefreshLayout smartRefreshLayout;
    public RecyclerView recyclerView;
    public GradeNet gradeNet;
    public LabAdapter labAdapter;
    private static final String TAG = "LabRoom";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_room);
        gradeNet=new GradeNet();
        smartRefreshLayout = findViewById(R.id.lab_smart_refresh);
        smartRefreshLayout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
        smartRefreshLayout.setDisableContentWhenRefresh(true);
        SearchForLab();
        recyclerView = findViewById(R.id.lab_recyclerView);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(50));
    }

    private void SearchForLab() {
        smartRefreshLayout.autoRefresh();
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.setEnableRefresh(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                      list_lab=gradeNet.lab();
                      if(list_lab==null)
                      {
                          smartRefreshLayout.finishRefresh();
                          return;
                      }
                        Collections.sort(list_lab);
                        Log.d(TAG, "run: "+list_lab.size());
                      runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              labAdapter = new LabAdapter(list_lab);
                              recyclerView.setAdapter(labAdapter);
                          }
                      });

                        smartRefreshLayout.finishRefresh();
                    }
                }).start();
            }
        });
    }

}
