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

import heng.others.WhereWhen;
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
        WhereWhen getWhere=( WhereWhen)getIntent().getSerializableExtra("where");
        Log.d(TAG, "onCreate: 哪里"+getWhere.getWhere());
        Log.d(TAG, "onCreate: 哪里启示"+getWhere.getBegin_time());
        Log.d(TAG, "onCreate: 哪里结束"+getWhere.getEnd_time());

        initData(getWhere);
        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);//纵向滑动
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(50));
        CardAdapter cardAdapter=new CardAdapter(RoomList);//构造函数
        recyclerView.setAdapter(cardAdapter);

    }

    private void initData(WhereWhen obj) {
       /** List<EmptyRoom> cc=LitePal.where("location = ?",data).find(EmptyRoom.class);
        for(EmptyRoom ee :cc)
        {
            RoomList.add(ee);
        }*/
       String position=obj.getWhere();
       String begin=obj.getBegin_time()+"";
       String end=obj.getEnd_time()+"";
       Cursor cc=LitePal.findBySQL("select room,nums,location from EmptyRoom where location = ? and time>=? and time <=? group by room",position,begin,end);
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
