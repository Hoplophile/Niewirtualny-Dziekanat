package com.example.piotr.niewirtualnydziekanat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
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
    public boolean onNavigationItemSelected(MenuItem item) {
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

        } else if (id == R.id.nav_deanery) {
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_syllabus) {
            Intent intent = new Intent(context, SyllabusActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_timetable) {
            //TODO activity containing timetable content

        } else if (id == R.id.nav_skos) {
            //TODO activity containing SKOS content

        } else if (id == R.id.nav_site) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.eaiib.agh.edu.pl/"));
            startActivity(intent);

        } else if (id == R.id.nav_about) {
            //TODO activity with info about authors and app
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.facebook.com/agh.si.tech/"));
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
