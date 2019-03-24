package com.example.ysuselfstudy;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import com.ysuselfstudy.adapter.SpacesItemDecoration;
import com.ysuselfstudy.time.WhereWhen;
import com.ysuselfstudy.adapter.CardAdapter;
import com.ysuselfstudy.database.EmptyRoom;

public class CardShow extends BaseActivity {
    private static final String TAG = "CardShow";
    private List<EmptyRoom> RoomList=new ArrayList<>();
    private CardAdapter cardAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_show);
        toolbar=(Toolbar) findViewById(R.id.toolbar);

        Intent intent=getIntent();
        WhereWhen getWhere=( WhereWhen)getIntent().getSerializableExtra("where");

        initData(getWhere);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);//纵向滑动
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(50));
        cardAdapter=new CardAdapter(RoomList);//构造函数
        recyclerView.setAdapter(cardAdapter);

    }

    private void initData(WhereWhen obj) {
       String position=obj.getWhere();
       int  begin=obj.getBegin_time();
       int  end=obj.getEnd_time();
       String sql=" select room,nums,location from EmptyRoom where location = '"+position+"' and time="+begin+" ";
       for (int i=begin+2;i<=end;i+=2)
       {
          sql+="intersect select room,nums,location from EmptyRoom where location = '"+position+"' and time="+i+" ";
       }
       Cursor cc=LitePal.findBySQL(sql);
       int NumsOfClassroom=0;
        if(cc.moveToFirst())
        {
            do{
                NumsOfClassroom++;
                String name=cc.getString(cc.getColumnIndex("room"));
                String numbers=cc.getString(cc.getColumnIndex("nums"));
                String location=cc.getString(cc.getColumnIndex("location"));
                EmptyRoom ee=new EmptyRoom(name,numbers,location);
                RoomList.add(ee);
            }while (cc.moveToNext());
        }
        cc.close();
        //更新教室数量
        toolbar.setTitle("有 "+NumsOfClassroom+" 间教室");
    }

    @Override
    public void onBackPressed() {
        RoomList.clear();
        //返回时务必要更新一下适配器
        cardAdapter.notifyDataSetChanged();
        super.onBackPressed();
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
