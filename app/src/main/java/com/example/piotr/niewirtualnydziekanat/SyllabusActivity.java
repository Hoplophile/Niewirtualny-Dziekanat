package com.example.piotr.niewirtualnydziekanat;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SyllabusActivity extends NavigationActivity {

    private final String url="https://syllabuskrk.agh.edu.pl/2016-2017/pl/treasuries/academy_units/16/study_plans";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);


        final WebView syllabusView = findViewById(R.id.syllabus_view);
        syllabusView.getSettings().setJavaScriptEnabled(true);
        syllabusView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return false;
            }
            @Override
            public void onPageFinished(WebView webview, String url){
                //webview.loadUrl("javascript:(function() { document.getElementsByTagName" +
                //        "('main-content')[0].style.display=\"none\"; })()");
                //TODO DELETE FEEDBACK ICON
            }
        });

        syllabusView.loadUrl(url);

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
        final WebView syllabusView = findViewById(R.id.syllabus_view);
        if(syllabusView.canGoBack()){
            syllabusView.goBack();
        } else{
            super.onBackPressed();
        }
    }
}
