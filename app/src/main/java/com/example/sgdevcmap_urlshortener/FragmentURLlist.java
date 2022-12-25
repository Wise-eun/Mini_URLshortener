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


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);
        if(requestQueue == null)
        {
            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        }
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_list, container, false);
        ArrayList<ListData> items = new ArrayList<>();


        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_urllist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        GetURLlist(items );



return view;


    }

    public void GetURLlist( ArrayList<ListData> items)
    {


        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }

            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType)
                    throws java.security.cert.CertificateException {
                // TODO Auto-generated method stub
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType)
                    throws java.security.cert.CertificateException {
                // TODO Auto-generated method stub
            }
        }};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }

        HostnameVerifier allHostsValid = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                //특정 hostname만 승인을 해주는 형태
                if(hostname.equalsIgnoreCase("192.168.0.155")) //내가 우회하고자하는 url 주소를 넣어준다.
                    return true;
                else
                    return false;
            }
        };

        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        String URL = "https://192.168.0.155/showURLlist.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replaceAll("\\[","");
                response = response.replaceAll("\\]","");
                response = response.replaceAll(",","");
                //Toast.makeText(getActivity().getApplicationContext(), "응답 : " + response, Toast.LENGTH_SHORT).show();
                response = response.replaceAll("\\\\","");
                String[] strArr = response.split("\"");

                for(int i=1;i<strArr.length;i+=4){
                    items.add(new ListData(strArr[i], "http://192.168.0.155/" + strArr[i+2]));
                }

                adapter = new Adapter(items);
                recyclerView.setAdapter(adapter);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), "에러 : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ERROR",error.getMessage());
            }
        });


        request.setShouldCache(false);
        requestQueue.add(request);

    }




}
