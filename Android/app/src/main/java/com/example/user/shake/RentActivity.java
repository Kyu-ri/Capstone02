package com.example.user.shake;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class RentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);

        String[] info = new String[2];
        info=((Main2Activity)Main2Activity.mContext).getInfo();

        final Intent intent = getIntent();

        final Button rentbtn = (Button) findViewById(R.id.rent_button);
        final Button return_btn = (Button) findViewById(R.id.return_button);
        final TextView explain = (TextView) findViewById(R.id.explain);
        final String borrower = info[0];
        final String rentnumber_send;

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        explain.setText("대여 가능");
                    }
                    else{
                        explain.setText("대여 중인 \n자전거가 있습니다");
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        CheckRequest checkRequest = new CheckRequest(borrower, responseListener);
        RequestQueue queue = Volley.newRequestQueue(RentActivity.this);
        queue.add(checkRequest);


        rentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                explain.setText("대여 중인 \n자전거가 있습니다");
                                Intent intent = new Intent(RentActivity.this, InfoActivity.class);
                                String rentnumber = jsonResponse.getString("rentnumber");
                                intent.putExtra("rentnumber",rentnumber);
                                finish();

                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RentActivity.this);
                                builder.setMessage("대여에 실패하였습니다")
                                        .setNegativeButton("다시 시도",null)
                                        .create()
                                        .show();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                if(!explain.getText().equals("대여 중인 \n자전거가 있습니다")) {
                    long time = System.currentTimeMillis();
                    SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String day = dayTime.format(new Date(time));
                    Date date = new Date();
                    Calendar cal = Calendar.getInstance(); cal.setTime(date); // 10분 더하기
                    cal.add(Calendar.HOUR, 1);//return 할때 몇시간 빌리는지 넣어주면 됨
                    String return_time = dayTime.format(cal.getTime()); System.out.println(day+"      "+ return_time);
                    RentRequest rentRequest = new RentRequest(intent.getStringExtra("bikecode"), intent.getStringExtra("borrower"), day, return_time, 0, 0, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RentActivity.this);
                    queue.add(rentRequest);
                }
                else{
                    Toast.makeText(getApplicationContext(),"대여 중인 자전거가 있습니다",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
