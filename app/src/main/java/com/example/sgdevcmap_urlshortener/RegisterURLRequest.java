package com.example.sgdevcmap_urlshortener;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class RegisterURLRequest extends StringRequest {
    final static private String URL = "http://192.168.0.155/registerURL.php";
    private Map<String, String> map;

    public RegisterURLRequest(BigInteger ID, String ORIGIN_URL, String SHORT_URL, Response.Listener<String>listener)
    {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("ID", ID + "");
        map.put("ORIGIN_URL", ORIGIN_URL);
        map.put("SHORT_URL", SHORT_URL);
    }

    protected Map<String,String> getParams() throws AuthFailureError
    {
        return  map;
    }
}
