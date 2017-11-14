package com.example.piotr.niewirtualnydziekanat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView hello;
    private String albumNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        albumNumber = intent.getStringExtra("Album Number");

        hello = findViewById(R.id.album_number);
        hello.setText(albumNumber);
    }
}
