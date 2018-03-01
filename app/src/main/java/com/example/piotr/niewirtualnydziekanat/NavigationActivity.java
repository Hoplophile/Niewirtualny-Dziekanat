package com.example.piotr.niewirtualnydziekanat;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    Context context = NavigationActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem  item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_start) {
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_authorities) {
            Intent intent = new Intent(context, AuthoritiesActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_opening_hours) {
            Intent intent = new Intent(context, OpeningHoursActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_syllabus) {
            Intent intent = new Intent(context, SyllabusActivity.class);
            intent.putExtra("url",
                    "https://syllabuskrk.agh.edu.pl/2016-2017/pl/treasuries/academy_units/16/study_plans");
            startActivity(intent);

        } else if (id == R.id.nav_timetable) {
            Intent intent = new Intent(context, TimetableActivity.class);
            intent.putExtra("url", "http://planzajec.eaiib.agh.edu.pl");
            startActivity(intent);

        } else if (id == R.id.nav_skos) {
            Intent intent = new Intent(context, SkosActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_map) {
            Intent intent = new Intent(context, MapActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_site) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.eaiib.agh.edu.pl/"));
            startActivity(intent);

        } else if (id == R.id.nav_wu) {
            try{
                Intent intent = context.getPackageManager()
                        .getLaunchIntentForPackage("pl.janpogocki.agh.wirtualnydziekanat");
                context.startActivity(intent);
            } catch (ActivityNotFoundException |NullPointerException e) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://dziekanat.agh.edu.pl/"));
                startActivity(intent);
            }
        }
                                                                                                    //TODO activity with info about authors and app
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
