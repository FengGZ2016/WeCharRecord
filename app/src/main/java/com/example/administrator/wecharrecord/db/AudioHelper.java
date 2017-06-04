package com.example.administrator.wecharrecord.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/6/4.
 */

public class AudioHelper extends SQLiteOpenHelper{
    //数据库名字
    private static final String DATABASE_NAME = "test.db";
    //数据库版本
    private static final int DATABASE_VERSION = 1;


    public AudioHelper(Context context) {
        //CursorFactory设置为null,使用默认值
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("create a database");
        db.execSQL("create table Record(id varchar(225), path varchar(225), second int, isPlayed int)");//0:true,1：false
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
