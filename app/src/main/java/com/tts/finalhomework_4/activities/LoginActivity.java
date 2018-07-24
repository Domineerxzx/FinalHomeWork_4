package com.tts.finalhomework_4.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.tts.finalhomework_4.R;
import com.tts.finalhomework_4.utils.MyToast;

public class LoginActivity extends Activity {

    private EditText et_username;
    private EditText et_password;
    private SharedPreferences config;
    private CheckBox cb_remember;
    public static String username;
    public String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(getIntent().getStringExtra("username")!=null){
            MyToast.show(this,"已经登录了，不要再登录了");
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("username", MainActivity.username);
            startActivity(intent);
            finish();
        }
        et_username = (EditText) findViewById(R.id.et_L_username);
        et_password = (EditText) findViewById(R.id.et_L_password);
        cb_remember = (CheckBox) findViewById(R.id.cb_remember);
        config = getSharedPreferences("config", MODE_PRIVATE);
        String username = config.getString("username", "");
        String password = config.getString("password", "");
        showUP(username, password);
    }

    public void back_to_myself(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("back","notLogin");
        startActivity(intent);
        finish();
    }

    private void showUP(String username, String password) {
        et_username.setText(username);
        et_password.setText(password);
    }

    private boolean saveWithSP(String username, String password) {
        SharedPreferences.Editor editor = config.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        return editor.commit();
    }

    public void login(View v) {
        username = et_username.getText().toString();
        password = et_password.getText().toString();
        SQLiteDatabase db = MainActivity.db.getWritableDatabase();
        Cursor userInfo = db.query("userInfo", new String[]{"username",
                        "password"},"username=?" , new String[]{username},
                null,null, null);
        if(userInfo.getCount()==0){
            MyToast.show(this, "账号不存在，请注册！！！");
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            finish();
            userInfo.close();
            db.close();
            return;
        }else {
            while (userInfo.moveToNext()) {
                String pw = userInfo.getString(1);
                if (!password.equals(pw)) {
                    MyToast.show(this, "密码不正确！！！");
                    userInfo.close();
                    db.close();
                    return;
                }
            }
        }
        userInfo.close();
        db.close();
        if (cb_remember.isChecked() == true) {
            boolean saveWithSP = saveWithSP(username, password);
            if (saveWithSP == true) {
                MyToast.show(this, "SUCCESS");
            } else {
                MyToast.show(this, "FAILED");
            }
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }

    public void next_to_register(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
