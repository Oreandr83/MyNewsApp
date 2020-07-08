package com.example.mynewsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailedActivity extends AppCompatActivity {

    private TextView titleD, sourceD, timeD, descriptD;
    private ImageView imageView;
    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        titleD = findViewById(R.id.title_detail);
        sourceD = findViewById(R.id.source_detail);
        timeD = findViewById(R.id.time_detail);
        descriptD = findViewById(R.id.descript_detail);

        imageView = findViewById(R.id.image_detail);
        webView = findViewById(R.id.webView);

        progressBar = findViewById(R.id.web_prog_bar);
        progressBar.setVisibility(View.VISIBLE);

        //get extras of adapter
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String source = intent.getStringExtra("source");
        String time = intent.getStringExtra("time");
        String descript = intent.getStringExtra("description");
        String imageUrl = intent.getStringExtra("imageUrl");
        String url = intent.getStringExtra("url");


        titleD.setText(title);
        sourceD.setText(source);
        timeD.setText(time);
        descriptD.setText(descript);

        Picasso.with(DetailedActivity.this).load(imageUrl).into(imageView);

        webView.getSettings().setDomStorageEnabled(true);
   //     webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(ImageView.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
        if(webView.isShown()){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }

    }
}