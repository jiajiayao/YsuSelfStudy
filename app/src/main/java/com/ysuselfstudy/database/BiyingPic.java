package com.ysuselfstudy.database;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

public class BiyingPic extends LitePalSupport {
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    String url;

}
