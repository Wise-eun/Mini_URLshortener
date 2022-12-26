package com.example.sgdevcmap_urlshortener;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class FragmentURLlist extends Fragment {
    static RequestQueue requestQueue;
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        }
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_list, container, false);
        ArrayList<ListData> items = new ArrayList<>();


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_urllist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        GetURLlist(items);


        return view;


    }

    public void GetURLlist(ArrayList<ListData> items) {


        String URL = "https://192.168.0.155/showURLlist.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("URLLIST");
                    JSONObject item;

                    for (int i = 0; i < jsonArray.length(); i ++) {
                        item = jsonArray.getJSONObject(i);
                        String originURL = item.getString("ORIGIN_URL");
                        String shortURL = item.getString("SHORT_URL");
                        items.add(new ListData(originURL, "http://192.168.0.155/" + shortURL));

                    }

                    adapter = new Adapter(items);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), "에러 : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ERROR", error.getMessage());
            }
        });


        request.setShouldCache(false);
        requestQueue.add(request);

    }


}
