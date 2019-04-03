package com.example.ysuselfstudy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.ysuselfstudy.adapter.TimeLineAdapter;
import com.ysuselfstudy.database.MipushInfor;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class InfoActivity extends BaseActivity {
    public List<MipushInfor> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.info_toolbar);
        ImageView imageView_back = findViewById(R.id.background_img);
        ImageView touxiang = findViewById(R.id.touxiang);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Glide.with(this).load(AllString.qq_touxiang).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(touxiang);
        collapsingToolbarLayout.setTitle(AllString.nickname);
        RecyclerView recyclerView=findViewById(R.id.personal_info);
        init_data();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        TimeLineAdapter adapter = new TimeLineAdapter(list);
        recyclerView.setAdapter(adapter);

    }

    private void init_data() {
          List<MipushInfor> temp = LitePal.findAll(MipushInfor.class);
          list.clear();
        for (int i = temp.size()-1; i >=0; i--) {
            list.add(temp.get(i));
        }
    }
}
