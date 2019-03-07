package com.example.ysuselfstudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static ImageView image;
    TextView textView;
    TextView Today;
    String temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //每日必应图片
        image=(ImageView) findViewById(R.id.image);
        loadImage(image);

        //图片上显式的字
        Today=findViewById(R.id.text);
        Today.setText(AllString.Today);

        //时间的点击触发底部弹窗
        textView=(TextView) findViewById(R.id.showTime);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DemoPopup(MainActivity.this).showPopupWindow();
            }
        });

    }
        /*
        * 加载必应每日一图，可以通过 Jsoup 来实现。
        * 这里还没有准备好
        * */
    private void loadImage(final ImageView imageView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //子线程获取地址
               temp=Spider.SearchForBiYing();

                //切换到主线程进行更新
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(MainActivity.this ).load(temp).into(imageView);
                    }
                });
            }
        }).start();


    }
}
