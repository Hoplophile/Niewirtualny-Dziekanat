package com.example.piotr.niewirtualnydziekanat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView hello;
    private String albumNumber;
    private TextView display;
    private String serverContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        albumNumber = intent.getStringExtra("Album Number");
        serverContent = intent.getStringExtra("Server Content");

        hello = findViewById(R.id.title);
        hello.setText(albumNumber);

        display = findViewById(R.id.display);
        display.setText(serverContent);
    }

    //TODO: enable other http requests (POST, DELETE)
}