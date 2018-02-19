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

public class TimetableActivity extends NavigationActivity {

    private final String url="http://planzajec.eaiib.agh.edu.pl";
    private View progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        progressBar = findViewById(R.id.loading_progress);

        final WebView timetableView = findViewById(R.id.timetable_view);                            //TODO: enable JS
        timetableView.clearCache(true);
        timetableView.clearHistory();
        timetableView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        timetableView.getSettings().setJavaScriptEnabled(true);
        timetableView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return false;
            }
            @Override
            public void onPageFinished(WebView webview, String url){
                progressBar.setVisibility(View.GONE);
            }
        });

        timetableView.loadUrl(url);

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
        final WebView timetableView = findViewById(R.id.timetable_view);
        if(timetableView.canGoBack()){
            timetableView.goBack();
        } else{
            super.onBackPressed();
        }
    }
}
