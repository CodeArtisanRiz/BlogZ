package com.t3g.blogz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.Objects;

public class PostActivity extends AppCompatActivity {
    WebView contentView;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Intent data = getIntent();
        String title = data.getStringExtra("title");
        String content = data.getStringExtra("content");

        Toast.makeText(getApplicationContext(), title, Toast.LENGTH_SHORT).show();

        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        TextView contents = findViewById(R.id.contentTxt);
//        contents.setText(content);
        contentView = findViewById(R.id.contentView);
        final String mimeType = "text/html";
        final String encoding = "UTF-8";

        contentView.loadDataWithBaseURL("",content,mimeType,encoding,"");
        contentView.getSettings().setJavaScriptEnabled(true);
        contentView.getSettings().setDomStorageEnabled(true);
        contentView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        contentView.setWebChromeClient(new WebChromeClient() {


        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    private void injectJS() {
        try {
            InputStream inputStream = getAssets().open("jscript.js");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
            contentView.loadUrl("javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var script = document.createElement('script');" +
                    "script.type = 'text/javascript';" +
                    "script.innerHTML = window.atob('" + encoded + "');" +
                    "parent.appendChild(script)" +
                    "})()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}