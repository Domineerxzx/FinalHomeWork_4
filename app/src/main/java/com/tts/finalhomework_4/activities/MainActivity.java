package com.tts.finalhomework_4.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.tts.finalhomework_4.dbaccess.MyDateBase;
import com.tts.finalhomework_4.fragments.LiveBottomFragment;
import com.tts.finalhomework_4.fragments.LiveContentFragment;
import com.tts.finalhomework_4.fragments.MyselfBottomFragment;
import com.tts.finalhomework_4.fragments.MyselfContentFragment;
import com.tts.finalhomework_4.fragments.NewsBottomFragment;
import com.tts.finalhomework_4.fragments.NewsContentFragment;
import com.tts.finalhomework_4.R;
import com.tts.finalhomework_4.utils.SaveNewsInDB;
import com.tts.finalhomework_4.utils.SaveVideosInDB;

public class MainActivity extends Activity {


    public static MyDateBase db = null;
    public static String username;
    private MyselfContentFragment myselfContentFragment;
    private LiveContentFragment liveContentFragment;
    private NewsContentFragment newsContentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (db == null) {
            db = new MyDateBase(this);
            AssetManager assets = getAssets();
            SaveNewsInDB.savenewsindb(assets);
            SaveVideosInDB.savevideosindb(assets);
        }
        myselfContentFragment = new MyselfContentFragment();
        liveContentFragment = new LiveContentFragment();
        newsContentFragment = new NewsContentFragment();
        replaceContentActivity(newsContentFragment);
        replaceBottomActivity(new NewsBottomFragment());
        if (MainActivity.username != null) {
            SQLiteDatabase db = MainActivity.db
                    .getWritableDatabase();
            Cursor userInfo = db.query("userInfo", new String[]{"nickname"},
                    "username=?", new String[]{MainActivity.username}, null,
                    null, null);
            userInfo.moveToNext();
            String nickname = userInfo.getString(0);
            Bundle bundle = new Bundle();
            bundle.putString("nickname", nickname);
            myselfContentFragment.setArguments(bundle);
        }
        if (getIntent().getStringExtra("username") != null) {
            showUserInfo();
        }
        if (getIntent().getStringExtra("back") != null && getIntent()
                .getStringExtra("back").equals("notLogin")) {
            replaceContentActivity(myselfContentFragment);
            replaceBottomActivity(new MyselfBottomFragment());
        }
        if (getIntent().getIntExtra("cancellation", 0) == 1) {
            replaceContentActivity(myselfContentFragment);
            replaceBottomActivity(new MyselfBottomFragment());
            username = null;
            String s = new String("登录/注册");
            Bundle bundle = new Bundle();
            bundle.putString("nickname", s);
            myselfContentFragment.setArguments(bundle);
        }
    }

    public void showUserInfo() {
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        SQLiteDatabase db = this.db.getReadableDatabase();
        Cursor userInfo = db.query("userInfo", new String[]{"nickname"},
                "username=?",
                new String[]{username}, null, null, null);
        if (userInfo != null || userInfo.getCount() > 0) {
            while (userInfo.moveToNext()) {
                String nickname = userInfo.getString(0);
                Bundle bundle = new Bundle();
                bundle.putString("nickname", nickname);
                myselfContentFragment.setArguments(bundle);
                replaceContentActivity(myselfContentFragment);
                replaceBottomActivity(new MyselfBottomFragment());
            }
        }
        userInfo.close();
        db.close();
    }

    private void replaceBottomActivity(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frag_bottom, fragment);
        transaction.commit();
    }

    private void replaceContentActivity(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frag_content, fragment);
        transaction.commit();
    }

    public void page_news(View v) {
        replaceContentActivity(newsContentFragment);
        replaceBottomActivity(new NewsBottomFragment());
    }

    public void page_live(View v) {
        replaceContentActivity(liveContentFragment);
        replaceBottomActivity(new LiveBottomFragment());
    }

    public void page_myself(View v) {
        replaceContentActivity(myselfContentFragment);
        replaceBottomActivity(new MyselfBottomFragment());
    }

    public void login_or_register(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }

    public void show_userInfo(View v) {
        Intent intent = new Intent(this, UserInfoActivity.class);
        startActivity(intent);
        finish();
    }

    public void input(View v) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void onclick_live(View v) {
        Intent intent = new Intent(this, PlayActivity.class);
        switch (v.getId()) {
            case R.id.video1:
                intent.putExtra("videosid", "1");
                startActivity(intent);
                break;
            case R.id.video2:
                intent.putExtra("videosid", "2");
                startActivity(intent);
                break;
            case R.id.video3:
                intent.putExtra("videosid", "3");
                startActivity(intent);
                break;
            case R.id.video4:
                intent.putExtra("videosid", "4");
                startActivity(intent);
                break;
            case R.id.video5:
                intent.putExtra("videosid", "5");
                startActivity(intent);
                break;
        }
    }
}
