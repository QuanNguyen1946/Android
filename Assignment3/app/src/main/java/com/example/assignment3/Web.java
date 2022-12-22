package com.example.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Web extends AppCompatActivity {

    WebView webview ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        webview = (WebView) findViewById(R.id.webview);

        Intent intent = getIntent();

        String link  = intent.getStringExtra("linkweb");
        webview.loadUrl(link);
    }
}
