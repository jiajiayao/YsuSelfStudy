package com.ysuselfstudy.tencent;

import org.litepal.crud.LitePalSupport;

public class Personel extends LitePalSupport {
    public String getOpenId() {
        return OpenId;
    }

    public void setOpenId(String openId) {
        OpenId = openId;
    }

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String accessToken) {
        AccessToken = accessToken;
    }

    public String OpenId;
    public String AccessToken;

    public String getQQname() {
        return QQname;
    }

    public void setQQname(String QQname) {
        this.QQname = QQname;
    }

    public String getQQtouxiang() {
        return QQtouxiang;
    }

    public void setQQtouxiang(String QQtouxiang) {
        this.QQtouxiang = QQtouxiang;
    }

    public String QQname;
    public String QQtouxiang;

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String expires;
}
