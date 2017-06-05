package com.example.administrator.wecharrecord.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.administrator.wecharrecord.model.Record;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/6/4.
 * 数据库管理类
 */

public class DBDao {

    private AudioHelper mHelper;
    private SQLiteDatabase db;
    public DBDao(Context context){
        mHelper=new AudioHelper(context);
    }

    /**
     * 增加数据
     * */
    public void add(Record record){
        record.setId(UUID.randomUUID().toString());
        db = mHelper.getWritableDatabase();
        db.beginTransaction();  //开始事务
        try {
            db.execSQL("INSERT INTO Record VALUES(?, ?, ?, ?)", new Object[]{record.getId(), record.getPath(), record.getSecond(), record.isPlayed() ? 0 : 1});
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
            db.close();
        }
        Log.e("wgy", "添加数据库成功："+record.toString());
    }

    /**
     * 更新数据
     * */
    public void updateRecord(Record record) {
        db = mHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("isPlayed", record.isPlayed() ? 0 : 1);
        db.update("record", cv, "id = ?", new String[]{record.getId()});
        db.close();

    }


    /**
     * 删除数据
     * */
    public void deleteRecord(Record record) {
        db = mHelper.getWritableDatabase();
        db.delete("Record", "id = ?", new String[]{record.getId()});
        db.close();

    }

    /**
     * 查找所有数据
     * */
    public List<Record> queryAll(){
        db = mHelper.getWritableDatabase();
        List<Record> recordList=new ArrayList<>();
        Cursor cursor=db.rawQuery("SELECT * FROM record", null);
        while (cursor.moveToNext()) {
            Record record = new Record();
            record.setId(cursor.getString(cursor.getColumnIndex("id")));
            record.setPath(cursor.getString(cursor.getColumnIndex("path")));
            record.setSecond(cursor.getInt(cursor.getColumnIndex("second")));
            record.setPlayed(cursor.getInt(cursor.getColumnIndex("isPlayed"))==0?true:false);
            recordList.add(record);
        }
        cursor.close();
        return recordList;
    }


    /**
     * 关闭databa
     * */
    public void closeDB() {
        db.close();
    }
}
