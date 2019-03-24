package com.example.ysuselfstudy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Connection;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginOffice extends AppCompatActivity {
     private   ImageView imageView;
     private   EditText xuehao;
     private   EditText mima;
     private   EditText yanzhengma;
     private   Button login;
     private static final String TAG = "LoginOffice";
     private  String LoginCookie;
    /**
     * 这是自动保存 Okhttp 的 Cookie
     */
    OkHttpClient client=new OkHttpClient.Builder().cookieJar(new CookieJar() {
        private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            cookieStore.put(url.host(), cookies);
            LoginCookie=cookies.get(cookies.size()-1).toString();
            LoginCookie=LoginCookie.substring(0,LoginCookie.indexOf(";"));//提取出来 Cookie
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url.host());
            return cookies != null ? cookies : new ArrayList<Cookie>();
        }
    }).build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_office);
        InitView();
        ConnectToOffice();
    }

    /**
     * 更换验证码
     * @param view
     */
    public void ChangeImage(View view) {
        ConnectToOffice();
    }

    /**
     * 初始化网络
     */
    public void ConnectToOffice()
    {
        try{
            new Thread(new Runnable() {
                @Override
                public void run() {

                    final Request request=new Request.Builder()
                            .url(AllString.YsuYanZhengMa)
                            .build();
                    Call call=client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Toast.makeText(getApplicationContext(),"连接不顺",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final byte[] picture=response.body().bytes();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.length);
                                    imageView.setImageBitmap(bitmap);
                                }
                            });
                        }
                    });
                }
            }).start();


        }catch (Exception e)
        {
            Log.d(TAG, "ConnectToOffice: "+e.toString());
        }
    }

    /**
     * 登录的点击事件
     * @param view
     */
    public void Login(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String username=xuehao.getText().toString();
                String password=mima.getText().toString();
                String lingpai=yanzhengma.getText().toString();
                RequestBody requestBody=new FormBody.Builder()
                        .add("__VIEWSTATE","dDwxNTMxMDk5Mzc0Ozs+cgOhsy/GUNsWPAGh+Vu0SCcW5Hw=")
                        .add("txtUserName",username)
                        .add("Textbox1","")
                        .add("TextBox2",password)
                        .add("RadioButtonList1","学生")
                        .add("Button1","")
                        .add("txtSecretCode",lingpai)
                        .add("lbLanguage","")
                        .add("hidPdrs","")
                        .add("hidsc","").build();
                Request request=new Request.Builder()
                        .url(AllString.YsuOffice)
                        .post(requestBody)
                        .build();
                Call call=client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    /**
                     * 这里是登录是否成功的处理逻辑
                     * @param call
                     * @param response
                     * @throws IOException
                     */
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        
                        if (response.request().url().toString().equals(AllString.YsuOffice))
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ConnectToOffice();
                                    Toast.makeText(LoginOffice.this,"登录失败",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else
                        {
                            Log.d(TAG, "onResponse: 登录成功");
                        }

                    }
                });
            }
        }).start();


    }
    private void InitView()
    {
        imageView =findViewById(R.id.change);
        xuehao =findViewById(R.id.xuehao);
        mima   =findViewById(R.id.password);
        yanzhengma  =findViewById(R.id.yanzhengma);
        login    =findViewById(R.id.LoginOffice);
    }
}
