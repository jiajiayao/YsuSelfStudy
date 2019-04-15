package com.example.ysuselfstudy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class ScrollingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=2051183243";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }
                catch (Exception e)
                {
                    Snackbar.make(view, "请先安装QQ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });
    }
}
