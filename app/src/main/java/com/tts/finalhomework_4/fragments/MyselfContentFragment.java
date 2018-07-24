package com.tts.finalhomework_4.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tts.finalhomework_4.R;

/**
 * Created by 37444 on 2017/12/14.
 */

public class MyselfContentFragment extends Fragment {

    private View view;
    public Button bt_userNickname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_myself_content, null);
        bt_userNickname = (Button) view.findViewById(R.id.bt_usernickname);
        if(getArguments()!=null){
            changeNickname();
        }
        return view;
    }
    public void changeNickname(){
        Bundle arguments = getArguments();
        String nickname = arguments.getString("nickname");
        setText(nickname);
    }

    public String getText() {
        if (bt_userNickname != null) {
            String s = bt_userNickname.getText().toString();
            return s;
        } else {
            return "";
        }
    }

    public void setText(String s) {
        if (bt_userNickname != null) {
            bt_userNickname.setText(s);
        } else {
            return;
        }
    }
}
