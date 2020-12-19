package com.example.whatfoo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class JoinActivity  extends AppCompatActivity {

    private EditText txtId, txtPw, txtPwchk, txtName;
    private RadioButton rdoMale, rdoFemale;
    private Button joinButton, cancelButton;
    private RadioGroup rdgGender;
    String userGender;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        joinButton = (Button) findViewById(R.id.btnJoin);
        cancelButton = (Button) findViewById(R.id.btnCancel);

        txtId = (EditText)findViewById(R.id.txtId);
        txtPw = (EditText)findViewById(R.id.txtPw);
        txtPwchk = (EditText)findViewById(R.id.txtPwchk);
        txtName = (EditText)findViewById(R.id.txtName);

        rdgGender = (RadioGroup) findViewById(R.id.rdgGender);
        rdgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.rdoMale) {
                    userGender = "Male";
                } else if(i == R.id.rdoFemale) {
                    userGender = "Female";
                }
            }
        });


        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        joinButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String userId = txtId.getText().toString();
                String userPw = txtPw.getText().toString();
                String userPwchk = txtPwchk.getText().toString();
                String userName = txtName.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success) {   //회원가입 성공
                                Toast.makeText(getApplicationContext(), "회원 가입을 축하드립니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {    //회원가입 실패
                                Toast.makeText(getApplicationContext(), "회원 가입을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                //Volley를 이용해서 요청
                RegisterRequest registerRequest = new RegisterRequest(userId, userPw, userName, userGender, responseListener);
                RequestQueue queue = Volley.newRequestQueue(JoinActivity.this);
                queue.add(registerRequest);
            }

        });
    }
}
