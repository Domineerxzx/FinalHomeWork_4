package com.tts.finalhomework_4.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.tts.finalhomework_4.R;
import com.tts.finalhomework_4.utils.MyToast;

public class UserInfoActivity extends Activity {

    private TextView tv_id;
    private TextView tv_nickname;
    private TextView tv_sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        tv_id = (TextView) findViewById(R.id.tv_id);
        tv_nickname = (TextView) findViewById(R.id.tv_nickname);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        if (MainActivity.username != null) {
            String username = MainActivity.username;
            SQLiteDatabase db = MainActivity.db.getWritableDatabase();
            Cursor userInfo = db.query("userInfo", new String[]{"_id",
                            "nickname", "sex"}, "username=?", new
                            String[]{username}, null, null, null);
            if (userInfo != null || userInfo.getCount() > 0) {
                while (userInfo.moveToNext()) {
                    String id = userInfo.getString(0);
                    String nickname = userInfo.getString(1);
                    String sex = userInfo.getString(2);
                    tv_id.setText(id);
                    tv_nickname.setText(nickname);
                    tv_sex.setText(sex);
                }
            }
            userInfo.close();
            db.close();
        } else {
            MyToast.show(this, "未登录");
        }
    }

    public void back_to_myself(View v) {
        Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
        intent.putExtra("username", MainActivity.username);
        startActivity(intent);
        finish();
    }

    public void cancellation(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("注销");
        builder.setMessage("确定注销该账户吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.username=null;
                Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
                intent.putExtra("cancellation", 1);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        builder.show();

    }
}
