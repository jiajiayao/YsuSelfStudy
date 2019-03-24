package com.example.ysuselfstudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginOffice extends AppCompatActivity {
  private   ImageView imageView;
  private   EditText xuehao;
  private   EditText mima;
  private   EditText yanhengma;
  private   Button login;
    private static final String TAG = "LoginOffice";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_office);
        InitView();
        ConnectToOffice();
    }

    public void ChangeImage(View view) {

    }
    public void ConnectToOffice()
    {
        try{
            new Thread(new Runnable() {
                @Override
                public void run() {

                }
            }).start();


        }catch (Exception e)
        {
            Log.d(TAG, "ConnectToOffice: "+e.toString());
        }
    }
    public void Login(View view) {
    }
    private void InitView()
    {
        imageView =findViewById(R.id.change);
        xuehao =findViewById(R.id.xuehao);
        mima   =findViewById(R.id.password);
        yanhengma  =findViewById(R.id.yanzhengma);
        login    =findViewById(R.id.LoginOffice);
    }
}
