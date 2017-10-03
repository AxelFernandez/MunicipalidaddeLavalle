package com.axelfernandez.municipalidaddelavalle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class webView_Noticias extends AppCompatActivity {
    WebSettings webSettings;
    WebView myWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view__noticias);
        myWebView = (WebView) findViewById(R.id.webviewid);
        final ProgressBar pg =(ProgressBar) findViewById(R.id.progressbarwebview);

        String id = getIntent().getExtras().getString("id");
        String titulo = getIntent().getExtras().getString("title");

        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl("http://lavallemendoza.gob.ar/public/noticias/noticia/idnoticia/"+id);
        myWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100 && pg.getVisibility() == ProgressBar.GONE) {
                    pg.setVisibility(ProgressBar.VISIBLE);
                    myWebView.setVisibility(View.INVISIBLE);
                }

                pg.setProgress(progress);
                if (progress == 100) {
                    pg.setVisibility(ProgressBar.GONE);
                    myWebView.setVisibility(View.VISIBLE);
                }
            }});








    }



}
