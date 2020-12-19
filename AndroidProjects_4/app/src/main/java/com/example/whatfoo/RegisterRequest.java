package com.example.whatfoo;

import android.text.PrecomputedText;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    //서버 URL 설정 (php 파일 연동)
    final static private String URL = "http://localhost/Register.php";
    private Map<String, String> map;


    public RegisterRequest(String userId, String userPw, String userName, String userGender, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userId", userId);
        map.put("userPw", userPw);
        map.put("userName", userName);
        map.put("userGender", userGender);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
