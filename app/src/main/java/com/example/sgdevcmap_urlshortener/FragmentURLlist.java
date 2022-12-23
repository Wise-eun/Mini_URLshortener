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

import java.util.ArrayList;
import java.util.Arrays;

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
        //recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        GetURLlist(items );



return view;


    }

    public void GetURLlist( ArrayList<ListData> items)
    {
        String URL = "http://auddms.ivyro.net/showURLlist.php";

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replaceAll("\\[","");
                response = response.replaceAll("\\]","");
                response = response.replaceAll(",","");
                //Toast.makeText(getActivity().getApplicationContext(), "응답 : " + response, Toast.LENGTH_SHORT).show();
                String[] strArr = response.split("\"");

                for(int i=1;i<strArr.length;i+=4){
                    items.add(new ListData("http://" +strArr[i], "http://auddms.ivyro.net/" + strArr[i+2]));
                }

                adapter = new Adapter(items);
                recyclerView.setAdapter(adapter);
                Log.e("Log","count = " + Integer.toString(adapter.getItemCount()));
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), "에러 : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        request.setShouldCache(false);
        requestQueue.add(request);

    }




}
