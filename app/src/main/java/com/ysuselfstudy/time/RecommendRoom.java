package com.ysuselfstudy.time;

/**
 * 1.本类实现地理定位。
 * 2. 实现时间的错位。
 *
 * 数据库中的时间是按照 1， 3 ，5 ，7 ，9 ，11存储的。
 */
public class RecommendRoom {
    public WhereWhen whereWhen=new WhereWhen();
    private static final double []TimeClock={0.0, 8.00, 9.35 , 10.05, 11.40, 13.30, 15.05, 15.35, 17.10, 18.10, 19.45, 20.05 ,21.40 };
    public WhereWhen obj(String time)
    {
        int BZ=Integer.parseInt(time.substring(0,2));
        int BX=Integer.parseInt(time.substring(3,5));
        int EZ=Integer.parseInt(time.substring(6,8));
        int EX=Integer.parseInt(time.substring(9,11));
        double begin=BZ+0.01*BX;
        double end  =EZ+0.01*EX;
        int chu=1,zhong=11;
        boolean a=true;
        for (int i=1;i<TimeClock.length;i++)
        {
            if(begin<TimeClock[i]&&a)
            {
                a=false;
                if(i%2==1)
                    chu=i;
                else
                    chu=i-1;
            }
            if(end<TimeClock[i])
            {
                if (i%2==1)
                 zhong=i;
                else
                    zhong=i-1;
                break;
            }
        }
        whereWhen.setBegin_time(chu);
        whereWhen.setEnd_time(zhong);
        return whereWhen;
    }
}
