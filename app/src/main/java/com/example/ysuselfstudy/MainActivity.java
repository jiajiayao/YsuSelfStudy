package com.example.ysuselfstudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Button button;
    TextView textView;
    TimePicker timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=(ImageView) findViewById(R.id.image);
        loadImage(imageView);
        textView=(TextView) findViewById(R.id.showTime);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Toast.makeText(MainActivity.this,"好了",Toast.LENGTH_SHORT).show();
                new DemoPopup(MainActivity.this).showPopupWindow();
            }
        });
    }
        /*
        * 加载必应每日一图，可以通过 Jsoup 来实现。
        * 这里还没有准备好
        * */
    private void loadImage(ImageView imageView) {

        String url = "http://cn.bing.com/az/hprichbg/rb/BrittlebushBloom_ZH-CN9198170508_1920x1080.jpg";
        Glide.with(this ).load(url).into(imageView);
    }
}
