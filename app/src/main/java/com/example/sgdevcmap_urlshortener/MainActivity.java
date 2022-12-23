package com.example.sgdevcmap_urlshortener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentURLchart fragmentURLchart= new FragmentURLchart();
    private FragmentURLlist fragmentURLlist= new FragmentURLlist();
    private FragmentMain fragmentMain= new FragmentMain();

    public ArrayList<ListData> items = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragmentMain).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById((R.id.bottom_menu));
        bottomNavigationView.setOnNavigationItemSelectedListener(new FragmentSelectedListener());


    }

    class FragmentSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            if(item.getItemId() == R.id.tab_list)
                transaction.replace(R.id.frameLayout,fragmentURLlist).commitAllowingStateLoss();
            else if(item.getItemId() == R.id.tab_chart)
                transaction.replace(R.id.frameLayout,fragmentURLchart).commitAllowingStateLoss();
            else if(item.getItemId() == R.id.tab_home)
                transaction.replace(R.id.frameLayout,fragmentMain).commitAllowingStateLoss();

            return true;
        }
    }



}