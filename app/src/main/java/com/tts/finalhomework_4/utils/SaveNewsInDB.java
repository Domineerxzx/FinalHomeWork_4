package com.tts.finalhomework_4.utils;

import android.content.ContentValues;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.tts.finalhomework_4.activities.MainActivity;
import com.tts.finalhomework_4.beans.News;

import java.io.InputStream;
import java.util.List;

/**
 * Created by 37444 on 2017/12/14.
 */

public class SaveNewsInDB {
    public static void savenewsindb(AssetManager assetManager){
        AssetManager assets = assetManager;
        SQLiteDatabase db = MainActivity.db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        try {
            NewsPaser newsParser = new NewsPaser();
            InputStream inputStream = assets.open("news.xml");
            List<News> newsList = newsParser.parseXML(inputStream);
            for (News news: newsList) {
                String newsid = news.getId();
                String newstitle = news.getNewstitle();
                String assess = news.getAssess();
                String newsdate = news.getNewsdate();
                String newscontent = news.getNewscontent();
                String newsimage = news.getNewsimage();
                cv.put("newsid",newsid);
                cv.put("newstitle",newstitle);
                cv.put("assess",assess);
                cv.put("newsdate",newsdate);
                cv.put("newscontent",newscontent);
                cv.put("newsimage",newsimage);
                Cursor newsInfo = db.query("newsInfo", null, "newsid=?",
                        new String[]{newsid}, null, null, null);
                if(newsInfo.getCount()==0){
                    db.insert("newsInfo", null, cv);
                }
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }
}
