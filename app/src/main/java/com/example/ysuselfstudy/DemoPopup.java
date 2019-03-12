package com.example.ysuselfstudy;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import razerdp.basepopup.BasePopupWindow;

public class DemoPopup extends BasePopupWindow {
    TimePicker BeginTime;
    TimePicker EndTime;
    Button button=findViewById(R.id.certify);
        public DemoPopup(final Context context)
        {
            super(context);
            setPopupGravity(Gravity.BOTTOM);
            bindEvent();
/*
* 底部弹出栏的确认按钮
* */
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String time=BeginTime.getHour()+":"+BeginTime.getMinute()+"-"+EndTime.getHour()+":"+EndTime.getMinute();
                    MainActivity.SetTime(time);
                    onBackPressed();
                }
            });
        }
/*
* TimePicker 的应用
* */
    private void bindEvent() {
        BeginTime= findViewById(R.id.BeginTime);
        BeginTime.setIs24HourView(true);

        EndTime=findViewById(R.id.EndTime);
        EndTime.setIs24HourView(true);
        BeginTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if(EndTime.getHour()<BeginTime.getHour())
                {
                    EndTime.setHour(BeginTime.getHour());
                }
                else if(EndTime.getHour()==BeginTime.getHour())
                    if(EndTime.getMinute()<=BeginTime.getMinute())
                        EndTime.setMinute(BeginTime.getMinute()-1);

            }
        });
        EndTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if(EndTime.getHour()<BeginTime.getHour())
                  EndTime.setHour(BeginTime.getHour());
                else if(EndTime.getHour()==BeginTime.getHour())
                    if(EndTime.getMinute()<=BeginTime.getMinute())
                          EndTime.setMinute(BeginTime.getMinute()+1);
            }
        });
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.layout);
    }

}
