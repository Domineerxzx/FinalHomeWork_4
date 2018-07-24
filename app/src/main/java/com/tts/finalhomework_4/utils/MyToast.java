package com.tts.finalhomework_4.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 37444 on 2017/12/9.
 */

public class MyToast {
    public static void show(Context context , String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }
}
