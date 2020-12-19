package com.example.whatfoo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText txtId, txtPw;
    private Button joinButton, loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtId = (EditText) findViewById(R.id.txtId);
        txtPw = (EditText) findViewById(R.id.txtPw);
        joinButton = (Button) findViewById(R.id.btnJoin);
        loginButton = (Button) findViewById(R.id.btnLogin);

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = txtId.getText().toString();
                String userPw = txtPw.getText().toString();

                Intent intent = new Intent(LoginActivity.this, ChoiceActivity.class);
                startActivity(intent);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //인코딩 문제로 한글 DB 로그인 불가

                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if(success) {   //로그인 성공
                                String userId = jsonObject.getString("userId");
                                String userPw = jsonObject.getString("userPw");

                                Toast.makeText(getApplicationContext(), "오늘은 무엇을 드실래요?", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(LoginActivity.this, ChoiceActivity.class);
                                intent.putExtra("userId", userId);
                                intent.putExtra("userPw", userPw);

                                startActivity(intent);

                            } else {    //로그인 실패
                                Toast.makeText(getApplicationContext(), "아이디나 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userId, userPw, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}
