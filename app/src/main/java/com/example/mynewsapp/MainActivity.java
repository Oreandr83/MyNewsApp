package com.example.mynewsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.mynewsapp.Fragments.FavoriteFragment;
import com.example.mynewsapp.Fragments.InfoFragment;
import com.example.mynewsapp.Fragments.NewsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.id_bott_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);

        if(savedInstanceState == null){
            //fragment by default
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new NewsFragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment = null;
                    switch (item.getItemId()){
                        case R.id.favorite:
                            fragment = new FavoriteFragment();
                            break;
                        case R.id.photo_news:
                            fragment = new NewsFragment();
                            break;
                        case R.id.info:
                            fragment = new InfoFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
                    return true;
                }
            };
}