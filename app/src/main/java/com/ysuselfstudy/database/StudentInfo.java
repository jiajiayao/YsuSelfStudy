package com.ysuselfstudy.database;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

public class StudentInfo extends LitePalSupport {
    public StudentInfo(String name,String mima)
    {
        this.xuehao=name;
        this.password=mima;
    }
    private String xuehao;

    public String getXuehao() {
        return xuehao;
    }

    public void setXuehao(String xuehao) {
        this.xuehao = xuehao;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

}
