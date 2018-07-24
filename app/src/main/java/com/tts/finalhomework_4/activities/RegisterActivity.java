package com.tts.finalhomework_4.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.tts.finalhomework_4.R;
import com.tts.finalhomework_4.utils.MyToast;

public class RegisterActivity extends Activity {

    private EditText et_r_username;
    private EditText et_r_password;
    private EditText et_r_nickname;
    private CheckBox cb_boy;
    private CheckBox cb_girl;
    private EditText et_r_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_r_username = (EditText) findViewById(R.id.et_R_username);
        et_r_password = (EditText) findViewById(R.id.et_R_password);
        et_r_nickname = (EditText) findViewById(R.id.et_R_nickname);
        et_r_confirm = (EditText) findViewById(R.id.et_R_confirm);
        cb_boy = (CheckBox) findViewById(R.id.cb_boy);
        cb_girl = (CheckBox) findViewById(R.id.cb_girl);
    }

    public void back_to_login(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean checkMessage() {
        SQLiteDatabase db = MainActivity.db.getWritableDatabase();
        Cursor query = db.query("userInfo", null, "username=?", new
                String[]{et_r_username.getText().toString()}, null, null, null);
        if (TextUtils.isEmpty(et_r_username.getText())) {
            MyToast.show(this, "账号不能为空！！！");
            return false;
        }
        if (et_r_username.getText().toString().length() > 20) {
            MyToast.show(this, "账号大于20个字符！！！");
            return false;
        }
        if(query.getCount()==1){
            MyToast.show(this, "该账号已经被使用！！！");
            return false;
        }
        if (TextUtils.isEmpty(et_r_password.getText())) {
            MyToast.show(this, "密码不能为空！！！");
            return false;
        }
        if (et_r_password.getText().toString().length() > 20) {
            MyToast.show(this, "密码不能为空！！！");
            return false;
        }
        if (et_r_password.getText().toString().equals(et_r_confirm.getText()
                .toString()) == false) {
            MyToast.show(this, "确认密码与密码不相同，请确认！！！");
            return false;
        }
        if (TextUtils.isEmpty(et_r_nickname.getText())) {
            MyToast.show(this, "昵称不能为空！！！");
            return false;
        }
        if (et_r_nickname.getText().toString().length() > 20) {
            MyToast.show(this, "昵称不能为空！！！");
            return false;
        }
        if (cb_boy.isChecked() == true && cb_girl.isChecked() == true) {
            MyToast.show(this, "只能选择一种性别！！！");
            return false;
        }
        if (cb_boy.isChecked() == false && cb_girl.isChecked() == false) {
            MyToast.show(this, "必须选择一种性别！！！");
            return false;
        }
        MyToast.show(this, "信息准确，可以注册！");
        query.close();
        db.close();
        return true;
    }

    public void register(View v) {
        if (checkMessage() == true) {
            String username = et_r_username.getText().toString();
            String password = et_r_password.getText().toString();
            String nickname = et_r_nickname.getText().toString();
            String sex = null;
            SQLiteDatabase db = MainActivity.db.getWritableDatabase();
            ContentValues cv = new ContentValues();
            if (cb_boy.isChecked() == true) {
                sex = cb_boy.getText().toString();
            }
            if (cb_girl.isChecked() == true) {
                sex = cb_girl.getText().toString();
            }
            cv.put("username", username);
            cv.put("password", password);
            cv.put("nickname", nickname);
            cv.put("sex", sex);
            long insert = db.insert("userInfo", null, cv);
            db.close();
            if (insert > 0) {
                MyToast.show(this, "成功注册,已为您自动登录");
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            } else {
                MyToast.show(this, "注册失败，请重试！！！");
            }
        }
    }
}
