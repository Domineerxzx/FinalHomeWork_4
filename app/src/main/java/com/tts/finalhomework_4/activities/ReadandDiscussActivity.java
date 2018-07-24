package com.tts.finalhomework_4.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tts.finalhomework_4.R;

public class ReadandDiscussActivity extends Activity{

    private TextView tv_news_title;
    private TextView tv_content;
    private ImageButton ibt_history;
    private static boolean is_history = false;
    private LinearLayout ll_discuss;
    private String id;
    private LinearLayout discuss_item;
    private TextView tv_nickname;
    private TextView tv_discuss_time;
    private TextView tv_discuss_content;
    private View inflate;
    private TextView textView;
    private TextView tv_news_date;
    private ImageView imv_read;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_and_discuss);

        InitView();
        showNews();
        if(MainActivity.username!=null){
            showallDiscuss();
        }else{
            textView = new TextView(this);
            textView.setText(R.string.Unlogin);
            textView.setTextSize(20);
            ll_discuss.addView(textView);
        }
    }

    private void showallDiscuss() {
        SQLiteDatabase db = MainActivity.db.getReadableDatabase();
        Cursor discussInfo = db.query("discussInfo", new String[]{"nickname","discuss_time","discuss_content"}, "newsid=?", new
                String[]{id}, null, null, null);
        if (discussInfo != null || discussInfo.getCount() > 0) {
            while (discussInfo.moveToNext()) {
                setDiscuss(discussInfo);
                inflate=null;
                discuss_item=null;
            }
        }
    }

    private void setDiscuss(Cursor discussInfo) {
        inflate = LinearLayout.inflate(this, R.layout.discuss_item, null);
        discuss_item = (LinearLayout) inflate.findViewById(R.id.discuss_item);
        tv_nickname = (TextView) discuss_item.findViewById(R.id.tv_nickname);
        tv_discuss_time = (TextView) discuss_item.findViewById(R.id
                .tv_discuss_time);
        tv_discuss_content = (TextView) discuss_item.findViewById(R.id
                .tv_discuss_content);
        String nickname = discussInfo.getString(0);
        String discuss_time = discussInfo.getString(1);
        String discuss_content = discussInfo.getString(2);
        tv_nickname.setText(nickname);
        tv_discuss_time.setText(discuss_time);
        tv_discuss_content.setText(discuss_content);
        showDiscuss();
    }

    private void showDiscuss() {
        ll_discuss.addView(discuss_item);
    }

    private void InitView() {
        tv_news_title = (TextView) findViewById(R.id.tv_news_title);
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_news_date = (TextView) findViewById(R.id.tv_news_date);
        imv_read = (ImageView) findViewById(R.id.imv_read);
        ibt_history = (ImageButton) findViewById(R.id.ibt_history);
        ll_discuss = (LinearLayout) findViewById(R.id.ll_discuss);
    }

    private void showNews() {
        Intent intent = getIntent();
        id = intent.getStringExtra("newsid");
        SQLiteDatabase db = MainActivity.db.getWritableDatabase();
        Cursor newsInfo = db.query("newsInfo", new String[]{"newstitle",
                        "newscontent","newsdate","newsimage"}, "newsid=?", new String[]{id},
                null, null, null);
        newsInfo.moveToNext();
        String newstitle = newsInfo.getString(0);
        String newscontent = newsInfo.getString(1);
        String newsdate = newsInfo.getString(2);
        String newsimage = newsInfo.getString(3);
        int id = getResources().getIdentifier(newsimage, "mipmap", getPackageName());
        tv_news_title.setText(newstitle);
        tv_content.setText(newscontent);
        tv_news_date.setText(newsdate);
        imv_read.setBackgroundResource(id);
        newsInfo.close();
        db.close();
    }

    public void back_to_main(View v) {
        finish();
    }

    public void history(View v) {

        if (is_history == false) {
            ibt_history.setBackgroundResource(R.mipmap.historyed);
            is_history = true;
        } else {
            ibt_history.setBackgroundResource(R.mipmap.history);
            is_history = false;
        }
    }

    public void discuss(View v) {
        Intent intent = new Intent(this, DiscussNewsActivity.class);
        intent.putExtra("newsid", id);
        startActivity(intent);
        finish();
    }
}
