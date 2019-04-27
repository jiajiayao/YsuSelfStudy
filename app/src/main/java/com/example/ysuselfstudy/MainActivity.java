package com.example.ysuselfstudy;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import jp.wasabeef.glide.transformations.BlurTransformation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import com.tencent.bugly.Bugly;
import com.tencent.tauth.Tencent;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.xiaomi.mipush.sdk.MiPushClient;
import com.ysuselfstudy.adapter.DemoPopup;
import com.ysuselfstudy.database.BiyingPic;
import com.ysuselfstudy.tencent.BaseUiListener;
import com.ysuselfstudy.time.RecommendRoom;
import com.ysuselfstudy.time.WhereWhen;
import com.ysuselfstudy.database.DateBaseManager;
import com.ysuselfstudy.adapter.RoomExAdapter;
import com.ysuselfstudy.database.School;
import com.ysuselfstudy.database.SchoolBuilding;
import com.ysuselfstudy.database.Spider;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    public  static Context MainContext;
    private static ImageView image;
    private DrawerLayout mDrawerLayout;
    private static TextView TimeView;
    private static ImageView RoundImage;
    private static View headerLayout;
    private Tencent  mTencent;
    private static ImageView BackgroundImageView;
    private RecommendRoom recommendRoom=new RecommendRoom();//这个类与空教室查询有关
    String BiYingaddress;
    ArrayList<School> grouplist;
    ArrayList<List> childlist;
    public BaseUiListener baseUiListener;
    public Spider spider;
    private DateBaseManager dateBaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //左面的侧栏头部
        NavigationView navigationView=findViewById(R.id.nav_view) ;
        headerLayout =navigationView.inflateHeaderView(R.layout.nav_header);
        RoundImage=(ImageView) headerLayout.findViewById(R.id.icon_round_image);
        BackgroundImageView = (ImageView) headerLayout.findViewById(R.id.blur_image);
        LitePal.getDatabase();//创建数据库

        findViewById(R.id.drawer_layout).setFitsSystemWindows(false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        findViewById(R.id.toolbar).setPadding(0, 96, 0, 0);


        dateBaseManager = new DateBaseManager();
        spider=new Spider();//初始化爬虫，这里已经简化为只爬必应链接了



        //每日必应图片
        image=(ImageView) findViewById(R.id.image);

        //初始化必应图片和左边栏图片
        loadImage(image);


        baseUiListener=new BaseUiListener();
        baseUiListener.getActivity(MainActivity.this);
        baseUiListener.getContex(getApplicationContext());//初始化QQ登录
        MainContext=getApplicationContext();//获取本类的全局变量

        //注册Mipush通道
        if(shouldInit())
        {
            Log.d(TAG, "onCreate: 开始注册");
            MiPushClient.registerPush(MainActivity.this,AllString.MI_APP_ID,AllString.MI_APP_KEY);
        }


        /**
         * 下拉刷新
         */
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
                                //在search中会删除所有的空教室
                                spider.Search();
                            }

                        }catch (Exception e)
                        {
                            Log.d(TAG, "run: "+e.toString());
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                               refreshLayout.finishRefresh();
                               Toast.makeText(MainActivity.this,"完成!",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
        });


        Log.d(TAG, "onCreate: 尝试"+LitePal.count(BiyingPic.class));

        //导航栏显式左滑图标
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        }


       //初始化左边栏
        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);




        //时间的点击触发底部弹窗
        TimeView=(TextView) findViewById(R.id.showTime);
        TimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DemoPopup(MainActivity.this).showPopupWindow();
            }
        });

        initTime();
        initdata();

        //侧边栏点击事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.grade:
                        Intent intent_grade = new Intent(MainActivity.this, !AllString.Login ? LoginOffice.class : GradeActivity.class);
                        intent_grade.putExtra("version",1);;
                        startActivity(intent_grade);
                        break;
                    case R.id.exam:
                        Intent intent_exam= new Intent(MainActivity.this, !AllString.Login ? LoginOffice.class : ExamActivity.class);
                        intent_exam.putExtra("version", 2);
                        startActivity(intent_exam);
                        break;
                    case R.id.misscard:
                        Intent intent_misscard = new Intent(MainActivity.this, dateBaseManager.CheckStudent() ? PostMissingCard.class : LoginOffice.class);
                        intent_misscard.putExtra("version", 4);
                        startActivity(intent_misscard);
                        break;
                    case R.id.update:
                        Toast.makeText(MainActivity.this,"是最新版本",Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.xiaoli:
                        Intent intentxiaoli = new Intent(MainActivity.this, XiaoLiActivity.class);
                        startActivity(intentxiaoli);
                        break;
                    case R.id.lab:
                        Intent intentlab = new Intent(MainActivity.this, !AllString.Login ? LoginOffice.class :LabRoom.class);
                        intentlab.putExtra("version",3);;
                        startActivity(intentlab);

                        break;
                    case R.id.setting:
                       // Intent insten_setting = new Intent(MainActivity.this, SettingsActivity.class);
                        //startActivity(insten_setting);

                           baseUiListener.Loginout();
                           //恢复左边栏默认模样
                        Glide.with(headerLayout.getContext()).load(R.drawable.back)
                                .apply(bitmapTransform(new BlurTransformation(25, 3)))
                                .into(BackgroundImageView);
                           Glide.with(headerLayout.getContext()).load(R.mipmap.qq).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(RoundImage);
                        break;
                    case R.id.library :
                        Intent intentLibrary=new Intent(MainActivity.this,LibraryActivity.class);
                        startActivity(intentLibrary);
                        break;
                    case R.id.my_information://跳转到我的信息页。
                        if (mTencent!=null&& mTencent.isSessionValid()) {
                            Intent intentinfo = new Intent(MainActivity.this, InfoActivity.class);
                            startActivity(intentinfo);
                        }else {
                            View test=getWindow().getDecorView();
                            testLogin(test);
                        }
                        break;
                    case R.id.about:
                        Intent intent_about = new Intent(MainActivity.this, ScrollingActivity.class);
                        startActivity(intent_about);
                        break;
                        default:
                            break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        InitExpandableListView();
        Bugly.init(getApplicationContext(), AllString.Bugly_APPID, true);

    }

    /**
     * 加载扩展栏
     */
    public void InitExpandableListView()
    {
        final ExpandableListView expandableListView=(ExpandableListView) findViewById(R.id.expand_list);
        final RoomExAdapter roomExAdapter = new RoomExAdapter(this,
                R.layout.parent_item,
                R.layout.child_item,
                grouplist,
                childlist);
        expandableListView.setAdapter(roomExAdapter);
        /**
         * 点击实现关闭其他界面。
         */
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
                BiyingPic biyingPic = new BiyingPic();
                if(dateBaseManager.CheckPic()) {
                    biyingPic = LitePal.findLast(BiyingPic.class);
                    BiYingaddress = biyingPic.getUrl();

                    //切换到主线程进行更新
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(MainActivity.this).
                                    load(BiYingaddress)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(imageView);
                        }
                    });
                }
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(MainActivity.this).
                                    load(R.drawable.ic_error)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(imageView);
                        }
                    });
                }
            }
        }).start();
        //加载 侧边栏的头像，初始为 qq 企鹅
        Glide.with(headerLayout.getContext()).load(R.mipmap.qq).apply(bitmapTransform(new CircleCrop())).into(RoundImage);
        Glide.with(this).load(R.drawable.back)
                .apply(bitmapTransform(new BlurTransformation(25, 3)))
                .into(BackgroundImageView);
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
       // bg.add(new SchoolBuilding("西区第四教学楼"));
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
    private  void initTime()
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
     * 这是 qq 登录必须要实现的。
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
      super.onActivityResult(requestCode,resultCode,data);
        Tencent.onActivityResultData(requestCode,resultCode,data,baseUiListener);
    }
    /**
     * 头像点击实现 qq 登录
     * @param view
     */
    public  void  testLogin(View view)
    {
        mTencent=baseUiListener.getTencent();

        if(!mTencent.isSessionValid())
        {
            mTencent.login(MainActivity.this, "all",baseUiListener);
        }else {
            Intent intent = new Intent(MainActivity.this, InfoActivity.class);
            startActivity(intent);
        }

    }

    /**
     * 小米登录必备
     * @return
     */
    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }


    public static class YsuHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case AllString.BEGIN_ACCESS:
                    Log.d(TAG, "handleMessage: 收到");
                    Toast.makeText(MainContext,"开始同步",Toast.LENGTH_SHORT).show();
                    break;
                case AllString.BEGIN_STORE:
                    Toast.makeText(MainContext,"开始储存，时间依性能而定",Toast.LENGTH_SHORT).show();
                     break;
                case AllString.TENCENT_IMAGE:
                    String url=(String) msg.obj;
                    Glide.with(headerLayout.getContext()).load(url).apply(bitmapTransform(new CircleCrop())).into(RoundImage);
                    Glide.with(headerLayout.getContext()).load(url)
                            .apply(bitmapTransform(new BlurTransformation(25, 3)))
                            .into(BackgroundImageView);
                    break;
                default:
                    break;
            }
        }
    }

}
