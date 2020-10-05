package com.example.user.shake;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class BikeRegisterActivity extends AppCompatActivity {

    EditText location, model, addInfo, cost, lockId;
    Button buttonRegisterMap, buttonUp, buttonDown, buttonRegister;
    Spinner type;

    double longitude, latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_register);

        buttonRegisterMap = findViewById(R.id.buttonRegisterMap);
        buttonUp = findViewById(R.id.buttonCostUp);
        buttonDown = findViewById(R.id.buttonCostDown);
        buttonRegister = findViewById(R.id.buttonRegisterBike);

        location = findViewById(R.id.editTextBikeLocation);
        model = findViewById(R.id.editTextBikeModel);
        addInfo = findViewById(R.id.editTextBikeInfo);
        cost = findViewById(R.id.editTextBikeCost);
        lockId = findViewById(R.id.editTextBikeLockId);

        type = findViewById(R.id.spinnerBikeType);

        buttonRegisterMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BikeRegisterActivity.this, RegisterBikeLocationActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cost.setText(Integer.toString(Integer.parseInt(cost.getText().toString()) + 500));
            }
        });

        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(cost.getText().toString()) >= 500)
                    cost.setText(Integer.toString(Integer.parseInt(cost.getText().toString()) - 500));
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String owner = intent.getStringExtra("userId");
                String sCost = cost.getText().toString();
                String imageurl = "not implemented yet";
                String sLockId = lockId.getText().toString();
                String sModel = model.getText().toString();
                int iType = type.getSelectedItemPosition();
                String sType = "";

                switch (iType){
                    case 0:
                        sType = "로드자전거";
                        break;
                    case 1:
                        sType = "전기자전거";
                        break;
                    case 2:
                        sType = "산악자전거";
                        break;
                    case 3:
                        sType = "하이브리드자전거";
                        break;
                    case 4:
                        sType = "미니벨로";
                        break;
                    case  5:
                        sType = "기타자전거";
                        break;
                        default:
                            sType = "no bike type error" + Integer.toString(iType);
                }
                String sAddInfo = addInfo.getText().toString();
                String bikecode = owner+Double.toString(latitude)+sType;

                ArrayList<String> queryResult;

                PhpConnect task = new PhpConnect();
                try {
                    queryResult = task.execute("http://13.125.229.179/insertBikeInfo.php?owner=" + owner + "&bikecode=" + bikecode + "&latitude=" + Double.toString(latitude) + "&longitude=" + Double.toString(longitude)
                            + "&cost=" + sCost + "&url=" + imageurl + "&lockId=" + sLockId + "&model=" + sModel + "&type=" + sType + "&addInfo=" + sAddInfo).get();
                    finish();
                }catch (ExecutionException e){

                }catch (InterruptedException e){

                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == 1){
                latitude = data.getDoubleExtra("latitude", 0);
                longitude = data.getDoubleExtra("longitude", 0);
                location.setText(Double.toString(latitude)+", "+Double.toString(longitude));
            }
        }
    }
}
