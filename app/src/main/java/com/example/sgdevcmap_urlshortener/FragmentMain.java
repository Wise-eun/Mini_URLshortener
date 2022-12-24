package com.example.sgdevcmap_urlshortener;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
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

import java.util.ArrayList;

public class FragmentMain extends Fragment {
    private static String url_origin;

    private Button button_Shortener;
    private Button button_Copy;
    private EditText editText_OriginURL;
    private TextView textView_ShortenURL;
    static RequestQueue requestQueue;
    private  String shortenURL;

    char[] base62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
String checkHTTP = "https://";

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

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


        button_Shortener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String originURL =  editText_OriginURL.getText().toString();
               // textView_ShortenURL.setText(encode(num));
                AlertDialog.Builder errorDialog = new AlertDialog.Builder(getActivity());
                errorDialog.setTitle("ERROR");
if(!originURL.startsWith("https://"))
{
    errorDialog.setMessage("URL형식은 https://로 시작되어야합니다.");
    errorDialog.setPositiveButton("확인",null);
    errorDialog.show();
}
else if(!Patterns.WEB_URL.matcher(originURL).matches())
{
    errorDialog.setMessage("유효하지 않은 URL 입니다.");
    errorDialog.setPositiveButton("확인",null);
    errorDialog.show();
}
else {
    CheckURLInDB();
    textView_ShortenURL.setVisibility(View.VISIBLE);
    button_Copy.setVisibility(View.VISIBLE);
}
            }
        });

        button_Copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) view.getContext().getSystemService(CLIPBOARD_SERVICE);
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

    public void CheckURLInDB( )
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
                Log.e("LOG",String.valueOf(jsonResponse.length()));
                Log.e("LOG",jsonResponse.toString());
                shortenURL = "http://auddms.ivyro.net/" + jsonResponse.getString("SHORT_URL");
                textView_ShortenURL.setText(shortenURL);

                Log.e("LOG","DB에 존재하고있는 URL 입니다! :D");
                Log.e("LOG","받아온 short 는 " + jsonResponse.getString("SHORT_URL"));

            }
            else // DB에 없는 URL 일 경우
            {
                //새로 인코딩해서, 서버에다가 저장하기
                Log.e("LOG","DB에 존재하지 않는 URL 입니다!");


            }
        } catch (JSONException e) {
            Log.e("LOG","ERROR");
            e.printStackTrace();
        }
    }
};
ValidateURLRequest validateURLRequest = new ValidateURLRequest(editText_OriginURL.getText().toString(), responseListener);
        requestQueue.add(validateURLRequest);
    }





}