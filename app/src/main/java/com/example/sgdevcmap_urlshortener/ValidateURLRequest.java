package com.example.sgdevcmap_urlshortener;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ValidateURLRequest extends StringRequest {
    final static private String URL = "https://192.168.0.155/checkURL.php";
    private Map<String, String> map;

    public ValidateURLRequest(String ORIGIN_URL, Response.Listener<String>listener)
    {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("ORIGIN_URL", ORIGIN_URL);
    }

    protected Map<String,String> getParams() throws AuthFailureError
    {
        return  map;
    }
}
