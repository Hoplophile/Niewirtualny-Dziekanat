package com.example.piotr.niewirtualnydziekanat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.sql.Time;

public class TimetableActivity extends NavigationActivity {

    private final Context context = TimetableActivity.this;
    private View progressBar, progressBarBackground;
    private SharedPreferences timetableUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        timetableUrl = this.getPreferences(Context.MODE_PRIVATE);

        progressBar = findViewById(R.id.loading_progress);
        progressBarBackground = findViewById(R.id.progressbar_background);

        final WebView timetableView = findViewById(R.id.timetable_view);                            //TODO: enable JS
        timetableView.getSettings().setJavaScriptEnabled(true);                                      //TODO: enable JS
        timetableView.clearCache(true);
        timetableView.clearHistory();
        timetableView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        final Button star_border = findViewById(R.id.star_border);
        final Button star = findViewById(R.id.star);
        star_border.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor edit = timetableUrl.edit();
                edit.putString("timetable url", timetableView.getUrl());
                edit.commit();
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("timetable url", timetableView.getUrl());
                intent.putExtra("timetable message", "Ustawiono Twój plan zajęć");
                startActivity(intent);
                star.setVisibility(View.VISIBLE);
                star_border.setVisibility(View.GONE);
            }
        });
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor edit = timetableUrl.edit();
                edit.putString("timetable url", "");
                edit.commit();
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("timetable url", "");
                intent.putExtra("timetable message", "Usunięto Twój plan zajęć");
                startActivity(intent);
                star.setVisibility(View.GONE);
                star_border.setVisibility(View.VISIBLE);
            }
        });

        timetableView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                progressBar.setVisibility(View.VISIBLE);
                progressBarBackground.setVisibility(View.VISIBLE);
                view.loadUrl(request.getUrl().toString());
                return false;
            }

            @Override
            public void onPageFinished(WebView webview, String url) {
                progressBar.setVisibility(View.GONE);
                progressBarBackground.setVisibility(View.GONE);
                if (checkIfUrlsEqual(timetableUrl.getString("timetable url", ""), url)) {
                    star.setVisibility(View.VISIBLE);
                    star_border.setVisibility(View.GONE);
                } else {
                    star.setVisibility(View.GONE);
                    star_border.setVisibility(View.VISIBLE);
                }
            }
        });

        String url = getIntent().getStringExtra("url");
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
    public void onBackPressed() {
        final WebView timetableView = findViewById(R.id.timetable_view);
        if (timetableView.canGoBack()) {
            timetableView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private boolean checkIfUrlsEqual(String url1, String url2) {
        int index1 = url1.indexOf("?date");
        int index2 = url2.indexOf("?date");
        return url1.substring(0, index1).equals(url2.substring(0, index2));
    }
}
