package com.tts.finalhomework_4.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tts.finalhomework_4.R;

public class SearchActivity extends Activity {

    private EditText et_search;
    private long insert;
    private int update;
    private TextView tv_hotnews1;
    private TextView tv_hotnews2;
    private TextView tv_hotnews3;
    private TextView tv_hotnews4;
    private TextView tv_hotnews5;
    private TextView tv_hotnews6;
    private TextView tv_hotnews7;
    private TextView tv_hotnews8;
    private String[] hotnews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        et_search = (EditText) findViewById(R.id.et_search);
        tv_hotnews1 = (TextView) findViewById(R.id.tv_hotnews1);
        tv_hotnews2 = (TextView) findViewById(R.id.tv_hotnews2);
        tv_hotnews3 = (TextView) findViewById(R.id.tv_hotnews3);
        tv_hotnews4 = (TextView) findViewById(R.id.tv_hotnews4);
        tv_hotnews5 = (TextView) findViewById(R.id.tv_hotnews5);
        tv_hotnews6 = (TextView) findViewById(R.id.tv_hotnews6);
        tv_hotnews7 = (TextView) findViewById(R.id.tv_hotnews7);
        tv_hotnews8 = (TextView) findViewById(R.id.tv_hotnews8);
        showHotNews();
    }

    public void search(View v) {
        boolean finded = false;
        int oldersearchtimes = 0;
        SQLiteDatabase db = MainActivity.db.getWritableDatabase();
        String searchContent = et_search.getText().toString().trim();
        Cursor cursor = db.query("search", new String[]{"olderSearch",
                        "searchtimes"},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            String olderSearch = cursor.getString(0);
            if (!searchContent.equals(olderSearch)) {
                finded = false;
            } else {
                finded = true;
                oldersearchtimes = cursor.getInt(1);
                break;
            }
        }
        if (finded) {
            ContentValues cv = new ContentValues();
            cv.put("searchtimes", oldersearchtimes + 1);
            update = db.update("search", cv, "oldersearch=?", new
                    String[]{searchContent});
        } else {
            int times = 1;
            ContentValues cv = new ContentValues();
            cv.put("olderSearch", searchContent);
            cv.put("searchtimes", times);
            insert = db.insert("search", null, cv);
        }
        if (update > 0) {
            Toast.makeText(this, "SUCCESS UPDATE", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "FAILED UPDATE", Toast.LENGTH_SHORT).show();
        }
        if (insert > 0) {
            Toast.makeText(this, "SUCCESS INSERT", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "FAILED INSERT", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        db.close();
    }

    public void showHotNews() {
        int i=0;
        hotnews = new String[8];
        SQLiteDatabase db = MainActivity.db.getReadableDatabase();
        Cursor cursor = db.query("search", new String[]{"olderSearch"},
                null, null, null, null, "searchtimes desc");

        while(cursor.moveToNext()){
            String hotnew = cursor.getString(0);
            hotnews[i]=hotnew;
            if(i==7){
                break;
            }
            i++;
        }
        cursor.close();
        db.close();
        tv_hotnews1.setText(hotnews[0]);
        tv_hotnews2.setText(hotnews[1]);
        tv_hotnews3.setText(hotnews[2]);
        tv_hotnews4.setText(hotnews[3]);
        tv_hotnews5.setText(hotnews[4]);
        tv_hotnews6.setText(hotnews[5]);
        tv_hotnews7.setText(hotnews[6]);
        tv_hotnews8.setText(hotnews[7]);

    }
    public void back(View v){
        finish();
    }

}
