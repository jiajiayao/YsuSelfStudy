package com.example.ysuselfstudy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.ysuselfstudy.database.DateBaseManager;
import com.ysuselfstudy.database.StudentInfo;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginOffice extends BaseActivity {
     private   ImageView imageView;
     private   EditText xuehao;
     private   EditText mima;
     private   EditText yanzhengma;
     private static final String TAG = "LoginOffice";
     public String LoginCookie;
     private SmartRefreshLayout smartRefreshLayout;
     private DateBaseManager dateBaseManager;
     private Intent received_intent;
     private int version;
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
            AllString.Cookie=LoginCookie;//赋值给全局变量

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
        Toolbar toolbar=(androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);

        //状态栏添加返回按钮
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(AllString.LoginTitle);
        }
        InitView();
        received_intent=getIntent();
        version = received_intent.getIntExtra("version", 1);
        ConnectToOffice();
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

    public void YsuSetAlias(String Alias)
    {
        MiPushClient.setAlias(LoginOffice.this,Alias,null);
        Log.d(TAG, "YsuSetAlias: 别名设置好了");
    }

    /**
     * 登录的点击事件
     */
    public void Login() {
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.setEnableRefresh(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String username=xuehao.getText().toString();
                        final String password=mima.getText().toString();
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
                                            refreshLayout.finishRefresh();
                                            yanzhengma.setText("");
                                            Toast.makeText(LoginOffice.this,"登录失败",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                else
                                {
                                    Log.d(TAG, "onResponse: 登录成功");
                                    AllString.Login=true;
                                    YsuSetAlias(username);
                                    dateBaseManager.UpdateStu(new StudentInfo(username,password));//更新数据库中学生的信息。
                                    refreshLayout.finishRefresh();
                                    Log.d(TAG, "onResponse: " + version);
                                    Intent intent = new Intent(LoginOffice.this, version == 1 ? GradeActivity.class : ExamActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                    }
                }).start();
            }
        });



    }
    private void InitView()
    {
        imageView =findViewById(R.id.change);
        xuehao =findViewById(R.id.xuehao);
        mima   =findViewById(R.id.password);
        yanzhengma  =findViewById(R.id.yanzhengma);
        smartRefreshLayout=findViewById(R.id.LoginRefresh);
        smartRefreshLayout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
        smartRefreshLayout.setDisableContentWhenRefresh(true);
        Login();
        dateBaseManager=new DateBaseManager();
        if (dateBaseManager.CheckPassword())
        {
            StudentInfo stu= LitePal.findFirst(StudentInfo.class);
            xuehao.setText(stu.getXuehao());
            mima.setText(stu.getPassword());
        }
    }
}
