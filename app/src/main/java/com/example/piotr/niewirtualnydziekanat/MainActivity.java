package com.example.piotr.niewirtualnydziekanat;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends NavigationActivity {

    private final Context context = MainActivity.this;
    private String syllabusUrl;
    private String timetableUrl;
    private SharedPreferences urls;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urls = this.getPreferences(Context.MODE_PRIVATE);

        syllabusUrl = getIntent().getStringExtra("syllabus url");
        if (syllabusUrl != null) {
            Toast.makeText(context, getIntent().getStringExtra("syllabus message"), Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor edit = urls.edit();
            edit.putString("syllabus url", syllabusUrl);
            edit.commit();
        } else {
            syllabusUrl = urls.getString("syllabus url", "");
        }

        timetableUrl = getIntent().getStringExtra("timetable url");
        if (timetableUrl != null) {
            Toast.makeText(context, getIntent().getStringExtra("timetable message"), Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor edit = urls.edit();
            edit.putString("timetable url", timetableUrl);
            edit.commit();
        } else {
            timetableUrl = urls.getString("timetable url", "");
        }

        Button deaneryWebsiteButton = findViewById(R.id.virtual_deanery);
        deaneryWebsiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = context.getPackageManager()
                            .getLaunchIntentForPackage("pl.janpogocki.agh.wirtualnydziekanat");
                    context.startActivity(intent);
                } catch (ActivityNotFoundException | NullPointerException e) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://dziekanat.agh.edu.pl/"));
                    startActivity(intent);
                }
            }
        });

        Button openingHoursButton = findViewById(R.id.opening_hours);
        openingHoursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OpeningHoursActivity.class);
                startActivity(intent);
            }
        });

        Button staffButton = findViewById(R.id.staff);
        staffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AuthoritiesActivity.class);
                startActivity(intent);
            }
        });

        Button newsButton = findViewById(R.id.news);
        newsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewsActivity.class);
                startActivity(intent);
            }
        });

        Button favTimetableButton = findViewById(R.id.fav_timetable);
        favTimetableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timetableUrl == null || timetableUrl.equals("")) {
                    Toast.makeText(context, "Nie wybrałeś swojego planu zajęć", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(context, TimetableActivity.class);
                    intent.putExtra("url", timetableUrl);
                    startActivity(intent);
                }
            }
        });

        Button favSyllabusButton = findViewById(R.id.fav_syllabus);
        favSyllabusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (syllabusUrl == null || syllabusUrl.equals("")) {
                    Toast.makeText(context, "Nie wybrałeś swojego syllabusa", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(context, SyllabusActivity.class);
                    intent.putExtra("url", syllabusUrl);
                    startActivity(intent);
                }
            }
        });

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

}
