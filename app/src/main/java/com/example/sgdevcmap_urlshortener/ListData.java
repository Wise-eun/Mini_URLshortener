package com.example.sgdevcmap_urlshortener;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListData  {
    private String textview_originURL;
    private String textview_shortenURL;

    ListData(String textview_originURL, String textview_shortenURL)
    {
        this. textview_originURL = textview_originURL;
        this.textview_shortenURL = textview_shortenURL;
    }

    public String getTextview_originURL()
    {
        return textview_originURL;
    }
    public String getTextview_shortenURL()
    {
        return textview_shortenURL;
    }

    public void setTextview_originURL(String textview_originURL) {
        this.textview_originURL = textview_originURL;
    }

    public void setTextview_shortenURL(String textview_shortenURL) {
        this.textview_shortenURL = textview_shortenURL;
    }
}
