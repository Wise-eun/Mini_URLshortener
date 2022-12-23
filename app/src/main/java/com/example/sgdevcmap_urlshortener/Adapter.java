package com.example.sgdevcmap_urlshortener;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<ListData> urllistdata;



    public Adapter(ArrayList<ListData> urllistdata)
    {
        this.urllistdata = urllistdata;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_url,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        holder.textView_item_origin.setText(urllistdata.get(position).getTextview_originURL());
        holder.textView_item_shorten.setText(urllistdata.get(position).getTextview_shortenURL());
    }

    @Override
    public int getItemCount() {
        return urllistdata.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView_item_origin;
        private  TextView textView_item_shorten;


public ViewHolder(@NonNull View itemView)
{

    super(itemView);

    itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            if(pos != RecyclerView.NO_POSITION){
                ListData data = urllistdata.get(pos);
                ClipboardManager clipboardManager = (ClipboardManager) view.getContext().getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("URL", data.getTextview_shortenURL());
                clipboardManager.setPrimaryClip((clipData));
            
            
                Toast.makeText(view.getContext(), "URL이 복사되었습니다.", Toast.LENGTH_SHORT).show();


            }
        }
    });

    textView_item_origin = (TextView) itemView.findViewById(R.id.item_originURL);
    textView_item_shorten = (TextView) itemView.findViewById(R.id.item_shortenURL);
}

    }

}
