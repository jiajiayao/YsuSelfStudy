package com.example.ysuselfstudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ysuselfstudy.examadapter.ExamAdapter;
import com.ysuselfstudy.examadapter.ExamInfor;

import java.util.ArrayList;
import java.util.List;

public class ExamActivity extends AppCompatActivity {
    private List<ExamInfor> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private ExamAdapter examAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

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
}
