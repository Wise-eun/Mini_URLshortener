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


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);



        editText_OriginURL = view.findViewById(R.id.editText_originURL);
        textView_ShortenURL = view.findViewById(R.id.text_shortenURL);
        button_Shortener = view.findViewById(R.id.button_shorten);

        button_Shortener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url_origin = editText_OriginURL.getText().toString();
                textView_ShortenURL.setText(url_origin);
            }
        });
        return view;

    }


}