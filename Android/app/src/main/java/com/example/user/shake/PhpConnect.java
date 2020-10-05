package com.example.user.shake;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PhpConnect extends AsyncTask<String, String, ArrayList<String>> {
    @Override
    protected ArrayList<String> doInBackground(String... params) {
        ArrayList<String> output = new ArrayList<String>();
        try {

            URL url = new URL(params[0]);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if(conn != null){
                conn.setConnectTimeout(10000);
                conn.setUseCaches(false);

                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    for(; true;){
                        String line = br.readLine();
                        if(line == null) {
                            break;
                        }
                        output.add(line);
                    }
                    br.close();
                }
                conn.disconnect();
            }else{
                //fail to connect
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return output;
    }
}
