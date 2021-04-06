package com.example.sqlcourse_design;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String sql = "CREATE TABLE user(username varchar(20), password varchar(20))";
            db.execSQL(sql);
            sql = "CREATE TABLE IF NOT EXISTS classes(" +
                    "ID INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "credit INTEGER NOT NULL," +
                    "teacher TEXT NOT NULL)";
            db.execSQL(sql);
            sql = "CREATE TABLE IF NOT EXISTS student(" +
                    "ID INTEGER UNIQUE PRIMARY KEY," +
                    "name TEXT NOT NULL," +
                    "sex TEXT NOT NULL," +
                    "phoneNumber TEXT NOT NULL," +
                    "totalCredit INTEGER)";
            db.execSQL(sql);
            sql = "CREATE TABLE student_choose_class (" +
                    "student_ID INTEGER," +
                    "class_ID INTEGER," +
                    "score INTEGER DEFAULT \"\"," +
                    "FOREIGN KEY(student_ID) REFERENCES student(ID) ON UPDATE CASCADE," +
                    "FOREIGN KEY(class_ID) REFERENCES classes(ID) ON DELETE CASCADE" +
                    ")";
            db.execSQL(sql);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

