package com.tts.finalhomework_4.utils;

import android.content.ContentValues;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

import com.tts.finalhomework_4.activities.MainActivity;
import com.tts.finalhomework_4.beans.Videos;

import java.io.InputStream;
import java.util.List;

/**
 * Created by 37444 on 2017/12/14.
 */

public class SaveVideosInDB {
    public static void savevideosindb(AssetManager assetManager){
        AssetManager assets = assetManager;
        SQLiteDatabase db = MainActivity.db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        try {
            VideoPaser videoParser = new VideoPaser();
            InputStream inputStream = assets.open("video.xml");
            List<Videos> videosList = videoParser.parseXML(inputStream);
            for (Videos videos: videosList) {
                String videosid = videos.getId();
                String videoname = videos.getVideoname();
                String videosdate = videos.getVideosdate();
                String videoaddress = videos.getVideoaddress();
                cv.put("videosid",videosid);
                cv.put("videosdate",videosdate);
                cv.put("videoname",videoname);
                cv.put("videoaddress",videoaddress);
                long insert = db.insert("videoInfo", null, cv);
                if (insert>0){
                    System.out.println("----------------");
                }
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }
}
