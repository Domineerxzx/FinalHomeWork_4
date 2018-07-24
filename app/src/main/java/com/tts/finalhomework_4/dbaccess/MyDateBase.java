package com.tts.finalhomework_4.dbaccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 37444 on 2017/12/1.
 */

public class MyDateBase extends SQLiteOpenHelper {
    public MyDateBase(Context context) {
        super(context, "MOJINews.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table search(_id Integer primary key " +
                "autoincrement,olderSearch varchar(50),searchtimes Integer)");
        db.execSQL("create table userInfo(_id Integer primary key " +
                "autoincrement,username char(20),password char(20),nickname " +
                "char(20),sex char(2))");
        db.execSQL("create table newsInfo(_id Integer primary key " +
                "autoincrement,newsid char(2),newstitle char(30),assess char" +
                "(20),newsdate char(20),newscontent char(5000),newsimage char(10))");
        db.execSQL("create table discussInfo(_id Integer primary key " +
                "autoincrement,nickname char(20),discuss_time char(50)," +
                "discuss_content char(500),newsid char(2))");
        db.execSQL("create table videoInfo(_id Integer primary key " +
                "autoincrement,videosid char(2),videosdate char(20)," +
                "videoname char(30),videoaddress char(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
