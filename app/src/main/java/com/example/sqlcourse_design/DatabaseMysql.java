package com.example.sqlcourse_design;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseMysql {
    private String url = "jdbc:mysql://82.156.24.13:3306/test_android";
    private String user = "Lilc";
    private String password = "lilc123456";

    public DatabaseMysql() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            Log.e("Mysql", "数据库连接失败");
        }
    }


}
