/**
 * 本类为QQ登录的有关类。目前还缺少 Session 的持久化。
 * 应用退出后，头像会清空。
 *
 */



package com.ysuselfstudy.tencent;
import android.app.Activity;
import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.example.ysuselfstudy.AllString;
import com.example.ysuselfstudy.MainActivity;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

public class BaseUiListener implements IUiListener
{
    private static final String TAG = "BaseUiListener";
    public Tencent mTencent;
    public UserInfo mUserInfo;
    public View headerLayout;
    public Context context;
    public Activity activity;
    public MainActivity mainActivity;
    @Override
    public void onComplete(Object response) {
        Log.d(TAG, "onComplete: " + response.toString());
        JSONObject obj = (JSONObject) response;
        try {

            String openID = obj.getString("openid");

            String accessToken = obj.getString("access_token");

            String expires = obj.getString("expires_in");

            mTencent.setOpenId(openID);

            mTencent.setAccessToken(accessToken, expires);//这里应该持久化保存
            Log.d(TAG, "onComplete: 设置完毕");
            QQToken qqToken = mTencent.getQQToken();

            mUserInfo = new UserInfo(activity, qqToken);
            mUserInfo.getUserInfo(new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    Log.d(TAG,"登录成功"+o.toString());
                    JSONObject Mess=(JSONObject) o;
                    try {
                        String nickname=Mess.getString("nickname");
                        String touxiang=Mess.getString("figureurl_qq_2");

                       MainActivity.YsuHandler ui=new MainActivity.YsuHandler();
                        Message a=new Message();
                        a.what=AllString.TENCENT_IMAGE;
                        a.obj=touxiang;
                        ui.sendMessage(a);
                    }catch (Exception e)
                    {
                        Log.d(TAG, "onComplete:mUserInfo "+e.toString());
                    }

                }

                @Override
                public void onError(UiError uiError) {
                    Toast.makeText(context,"登录错误",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel() {
                    Toast.makeText(context,"登录取消",Toast.LENGTH_SHORT).show();
                }
            });


        } catch (Exception e) {
            Log.d(TAG, "onComplete: 错误"+e.toString());
        }
    }
    @Override
    public void onError(UiError uiError) {
        Toast.makeText(context,"登录错误",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel() {
        Toast.makeText(context,"登录取消",Toast.LENGTH_SHORT).show();
    }

    public void getActivity(Activity activity)
    {
        this.activity=activity;
    }

    public void getContex(Context context)
    {
        this.context=context;
        mTencent=Tencent.createInstance(AllString.TENCENT_APPID,this.context);
    }

    public Tencent getTencent()
    {
        return mTencent;
    }
}
