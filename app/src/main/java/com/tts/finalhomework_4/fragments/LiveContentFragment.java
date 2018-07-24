package com.tts.finalhomework_4.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tts.finalhomework_4.R;
import com.tts.finalhomework_4.activities.MainActivity;

/**
 * Created by 37444 on 2017/12/14.
 */

public class LiveContentFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_live_content, container, false);
        findItems(view);
        return view;
    }
    private void findItems(View view) {
        LinearLayout video1 = (LinearLayout) view.findViewById(R.id.video1);
        setText(video1, "1");
        ImageView imv_video1 = (ImageView) video1.findViewById(R.id.imv_live);
        imv_video1.setBackgroundResource(R.mipmap.video1);

        LinearLayout video2 = (LinearLayout) view.findViewById(R.id.video2);
        setText(video2, "2");
        ImageView imv_video2 = (ImageView) video2.findViewById(R.id.imv_live);
        imv_video2.setBackgroundResource(R.mipmap.video2);

        LinearLayout video3 = (LinearLayout) view.findViewById(R.id.video3);
        setText(video3, "3");
        ImageView imv_video3 = (ImageView) video3.findViewById(R.id.imv_live);
        imv_video3.setBackgroundResource(R.mipmap.video3);

        LinearLayout video4 = (LinearLayout) view.findViewById(R.id.video4);
        setText(video4, "4");
        ImageView imv_video4 = (ImageView) video4.findViewById(R.id.imv_live);
        imv_video4.setBackgroundResource(R.mipmap.video4);

        LinearLayout video5 = (LinearLayout) view.findViewById(R.id.video5);
        setText(video5, "5");
        ImageView imv_video5 = (ImageView) video5.findViewById(R.id.imv_live);
        imv_video5.setBackgroundResource(R.mipmap.video5);
    }

    private void setText(LinearLayout layout, String order) {

        SQLiteDatabase db = MainActivity.db.getWritableDatabase();
        Cursor videoInfo = db.query("videoInfo", new String[]{"videoname"},
                "videosid=?", new String[]{order}, null, null,
                null);
        videoInfo.moveToNext();
        String videoname = videoInfo.getString(0);
        TextView tv_live_name = (TextView) layout.findViewById(R.id.tv_live_name);
        tv_live_name.setText(videoname);
        videoInfo.close();
        db.close();
    }
}
