package com.example.ysuselfstudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import yuan.data.CardAdapter;
import yuan.data.EmptyRoom;

public class CardShow extends AppCompatActivity {
    private List<EmptyRoom> RoomList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_show);
        initData();
        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);//纵向滑动
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(50));
        CardAdapter cardAdapter=new CardAdapter(RoomList);//构造函数
        recyclerView.setAdapter(cardAdapter);

    }

    private void initData() {
        RoomList.add(new EmptyRoom("第4教学楼",16,"东"));
        RoomList.add(new EmptyRoom("第6教学楼",16,"东"));
        RoomList.add(new EmptyRoom("第7教学楼",16,"东"));
    }
}
