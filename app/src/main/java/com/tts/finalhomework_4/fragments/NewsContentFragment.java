package com.tts.finalhomework_4.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tts.finalhomework_4.R;
import com.tts.finalhomework_4.activities.MainActivity;
import com.tts.finalhomework_4.activities.ReadandDiscussActivity;
import com.tts.finalhomework_4.adapters.NewsAdapter;
import com.tts.finalhomework_4.beans.News;

import java.util.ArrayList;

/**
 * Created by 37444 on 2017/12/14.
 */

public class NewsContentFragment extends Fragment implements AdapterView.OnItemClickListener {

    private View view;
    private   ListView lv_news;
    private ArrayList<News> newsList;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news_content, null);
        newsList = new ArrayList<News>();
        setText();
        lv_news = view.findViewById(R.id.lv_news);
        lv_news.setAdapter(new NewsAdapter(getActivity(),newsList));
        lv_news.setOnItemClickListener(this);
        return view;
    }
    private void setText() {
        SQLiteDatabase db = MainActivity.db.getWritableDatabase();
        Cursor info = db.query("newsInfo", new String[]{"newstitle", "assess","newsimage"},
                null,null,null, null,
                null);
        if (info != null || info.getCount() > 0) {
            while (info.moveToNext()) {
                String newstitle = info.getString(0);
                String assess = info.getString(1);
                String newsimage = info.getString(2);
                News news = new News();
                news.setNewstitle(newstitle);
                news.setAssess(assess);
                news.setNewsimage(newsimage);
                newsList.add(news);
            }
        }
        info.close();
        db.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        Intent intent = new Intent(getActivity(),ReadandDiscussActivity.class);
        intent.putExtra("newsid",String.valueOf(position+1));
        getActivity().startActivity(intent);
    }

}
