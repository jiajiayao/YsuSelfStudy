package com.example.ysuselfstudy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.ysuselfstudy.gradeadapter.GradeBean;
import com.ysuselfstudy.gradeadapter.MarkAdapter;

import java.util.ArrayList;
import java.util.List;

public class GradeActivity extends AppCompatActivity {
    private static final String TAG = "GradeActivity";
    public List<GradeBean> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);
        RecyclerView recyclerView = findViewById(R.id.grade_RecyclrerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        MarkAdapter markAdapter = new MarkAdapter(list);
        recyclerView.setAdapter(markAdapter);

    }
}
