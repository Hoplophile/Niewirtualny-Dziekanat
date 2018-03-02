package com.example.piotr.niewirtualnydziekanat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.piotr.niewirtualnydziekanat.adapter.NewsAdapter;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends NavigationActivity {

    private final Context context = NewsActivity.this;
    private ListView lv;
    private View progressBar;
    private NewsActivity.News news = null;
    private final String ADDRESS = "https://www.eaiib.agh.edu.pl/aktualnosci.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        lv = findViewById(R.id.lv);
        progressBar = findViewById(R.id.loading_progress);

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

        setupNews(ADDRESS);

        Button archiveButton = findViewById(R.id.archive);
        archiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OpenNewsActivity.class);
                intent.putExtra("url","https://www.eaiib.agh.edu.pl/aktualnosci.html&pg=1");
                startActivity(intent);
            }
        });
    }

    private void setupNews(String address) {
        news = new NewsActivity.News(address);
        news.execute();
    }

    public String HttpGet(String urlString) {
        String result = "";
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            result = inputStreamToString(inputStream);
        } catch (Exception e) {
            Toast.makeText(context, "Coś poszło nie tak :c", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    private String inputStreamToString(InputStream is) {
        String result = "";
        String line = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = br.readLine()) != null) {
                result += line;
            }
            is.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return result;
    }

    public class News extends AsyncTask<Void, Void, Boolean> {

        private String address;
        private String content;
        private List<String> adapterList;

        News(String text) {
            address = text;
            adapterList = new ArrayList<>();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                progressBar.setVisibility(View.VISIBLE);
                content = HttpGet(address);
                content = StringUtils.substringBetween(content, "<h1>Aktualności</h1>", "ourPager");
                String[] list = StringUtils.splitByWholeSeparator(content, "</li>");
                for(int i = 0; i < list.length-1; i++) {
                    adapterList.add(i, list[i]);
                }
            } catch (Exception e) {
                Toast.makeText(context, "Coś poszło nie tak :c", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast.makeText(context, "brak polaczenia z internetem", Toast.LENGTH_SHORT).show();
            }
            NewsAdapter adp = new NewsAdapter(context, adapterList);
            lv.setAdapter(adp);
            progressBar.setVisibility(View.GONE);
        }
    }

}
