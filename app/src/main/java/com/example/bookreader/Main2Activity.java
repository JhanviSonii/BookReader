package com.example.bookreader;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent =getIntent();

        String pdf = intent.getStringExtra("URL");







        WebView webview = (WebView) findViewById(R.id.webView2);
        webview.setHorizontalScrollBarEnabled(false);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url="+pdf);
    }
}


