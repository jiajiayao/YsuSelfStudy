package com.example.ysuselfstudy;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;
import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

import yuan.data.DateBaseManager;
import yuan.data.EmptyRoom;
import yuan.data.RoomExAdapter;
import yuan.data.School;
import yuan.data.SchoolBuilding;
import yuan.data.Spider;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Tencent mTencent;
    private static ImageView image;
    private DrawerLayout mDrawerLayout;
    private static TextView TimeView;
    private Button loginButton;
    private UserInfo mUserInfo;
    private ImageView RoundImage;
    private View headerLayout;
    TextView Today;
    String temp;
    ArrayList<School> grouplist;
    ArrayList<List> childlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton=findViewById(R.id.login);

        NavigationView navigationView=findViewById(R.id.nav_view) ;
        headerLayout =navigationView.inflateHeaderView(R.layout.nav_header);
        RoundImage=(ImageView) headerLayout.findViewById(R.id.icon_round_image);
        LitePal.getDatabase();//创建数据库

        //实例化QQ

      loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTencent=Tencent.createInstance("101560830",getApplicationContext());
                if(!mTencent.isSessionValid())
                {
                    mTencent.login(MainActivity.this, "all",new BaseUiListener());
                }
            }
        });




        //检查用不用更新数据库
        DateBaseManager a=new DateBaseManager();
       if(!a.ChecekDate())
       {
           Spider.Search();
           /**
            * 这里还应该加一个东西，否则，用户不知道什么时候更新完成。
            */
       }

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //导航栏显式左滑图标
        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        }
        //每日必应图片
        image=(ImageView) findViewById(R.id.image);
        loadImage(image);

        //图片上显式的字
        Today=findViewById(R.id.text);
        Today.setText(AllString.Today);

        //时间的点击触发底部弹窗
        TimeView=(TextView) findViewById(R.id.showTime);
        TimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DemoPopup(MainActivity.this).showPopupWindow();
            }
        });

        initdata();
        final ExpandableListView expandableListView=(ExpandableListView) findViewById(R.id.expand_list);
        final RoomExAdapter roomExAdapter = new RoomExAdapter(this,
                R.layout.parent_item,
                R.layout.child_item,
                grouplist,
                childlist);
        expandableListView.setAdapter(roomExAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int count=roomExAdapter.getGroupCount();
                for(int i=0;i<count;i++)
                {
                    if(i!=groupPosition)
                        expandableListView.collapseGroup(i);
                }
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent,
                                        View v,
                                        int groupPosition,
                                        int childPosition,
                                        long id) {
                SchoolBuilding temp=(SchoolBuilding) childlist.get(groupPosition).get(childPosition);
                String where=temp.getBuildingName();
                Toast.makeText(parent.getContext(),"你点击了"+temp.getBuildingName(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,CardShow.class);
                intent.putExtra("where" ,where);
                startActivity(intent);
                return false;
            }
        });
    }
        /*
        * 加载必应每日一图，可以通过 Jsoup 来实现。
        * 占位图的设计?
        * */
    private void loadImage(final ImageView imageView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //子线程获取地址
               temp= Spider.SearchForBiYing();

                //切换到主线程进行更新
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(MainActivity.this).
                                load(temp).
                              //  placeholder(R.drawable.placeorder).
                                into(imageView);
                    }
                });
            }
        }).start();
        Glide.with(headerLayout.getContext()).load(R.mipmap.nav_icon).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(RoundImage);
    }

    private  void initdata()
    {

        grouplist=new ArrayList<>();
        childlist =new ArrayList<>();

        grouplist.add(new School("东校区"));
        grouplist.add(new School("西校区"));

        ArrayList xs =new ArrayList<>();
        xs.add(new SchoolBuilding("第一教学楼"));
        xs.add(new SchoolBuilding("第二教学楼"));
        xs.add(new SchoolBuilding("第三教学楼"));
        xs.add(new SchoolBuilding("第四教学楼"));
        xs.add(new SchoolBuilding("其他"));
        childlist.add(xs);

        ArrayList bg =new ArrayList<>();
        bg.add(new SchoolBuilding("第一教学楼"));
        bg.add(new SchoolBuilding("第二教学楼"));
        bg.add(new SchoolBuilding("第三教学楼"));
        bg.add(new SchoolBuilding("第四教学楼"));
        bg.add(new SchoolBuilding("第五教学楼"));
        bg.add(new SchoolBuilding("里仁教学楼"));
        bg.add(new SchoolBuilding("其他"));
        childlist.add(bg);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home :
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
                default:
        }
        return  true;
    }

    public  static  void SetTime(String name)
    {
        TimeView.setText(name);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
      super.onActivityResult(requestCode,resultCode,data);
        Tencent.onActivityResultData(requestCode,resultCode,data,new BaseUiListener());
    }

    /**
     * 腾讯登录的类
     */
    private class BaseUiListener implements IUiListener
    {
        @Override
        public void onComplete(Object response) {
            Log.d(TAG, "onComplete: " + response.toString());
            JSONObject obj = (JSONObject) response;
            try {

                String openID = obj.getString("openid");

                String accessToken = obj.getString("access_token");

                String expires = obj.getString("expires_in");

                mTencent.setOpenId(openID);

                mTencent.setAccessToken(accessToken, expires);

                QQToken qqToken = mTencent.getQQToken();

                mUserInfo = new UserInfo(getApplicationContext(), qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object o) {
                        Log.d(TAG,"登录成功"+o.toString());
                        JSONObject Mess=(JSONObject) o;
                        try {
                            String nickname=Mess.getString("nickname");
                            String touxiang=Mess.getString("figureurl_qq_2");
                            Glide.with(headerLayout.getContext()).load(touxiang).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(RoundImage);
                        }catch (Exception e)
                        {

                        }

                    }

                    @Override
                    public void onError(UiError uiError) {

                    }

                    @Override
                    public void onCancel() {

                    }
                });


            } catch (Exception e) {

            }
        }
        @Override
        public void onError(UiError uiError) {

        }

        @Override
        public void onCancel() {

        }
    }
}
