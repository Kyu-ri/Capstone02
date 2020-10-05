package com.example.user.shake;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReturnRequest extends StringRequest{

    final static private String URL = "http://13.125.229.179/Return.php";
    private Map<String, String> parameters;

    public ReturnRequest(String borrower,int rent_id, String real_return_time,String return_image_url,int smart_lock_id, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("borrower",borrower);
        parameters.put("rent_id",rent_id+"");
        parameters.put("real_return_time",real_return_time);
        parameters.put("return_image_url",return_image_url);
        parameters.put("smart_lock_id",smart_lock_id+"");
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
