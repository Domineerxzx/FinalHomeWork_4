package com.tts.finalhomework_4.adapters;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by 37444 on 2017/12/22.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    private Context context;
    private List<T> list;

    public MyBaseAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
    }

    public Context getContext() {
        return context;
    }

    public List<T> getList() {
        return list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
