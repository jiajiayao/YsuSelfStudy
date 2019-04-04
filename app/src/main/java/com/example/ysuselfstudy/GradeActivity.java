package com.example.ysuselfstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class GradeActivity extends AppCompatActivity {
    private static final String TAG = "GradeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);
        Log.d(TAG, "onCreate: 成绩界面");
    }
}
