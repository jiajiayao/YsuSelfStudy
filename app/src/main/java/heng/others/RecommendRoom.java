package heng.others;

/**
 * 1.本类实现地理定位。
 * 2. 实现时间的错位。
 *
 * 数据库中的时间是按照 1， 3 ，5 ，7 ，9 ，11存储的。
 */
public class RecommendRoom {
    public WhereWhen whereWhen=new WhereWhen();
    private static final double []TimeClock={8.00, 9.35 , 10.05, 11.40, 13.30, 15.05, 15.35, 17.10, 18.10, 19.45, 20.05 ,21.40 };

}
