package com.example.piotr.niewirtualnydziekanat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OpeningHoursActivity extends AppCompatActivity {

    private TextView title;
    private String[] types;
    private String[] hours;
    private ListView lv;
    Context context = OpeningHoursActivity.this;
    private List<HashMap<String,String>> myList;
    private SimpleAdapter adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_hours);

        Intent intent = getIntent();
        title = (TextView) findViewById(R.id.title);
        title.setText(intent.getStringExtra("Opening Hours"));

        //those arrays will hold data to be displayed
        //for now it's just random text
        types = intent.getStringArrayExtra("Array of titles");
        hours = intent.getStringArrayExtra("Array of hours");
        myList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.lv);
        initializer();
        adp = adapterMethod(myList);
        lv.setAdapter(adp);
    }

    private void initializer() {
        for (int i = 0; i < types.length; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("Types", types[i]);
            map.put("Hours", hours[i]);
            myList.add(map);
        }
    }

    private SimpleAdapter adapterMethod(List<HashMap<String,String>> llist) {
        SimpleAdapter adapter = new SimpleAdapter(context, llist, R.layout.custom_list, new String[] {"Types", "Hours"},
                new int[] {R.id.type, R.id.content});
        return adapter;
    }
}
