package com.example.piotr.niewirtualnydziekanat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class SyllabusActivity extends NavigationActivity {

    private final Context context = SyllabusActivity.this;
    private View progressBar, progressBarBackground;
    private SharedPreferences syllabusUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);
        syllabusUrl = this.getPreferences(Context.MODE_PRIVATE);

        progressBar = findViewById(R.id.loading_progress);
        progressBarBackground = findViewById(R.id.progressbar_background);

        final WebView syllabusView = findViewById(R.id.syllabus_view);
        syllabusView.getSettings().setJavaScriptEnabled(true);                                      //TODO: enable JS
        syllabusView.clearCache(true);
        syllabusView.clearHistory();
        syllabusView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        final Button star_border = findViewById(R.id.star_border);
        final Button star = findViewById(R.id.star);
        star_border.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor edit = syllabusUrl.edit();
                edit.putString("syllabus url", syllabusView.getUrl());
                edit.commit();
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("syllabus url", syllabusView.getUrl());
                intent.putExtra("syllabus message", "Ustawiono Twój syllabus");
                startActivity(intent);
                star.setVisibility(View.VISIBLE);
                star_border.setVisibility(View.GONE);
            }
        });
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor edit = syllabusUrl.edit();
                edit.putString("syllabus url", "");
                edit.commit();
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("syllabus url", "");
                intent.putExtra("syllabus message", "Usunięto Twój syllabus");
                startActivity(intent);
                star.setVisibility(View.GONE);
                star_border.setVisibility(View.VISIBLE);
            }
        });

        syllabusView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                progressBar.setVisibility(View.VISIBLE);
                progressBarBackground.setVisibility(View.VISIBLE);
                view.loadUrl(request.getUrl().toString());
                return false;
            }

            @Override
            public void onPageFinished(WebView webview, String url) {
                //webview.loadUrl("javascript:(function() { document.getElementsByTagName" +
                //        "('main-content')[0].style.display=\"none\"; })()");
                progressBar.setVisibility(View.GONE);
                progressBarBackground.setVisibility(View.GONE);
                if (syllabusUrl.getString("syllabus url", "").equals(url)) {
                    star.setVisibility(View.VISIBLE);
                    star_border.setVisibility(View.GONE);
                } else {
                    star.setVisibility(View.GONE);
                    star_border.setVisibility(View.VISIBLE);
                }
            }
        });

        String url = getIntent().getStringExtra("url");
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
    public void onBackPressed() {
        final WebView syllabusView = findViewById(R.id.syllabus_view);
        if (syllabusView.canGoBack()) {
            syllabusView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
