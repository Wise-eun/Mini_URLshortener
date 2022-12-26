package com.example.sgdevcmap_urlshortener;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class FragmentURLchart extends Fragment {

    RequestQueue requestQueue;

    View view;
    BarChart chart;
    ArrayList<String> xAxistLabel;
    int[] colorArray = new int[]{Color.parseColor("#0064CD"), Color.parseColor("#1E82CD"), Color.parseColor("#46AAEB"), Color.parseColor("#5ABEF5"), Color.parseColor("#87CEFA")};

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_chart, container, false);
        chart = view.findViewById(R.id.chart_url);


        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        }
        GetURLlist();
        return view;
    }

    public void GetURLlist() {


        String URL = "https://192.168.0.155/getURLcount.php";

        ArrayList<BarEntry> chartData = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("URLCOUNT");
                    JSONObject item;
                    LegendEntry[] legendEntries = new LegendEntry[5];

                    String data;
                    for (int i = 0; i < 5; i++) {
                        item = jsonArray.getJSONObject(i);
                        String url = item.getString("ORIGIN_URL");
                        String count = item.getString("COUNT");
                        LegendEntry entry = new LegendEntry();
                        entry.formColor = colorArray[i];
                        entry.label = url;
                        legendEntries[i] = entry;
                        chartData.add(new BarEntry(i, Float.parseFloat(count)));

                    }


                    BarDataSet barDataSet = new BarDataSet(chartData, "");
                    barDataSet.setColors(colorArray);
                    barDataSet.setValueTextColor(Color.BLACK);
                    barDataSet.setValueTextSize(13f);
                    barDataSet.setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getFormattedValue(float value) {
                            return (String.valueOf((int) value));
                        }
                    });


                    Legend legend = chart.getLegend();
                    legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
                    legend.setCustom(legendEntries);
                    BarData barData = new BarData(barDataSet);
                    legend.setOrientation(Legend.LegendOrientation.VERTICAL);
                    legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
                    legend.setXOffset(10f);
                    legend.getCalculatedLineSizes();
                    legend.setXEntrySpace(10f);
                    legend.setYEntrySpace(0f);
                    legend.setYOffset(30f);
                    legend.setWordWrapEnabled(true);
                    legend.setDrawInside(false);


                    chart.getDescription().setEnabled(false);
                    chart.getXAxis().setEnabled(false);
                    chart.getAxisRight().setEnabled(false);
                    chart.getAxisLeft().setEnabled(false);
                    chart.setFitBars(true);
                    chart.setData(barData);
                    chart.getDescription().setText("URLCOUNT");
                    chart.animateY(1000);
                    chart.setExtraOffsets(5f, 5f, 5f, 20f);

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
