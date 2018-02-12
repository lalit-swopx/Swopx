package com.swopx.fragment.edit_package;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.swopx.R;
import com.swopx.Urls_Api.Url_Links;

/**
 * Created by SONY on 23-01-2018.
 */

public class Terms extends AppCompatActivity {
    private WebView webView;
    private TextView back;
    private LinearLayout header;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_privacy_layout);
        webView = (WebView) findViewById(R.id.webview);
//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        back = (TextView) findViewById(R.id.back);
        header = (LinearLayout) findViewById(R.id.header);
        header.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        if(getIntent().getStringExtra("value").equalsIgnoreCase("policy"))
        {
            webView.loadUrl(Url_Links.Privacy_Url);
        }
        else
        {
            webView.loadUrl(Url_Links.Terms_Url);
        }

        webView.setHorizontalScrollBarEnabled(false);
    }
}
