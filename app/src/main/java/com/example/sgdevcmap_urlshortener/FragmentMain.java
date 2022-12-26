package com.example.sgdevcmap_urlshortener;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;

public class FragmentMain extends Fragment {
    private static String url_origin;

    private Button button_Shortener;
    private Button button_Copy;
    private EditText editText_OriginURL;
    private TextView textView_ShortenURL;
    static RequestQueue requestQueue;
    private  String shortenURL;
    private BigInteger urlID;
    ClipboardManager clipboardManager;
    View view;
    char[] base62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_main, container, false);

        if(requestQueue == null)
        {
            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        }

        editText_OriginURL = view.findViewById(R.id.editText_originURL);
        textView_ShortenURL = view.findViewById(R.id.text_shortenURL);
        button_Shortener = view.findViewById(R.id.button_shorten);
        button_Copy = view.findViewById(R.id.button_copy);

        textView_ShortenURL.setVisibility(View.INVISIBLE);
        button_Copy.setVisibility(View.INVISIBLE);
        clipboardManager = (ClipboardManager) view.getContext().getSystemService(CLIPBOARD_SERVICE);


        button_Shortener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url_origin =  editText_OriginURL.getText().toString();
                AlertDialog.Builder errorDialog = new AlertDialog.Builder(getActivity());
                errorDialog.setTitle("ERROR");
if(!url_origin.startsWith("https://") && !url_origin.startsWith("http://"))
{
    errorDialog.setMessage("URL형식은 http://또는 https:// 로 시작되어야합니다.");
    errorDialog.setPositiveButton("확인",null);
    errorDialog.show();
}
else if(!Patterns.WEB_URL.matcher(url_origin).matches())
{
    errorDialog.setMessage("유효하지 않은 URL 입니다.");
    errorDialog.setPositiveButton("확인",null);
    errorDialog.show();
}
else {
    CheckURL();
    textView_ShortenURL.setVisibility(View.VISIBLE);
    button_Copy.setVisibility(View.VISIBLE);

    ClipData clipData = ClipData.newPlainText("URL", shortenURL);
    clipboardManager.setPrimaryClip((clipData));

    Toast.makeText(view.getContext(), "URL이 복사되었습니다.", Toast.LENGTH_SHORT).show();

}
            }
        });

        button_Copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipData clipData = ClipData.newPlainText("URL", shortenURL);
                clipboardManager.setPrimaryClip((clipData));

                Toast.makeText(view.getContext(), "URL이 복사되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });


        return view;

    }

    private String encode(long value)
    {
        StringBuilder sb = new StringBuilder();
        do{
            int i = (int)(value % 62);
            sb.append(base62[i]);
            value /= 62;
        }while (value > 0);

        return sb.toString();
    }

    public void CheckURL( )
    {
Response.Listener<String> responseListener = new Response.Listener<String>() {
    @Override
    public void onResponse(String response) {
        try {
            Log.e("LOG RESPONSE",response);

            JSONObject jsonResponse= new JSONObject(response);

            boolean success = jsonResponse.getBoolean("success");
            if(success) //이미 URL이 저장되어있는 경우
            {     int length = jsonResponse.length();
                shortenURL = "http://192.168.0.155/" + jsonResponse.getString("SHORT_URL");
                textView_ShortenURL.setText(shortenURL);

            }
            else // DB에 없는 URL 일 경우, 새로 인코딩해서 서버에다가 저장하기
            {
                GetHighestID( );

            }
        } catch (JSONException e) {
            Log.e("LOG","ERROR");
            e.printStackTrace();
        }
    }
};
ValidateURLRequest validateURLRequest = new ValidateURLRequest(url_origin, responseListener);
        requestQueue.add(validateURLRequest);
    }
    public void GetHighestID( )
    {
        String URL = "https://192.168.0.155/getHighestID.php";

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                urlID = new BigInteger(response);
                urlID = urlID.add(new BigInteger("1"));
                Log.e("LOG","url ID" + urlID.toString());
                RegisterURL();
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


    public void RegisterURL( ) {
        shortenURL ="https://192.168.0.155/" + encode(urlID.longValue());
        textView_ShortenURL.setText(shortenURL);


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if(response.equals("success"))
                {

                    ClipData clipData = ClipData.newPlainText("URL", "http://192.168.0.155/" + encode(urlID.longValue()));
                    clipboardManager.setPrimaryClip((clipData));

                    Toast.makeText(view.getContext(), "URL이 복사되었습니다.", Toast.LENGTH_SHORT).show();

                }
                else
                    Log.e("LOG" , "DB에 정보넣기 실패!");

            }
        };
        RegisterURLRequest registerURLRequest = new RegisterURLRequest(urlID,url_origin, encode(urlID.longValue()), responseListener);
        requestQueue.add(registerURLRequest);
    }

}