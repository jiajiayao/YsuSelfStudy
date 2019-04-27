package com.example.ysuselfstudy;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;


public class PostMissingCard extends BaseActivity {
    EditText xuehao;
    EditText QQhao;
    EditText phone;
    Button postbutton;
    String Alias;
    String ContactQQ;
    String ContactPhone;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = "PostMissingCard";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_missing_card);
        initView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PostMissingCard.this.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        postbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alias=xuehao.getText().toString();
                ContactQQ=QQhao.getText().toString();
                ContactPhone=phone.getText().toString();
                Snackbar.make(v, "发送成功", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient okHttpClient=new OkHttpClient();
                        HashMap<String,String> map=new HashMap<>();
                        map.put("xuehao",Alias);
                        map.put("qq",ContactQQ);
                        map.put("phone",ContactPhone);

                        Gson gson=new Gson();
                        String data=gson.toJson(map);

                        RequestBody formbody=RequestBody.create(JSON,data);
                        Request request=new Request.Builder()
                                .post(formbody)
                                .url("http://39.96.163.218:8080/SelfStudy/GetMissingInfo")
                                .build();
                        okHttpClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                Log.d(TAG, "onResponse: "+response.body().string());
                            }
                        });
                    }
                }).start();
            }
        });
    }
    public void initView()
    {
        xuehao=findViewById(R.id.other_xuehao);
        QQhao = findViewById(R.id.my_qq);
        phone=findViewById(R.id.myphone);
        postbutton=findViewById(R.id.post);
    }
}
