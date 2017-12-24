package com.example.piotr.niewirtualnydziekanat;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AuthoritiesActivity extends NavigationActivity {

    String dean_name[] = {"Ryszard Sroka", "Andrzej Izworski", "Janusz Gajda", "Szczepan Moskwa", "Krzysztof Kluza", "Robert Stala", "Zbigniew Marszałek"};
    String auth_name[] = {"Małgorzata Tabor", "Alina Wącławska", "Małgorzata Frączek", "Angelika Burgknap-Rumian", "Małgorzata Frączek"};
    String others_name[] = {"Wioletta Serwatka", "Sara Rahman-Kula"};
    String ext_name = "Anna Jasuba";
    String dean_role[] = {"Dziekan Wydziału", "Prodziekan ds. Kształcenia - IB", "Prodziekan ds. Nauki i Współpracy", "Prodziekan ds. Studenckich i Ogólnych",
            "Prodziekan ds. Kształcenia - AiR, Informatyka", "Prodziekan ds. Kształcenia - MTM, Elektrotechnika", "Dyrektor Administracyjny Wydziału"};
    String auth_role[] = {"Automatyka i Robotyka", "Elektrotechnika", "Informatyka", "Inżynieria Biomedyczna", "Mikroelektronika w Technice i Medycynie"};
    String others_role[] = {"Sekcja socjalna", "Studia doktoranckie"};
    String ext_role = "Automatyka i Robotyka, Elektrotechnika";
    String auth_mail[] = {"mtabor@agh.edu.pl", "awaclaw@agh.edu.pl", "fraczek@agh.edu.pl", "biomed@agh.edu.pl", "fraczek@agh.edu.pl"};
    String auth_tel[] = {"126174566", "126174893", "126174892", "126172860", "126174892"};

    private List<HashMap<String, String>> mList = new ArrayList<>();
    private final Context context = AuthoritiesActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorities);

        ListView deansList = findViewById(R.id.deans_list);
        LinearLayout fullTimeList = findViewById(R.id.full_time_list);
        LinearLayout extramuralList = findViewById(R.id.extramural_list);
        LinearLayout othersList = findViewById(R.id.others_list);

        PopupWindow popupWindow = new PopupWindow(this);

        for (int i = 0; i < dean_name.length; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("Name", dean_name[i]);
            map.put("Role", dean_role[i]);
            mList.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(context, mList, R.layout.authorities_list_item,
                new String[] {"Name", "Role"}, new int[] {R.id.authority_name, R.id.authority_role});
        ListView listView = findViewById(R.id.deans_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        for(int i=0; i<auth_name.length; i++){
            final AuthorityItem authorityItem =
                    new AuthorityItem(context,auth_name[i],auth_role[i]);
            authorityItem.setId(i);
            fullTimeList.addView(authorityItem);
        }
        final AuthorityItem authorityItem =
                new AuthorityItem(context,ext_name,ext_role);
        extramuralList.addView(authorityItem);

        final AuthorityItem authorityItem1 =
                new AuthorityItem(context,others_name[0],others_role[0]);
        othersList.addView(authorityItem1);

        final AuthorityItem authorityItem2 =
                new AuthorityItem(context,others_name[1],others_role[1]);
        othersList.addView(authorityItem2);

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
}
