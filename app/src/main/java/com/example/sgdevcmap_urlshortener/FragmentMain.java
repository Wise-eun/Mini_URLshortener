package com.example.sgdevcmap_urlshortener;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentMain extends Fragment {
    private static String url_origin;

    private Button button_Shortener;
    private EditText editText_OriginURL;
    private TextView textView_ShortenURL;

    char[] base62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);



        editText_OriginURL = view.findViewById(R.id.editText_originURL);
        textView_ShortenURL = view.findViewById(R.id.text_shortenURL);
        button_Shortener = view.findViewById(R.id.button_shorten);

        button_Shortener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long num = Long.parseLong( editText_OriginURL.getText().toString());
                textView_ShortenURL.setText(encode(num));
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

}