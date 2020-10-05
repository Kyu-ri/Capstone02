package com.example.user.shake;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CheckRequest extends StringRequest {
    final static private String URL = "http://13.125.229.179/Check.php";
    private Map<String, String> parameters;

    public CheckRequest(String borrower,Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("borrower",borrower);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
