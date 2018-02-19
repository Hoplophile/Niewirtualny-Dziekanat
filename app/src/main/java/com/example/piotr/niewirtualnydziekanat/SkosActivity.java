package com.example.piotr.niewirtualnydziekanat;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by piotr on 25.12.2017.
 */

public class SkosActivity extends NavigationActivity {

    private final String url="https://skos.agh.edu.pl/";
    private View progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skos);

        progressBar = findViewById(R.id.loading_progress);

        final WebView skosView = findViewById(R.id.skos_view);
        skosView.getSettings().setJavaScriptEnabled(true);                                          //TODO: enable JS
        skosView.clearCache(true);
        skosView.clearHistory();
        skosView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        skosView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                progressBar.setVisibility(View.VISIBLE);
                return false;
            }
            @Override
            public void onPageFinished(WebView webview, String url){
                progressBar.setVisibility(View.GONE);
            }
        });

        skosView.loadUrl(url);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
    }

    @Override
    public void onBackPressed(){
        final WebView skosView = findViewById(R.id.skos_view);
        if(skosView.canGoBack()){
            skosView.goBack();
        } else{
            super.onBackPressed();
        }
    }
}
