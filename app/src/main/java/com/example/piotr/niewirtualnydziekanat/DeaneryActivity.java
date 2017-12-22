package com.example.piotr.niewirtualnydziekanat;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class DeaneryActivity extends NavigationActivity {

    private TextView hello;
    private String albumNumber;
    private TextView display;
    private String serverContent;
    private final Context context = DeaneryActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deanery);

        Intent intent = getIntent();
        albumNumber = intent.getStringExtra("Album Number");
        serverContent = intent.getStringExtra("Server Content");

        hello = findViewById(R.id.title);
        hello.setText(albumNumber);

        display = findViewById(R.id.display);
        display.setText(serverContent);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //TODO: enable other http requests (POST, DELETE)
}