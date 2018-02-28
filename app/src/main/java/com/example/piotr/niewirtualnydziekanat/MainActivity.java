package com.example.piotr.niewirtualnydziekanat;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button deaneryWebsiteButton = findViewById(R.id.virtual_deanery);
        deaneryWebsiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intent = context.getPackageManager()
                            .getLaunchIntentForPackage("pl.janpogocki.agh.wirtualnydziekanat");
                    context.startActivity(intent);
                } catch (ActivityNotFoundException|NullPointerException e) {
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
                Intent intent = new Intent(context,TimetableActivity.class);
                intent.putExtra("url",
                        "http://planzajec.eaiib.agh.edu.pl/view/timetable/330?date=2018-02-26");
                startActivity(intent);
            }
        });

        Button favSyllabusButton = findViewById(R.id.fav_syllabus);
        favSyllabusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Nie wybrałeś swojego syllabusa", Toast.LENGTH_SHORT).show();
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
