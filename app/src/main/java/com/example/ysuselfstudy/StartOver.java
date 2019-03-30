package com.example.ysuselfstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.view.View;

import com.jaredrummler.android.widget.AnimatedSvgView;

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
