package com.example.user.shake;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText idText = (EditText) findViewById(R.id.id_edit);
        final EditText passwordText = (EditText) findViewById(R.id.password_edit);
        final EditText repasswordText = (EditText) findViewById(R.id.re_password_edit);
        final EditText emailText = (EditText) findViewById(R.id.email_edit);
        final EditText nameText = (EditText) findViewById(R.id.name_edit);
        final EditText phonenuberText1 = (EditText)findViewById(R.id.phone_edit1);
        final EditText phonenuberText2 = (EditText)findViewById(R.id.phone_edit2);
        final EditText phonenuberText3 = (EditText)findViewById(R.id.phone_edit3);

        Button registerButton = (Button) findViewById(R.id.register_button);

        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                String userName = nameText.getText().toString();
                String userEmail = emailText.getText().toString();
                String phonenumber = phonenuberText1.getText().toString()+"-"+phonenuberText2.getText().toString()+"-"+phonenuberText3.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("회원 등록에 성공했습니다")
                                        .setPositiveButton("확인",null)
                                        .create()
                                        .show();
                                //System.out.println(jsonResponse);
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("회원 등록에 실패했습니다")
                                        .setNegativeButton("다시 시도",null)
                                        .create()
                                        .show();
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userName,userEmail,phonenumber,responseListener);//, phonenumber, userEmail,0,0,0,0,0
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
