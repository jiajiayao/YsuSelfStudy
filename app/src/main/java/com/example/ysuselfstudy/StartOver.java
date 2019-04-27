package com.example.ysuselfstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.view.View;

import com.jaredrummler.android.widget.AnimatedSvgView;
import com.ysuselfstudy.database.BiyingPic;
import com.ysuselfstudy.database.MipushInfor;
import com.ysuselfstudy.time.DateTime;

import org.litepal.LitePal;

import java.util.Calendar;

public class StartOver extends BaseActivity {
    public AnimatedSvgView animatedSvgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_over);

        //去掉了状态栏
        View decorView=getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);
        animatedSvgView=findViewById(R.id.animated_svg_view);
        setSvg(SVG.values()[0]);

        //特殊情况，更新时间巧合。可以这样做
        if(LitePal.count(BiyingPic.class)==0)
        {
            LitePal.deleteAll(MipushInfor.class);
            DateTime dateTime=new DateTime();
            Calendar c=Calendar.getInstance();
            int hour=c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            int secong=c.get(Calendar.SECOND);
            String time=dateTime.getMonth()+"月"+dateTime.getDate()+"日"+hour+"点"+minute+"分"+secong+"秒";
            MipushInfor mipushInfor=new MipushInfor(time,AllString.Hello);
            mipushInfor.save();
        }

        animatedSvgView.setOnStateChangeListener(new AnimatedSvgView.OnStateChangeListener() {
            @Override
            public void onStateChange(int state) {
                if(animatedSvgView.getState()==AnimatedSvgView.STATE_FINISHED)
                {
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void setSvg(SVG svg) {
        animatedSvgView.setGlyphStrings(svg.glyphs);
        animatedSvgView.setFillColors(svg.colors);
        animatedSvgView.setViewportSize(svg.width, svg.height);
        animatedSvgView.setTraceResidueColor(0x32000000);
        animatedSvgView.setTraceColors(svg.colors);
        animatedSvgView.rebuildGlyphData();
        animatedSvgView.start();
    }
}
