package com.example.ysuselfstudy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.ysuselfstudy.adapter.DemoPopup;
import com.ysuselfstudy.software.APKVersionCodeUtils;
import com.ysuselfstudy.time.RecommendRoom;
import com.ysuselfstudy.time.WhereWhen;
import com.ysuselfstudy.database.DateBaseManager;
import com.ysuselfstudy.adapter.RoomExAdapter;
import com.ysuselfstudy.database.School;
import com.ysuselfstudy.database.SchoolBuilding;
import com.ysuselfstudy.database.Spider;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private Tencent mTencent;
    private static ImageView image;
    private DrawerLayout mDrawerLayout;
    private static TextView TimeView;
    private UserInfo mUserInfo;
    private ImageView RoundImage;
    private View headerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecommendRoom recommendRoom=new RecommendRoom();
    String address;
    ArrayList<School> grouplist;
    ArrayList<List> childlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //左面的侧栏头部
        NavigationView navigationView=findViewById(R.id.nav_view) ;
        headerLayout =navigationView.inflateHeaderView(R.layout.nav_header);
        RoundImage=(ImageView) headerLayout.findViewById(R.id.icon_round_image);
        LitePal.getDatabase();//创建数据库

        RefreshLayout refreshLayout=(RefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
        refreshLayout.setDisableContentWhenRefresh(true);
        refreshLayout.autoRefresh();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.setEnableRefresh(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            //检查用不用更新数据库
                            DateBaseManager a=new DateBaseManager();
                            if(!a.ChecekDate())
                            {
                                Spider.Search();
                            }

                        }catch (Exception e)
                        {
                            Log.d(TAG, "run: "+e.toString());
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                               refreshLayout.finishRefresh();
                               Toast.makeText(MainActivity.this,"同步完成",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
        });

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

        //时间的点击触发底部弹窗
        TimeView=(TextView) findViewById(R.id.showTime);
        TimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DemoPopup(MainActivity.this).showPopupWindow();
            }
        });

        InitTime();

        initdata();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.grade:
                        if(!AllString.Login)//如果没有登录
                        {
                            Intent intent=new Intent(MainActivity.this,LoginOffice.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Intent intent=new Intent(MainActivity.this,ExamActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.update:
                      int versioncode= APKVersionCodeUtils.getVersionCode(MainActivity.this);
                        Log.d(TAG, "onNavigationItemSelected: 服务器版本"+Spider.GetVersion());
                        Log.d(TAG, "onNavigationItemSelected: 本地版本"+versioncode);
                      if(versioncode<Spider.GetVersion())
                      {
                          new Thread(new Runnable() {
                              @Override
                              public void run() {
                                  Log.d(TAG, "run: 准备下载");
                              }
                          }).start();
                      }
                      else  
                      {
                          Toast.makeText(MainActivity.this,"已经是最新版本",Toast.LENGTH_SHORT).show();
                      }
                        default:
                            break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        InitExpandableListView();

    }

    public void InitExpandableListView()
    {
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
                //这里调整为 WhereWhen 类型。
                WhereWhen whereWhen= recommendRoom.obj(GetTime());
                whereWhen.setWhere(temp.getBuildingName());
                Intent intent=new Intent(MainActivity.this,CardShow.class);
                intent.putExtra("where" ,whereWhen);
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
               address= Spider.SearchForBiYing();

                //切换到主线程进行更新
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(MainActivity.this).
                                load(address).
                                //placeholder(R.drawable.placeorder).
                                into(imageView);
                    }
                });
            }
        }).start();
        //加载 侧边栏的头像，默认为一条狗
        Glide.with(headerLayout.getContext()).load(R.mipmap.qq).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(RoundImage);
    }

    /**
     * 加载学校的各种建筑
     */
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
    //    xs.add(new SchoolBuilding("其他"));
        childlist.add(xs);

        ArrayList bg =new ArrayList<>();
        bg.add(new SchoolBuilding("西区第一教学楼"));
        bg.add(new SchoolBuilding("西区第二教学楼"));
        bg.add(new SchoolBuilding("西区第三教学楼"));
        bg.add(new SchoolBuilding("西区第四教学楼"));
        bg.add(new SchoolBuilding("西区第五教学楼"));
        bg.add(new SchoolBuilding("里仁教学楼"));
       // bg.add(new SchoolBuilding("其他"));
        childlist.add(bg);

    }


    /**
     * 打开侧边栏
     * @param item
     * @return
     */
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

    /**
     *一进去就设置时间
     */
    private  void InitTime()
    {
        Calendar calendar=Calendar.getInstance();
        int hours=calendar.get(Calendar.HOUR_OF_DAY);
        int minutes=calendar.get(Calendar.MINUTE);
        String temp=String.format("%02d:%02d-%02d:%02d",hours,minutes,hours,minutes);
        SetTime(temp);
    }
    /**
     * 供调用的设置时间的选项
     * @param name
     */
    public  static  void SetTime(String name)
    {
        TimeView.setText(name);
    }

    public static String GetTime()
    {
        return (String) TimeView.getText();
    }

    /**
     * 这是 QQ 登录必须要实现的。
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
      super.onActivityResult(requestCode,resultCode,data);
        Tencent.onActivityResultData(requestCode,resultCode,data,new BaseUiListener());
    }

    /**
     * 腾讯登录实现的接口类
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
                        Toast.makeText(MainActivity.this,"登录错误",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                      Toast.makeText(MainActivity.this,"登录取消",Toast.LENGTH_SHORT).show();
                    }
                });


            } catch (Exception e) {

            }
        }
        @Override
        public void onError(UiError uiError) {
            Toast.makeText(MainActivity.this,"登录错误",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(MainActivity.this,"登录取消",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 头像点击实现 QQ 登录
     * @param view
     */
    public  void  testLogin(View view)
    {
        mTencent=Tencent.createInstance("101560830",getApplicationContext());
        if(!mTencent.isSessionValid())
        {
            mTencent.login(MainActivity.this, "all",new BaseUiListener());
        }
    }
}
