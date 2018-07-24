package com.tts.finalhomework_4.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tts.finalhomework_4.R;
import com.tts.finalhomework_4.beans.News;

import java.util.List;

/**
 * Created by 37444 on 2017/12/22.
 */

public class NewsAdapter extends MyBaseAdapter<News> {
    public NewsAdapter(Context context, List<News> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView = View.inflate(getContext(), R.layout
                    .activity_news_news, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_newstitle = convertView.findViewById(R.id.tv_newstitle);
            viewHolder.tv_assess = convertView.findViewById(R.id.tv_assess);
            viewHolder.imv_news = convertView.findViewById(R.id.imv_news);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_newstitle.setText(getList().get(position).getNewstitle());
        viewHolder.tv_assess.setText(getList().get(position).getAssess());
        String imagename = getList().get(position).getNewsimage();
        int id = getContext().getResources().getIdentifier(imagename, "mipmap", getContext().getPackageName());
        viewHolder.imv_news.setBackgroundResource(id);
        return convertView;
    }
    private static class ViewHolder{
        TextView tv_newstitle;
        TextView tv_assess;
        ImageView imv_news;
    }
}
