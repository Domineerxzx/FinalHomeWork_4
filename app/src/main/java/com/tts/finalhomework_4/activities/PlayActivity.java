package com.tts.finalhomework_4.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.tts.finalhomework_4.R;

public class PlayActivity extends Activity {

    private TextView tv_play_name;
    private View inflate;
    private LinearLayout discuss_item;
    private TextView tv_nickname;
    private TextView tv_discuss_time;
    private TextView tv_discuss_content;
    private LinearLayout ll_live_discuss;
    private String videosid;
    private TextView textView;
    private VideoView vv_live;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        tv_play_name = (TextView) findViewById(R.id.tv_play_name);
        ll_live_discuss = (LinearLayout) findViewById(R.id.ll_live_discuss);
        vv_live = (VideoView) findViewById(R.id.vv_live);
        showVideoInfo();
        if(MainActivity.username!=null){
            showallDiscuss();
        }else{
            textView = new TextView(this);
            textView.setText(R.string.Unlogin);
            textView.setTextSize(20);
            ll_live_discuss.addView(textView);
        }
    }

    private void showVideoInfo() {
        Intent intent = getIntent();
        videosid = intent.getStringExtra("videosid");
        SQLiteDatabase db = MainActivity.db
                .getWritableDatabase();
        Cursor videoinfo = db.query("videoInfo", new String[]{"videoname",
                "videoaddress"}, "videosid=?", new String[]{videosid}, null,
                null, null);
        videoinfo.moveToNext();
        String videoname = videoinfo.getString(0);
        String videoaddress = videoinfo.getString(1);
        tv_play_name.setText(videoname);
        vv_live.setVideoPath(Environment.getExternalStorageDirectory().getAbsolutePath()+videoaddress);
    }

    private void showallDiscuss() {
        SQLiteDatabase db = MainActivity.db.getReadableDatabase();
        Cursor discussInfo = db.query("discussInfo", new String[]{"nickname","discuss_time","discuss_content"}, "videosid=?", new
                String[]{videosid}, null, null, null);
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
        ll_live_discuss.addView(discuss_item);
    }
    public void back_to_live(View v) {
        finish();
    }
    public void live_discuss(View v) {
        Intent intent = new Intent(this, DiscussLiveActivity.class);
        intent.putExtra("videosid", videosid);
        startActivity(intent);
        finish();
    }
    public void play(View v){
        if (!vv_live.isPlaying ()) {
            vv_live.start ();
        }
    }
    public void pause(View v){
        if(vv_live.isPlaying ()){
            vv_live.pause ();
        }
    }
    public void stop(View v){
        if(vv_live!=null && vv_live.isPlaying ()){
            vv_live.stopPlayback ();
        }
    }
    public void restart(View v){
        if (vv_live != null) {
            vv_live.resume ();
        }
    }
}
