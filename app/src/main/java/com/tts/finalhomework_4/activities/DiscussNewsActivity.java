package com.tts.finalhomework_4.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import com.tts.finalhomework_4.R;
import com.tts.finalhomework_4.utils.MyToast;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DiscussNewsActivity extends Activity {

    private EditText et_discuss_content;
    private String newsid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discuss);
        et_discuss_content = (EditText) findViewById(R.id.et_discuss_content);
        Intent intent = getIntent();
        newsid = intent.getStringExtra("newsid");
    }

    public void back_to_read(View v) {
        Intent intent = new Intent(this, ReadandDiscussActivity.class);
        intent.putExtra("newsid",newsid);
        startActivity(intent);
        finish();
    }

    public void publish(View v) {
        ContentValues cv = new ContentValues();
        String discuss_content = et_discuss_content.getText().toString();
        if (discuss_content .length()==0) {
            MyToast.show(this, "未输入评论内容！！！");
            Intent intent = new Intent(this, DiscussNewsActivity.class);
            intent.putExtra("newsid",newsid);
            startActivity(intent);
            finish();
            return;
        }
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(date);
        String username = MainActivity.username;
        if (username == null) {
            MyToast.show(this, "未登录，不能评论！！！");
            Intent intent = new Intent(this, ReadandDiscussActivity.class);
            intent.putExtra("newsid",newsid);
            startActivity(intent);
            finish();
            return;
        }
        SQLiteDatabase db = MainActivity.db
                .getWritableDatabase();
        Cursor userInfo = db.query("userInfo", new String[]{"nickname"},
                "username=?", new String[]{username}, null,
                null, null);
        userInfo.moveToNext();
        String nickname = userInfo.getString(0);
        userInfo.close();
        cv.put("nickname", nickname);
        cv.put("discuss_time", time);
        cv.put("discuss_content", discuss_content);
        cv.put("newsid", newsid);
        long insert = db.insert("discussInfo", null, cv);
        if (insert > 0) {
            MyToast.show(this, "发表成功");
            Intent intent = new Intent(this, ReadandDiscussActivity.class);
            intent.putExtra("newsid",newsid);
            startActivity(intent);
            finish();
        } else {
            MyToast.show(this, "发表未成功");
            return;
        }
    }
}
