package com.example.ysuselfstudy;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import yuan.data.CardAdapter;
import yuan.data.EmptyRoom;

public class CardShow extends AppCompatActivity {
    private static final String TAG = "CardShow";
    private List<EmptyRoom> RoomList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_show);
        Intent intent=getIntent();
        String data=intent.getStringExtra("where");

        Log.d(TAG, "onCreate: "+data);

        initData(data);
        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);//纵向滑动
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(50));
        CardAdapter cardAdapter=new CardAdapter(RoomList);//构造函数
        recyclerView.setAdapter(cardAdapter);

    }

    private void initData(String data) {
       /** List<EmptyRoom> cc=LitePal.where("location = ?",data).find(EmptyRoom.class);
        for(EmptyRoom ee :cc)
        {
            RoomList.add(ee);
        }*/
       Cursor cc=LitePal.findBySQL("select room,nums,location from EmptyRoom where location = ? group by room",data);
        Log.d(TAG, "initData: 行数"+cc.getCount());
        if(cc.moveToFirst())
        {
            do{
                String name=cc.getString(cc.getColumnIndex("room"));
                String numbers=cc.getString(cc.getColumnIndex("nums"));
                String location=cc.getString(cc.getColumnIndex("location"));
                EmptyRoom ee=new EmptyRoom(name,numbers,location);
                RoomList.add(ee);
            }while (cc.moveToNext());
        }
        cc.close();
    }

    @Override
    public void onBackPressed() {
        RoomList.clear();
        super.onBackPressed();
    }
}
