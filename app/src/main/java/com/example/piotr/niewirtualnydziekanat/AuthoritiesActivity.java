package com.example.piotr.niewirtualnydziekanat;

import android.app.Activity;
import android.content.ContentValues;
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
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AuthoritiesActivity extends NavigationActivity {

    String dean_name[] = {"Ryszard Sroka", "Andrzej Izworski", "Janusz Gajda", "Szczepan Moskwa", "Krzysztof Kluza", "Robert Stala", "Zbigniew Marszałek"};
    String auth_name[] = {"Małgorzata Tabor", "Alina Wącławska", "Małgorzata Frączek", "Angelika Burgknap-Rumian", "Małgorzata Frączek"};
    String others_name[] = {"Wioletta Serwatka", "Sara Rahman-Kula"};
    String ext_name[] = {"Anna Jasuba"};
    String dean_role[] = {"Dziekan Wydziału", "Prodziekan ds. Kształcenia - IB", "Prodziekan ds. Nauki i Współpracy", "Prodziekan ds. Studenckich i Ogólnych",
            "Prodziekan ds. Kształcenia - AiR, Informatyka", "Prodziekan ds. Kształcenia - MTM, Elektrotechnika", "Dyrektor Administracyjny Wydziału"};
    String auth_role[] = {"Automatyka i Robotyka", "Elektrotechnika", "Informatyka", "Inżynieria Biomedyczna", "Mikroelektronika w Technice i Medycynie"};
    String others_role[] = {"Sekcja socjalna", "Studia doktoranckie"};
    String ext_role[] = {"Automatyka i Robotyka, Elektrotechnika"};
    String auth_mail[] = {"mtabor@agh.edu.pl", "awaclaw@agh.edu.pl", "fraczek@agh.edu.pl", "biomed@agh.edu.pl", "fraczek@agh.edu.pl"};
    String auth_tel[] = {"126174566", "126174893", "126174892", "126172860", "126174892"};

    private List<HashMap<String, String>> mList = new ArrayList<>();
    private final Context context = AuthoritiesActivity.this;
    private final Activity activity = this;
    String auth_name_p, auth_role_p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorities);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        LinearLayout deanLayout = findViewById(R.id.deans_list);
        LinearLayout authoritiesLayout = findViewById(R.id.full_time_list);
        LinearLayout othersLayout = findViewById(R.id.others_list);
        LinearLayout extraLayout = findViewById(R.id.extramural_list);

        setupLayout(dean_name, dean_role, deanLayout);
        setupLayout(auth_name, auth_role, authoritiesLayout);
        setupLayout(others_name, others_role, othersLayout);
        setupLayout(ext_name, ext_role, extraLayout);
    }

    private void setupLayout(String[] name, String[] role, LinearLayout layout) {
        for(int i=0; i<name.length; i++) {
            auth_name_p = name[i];
            auth_role_p = role[i];
            View child = getLayoutInflater().inflate(R.layout.authorities_list_item, null);
            TextView authorityName = child.findViewById(R.id.authority_name);
            authorityName.setText(name[i]);
            TextView authorityRole = child.findViewById(R.id.authority_role);
            authorityRole.setText(role[i]);
            layout.addView(child);
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AuthorityPopup authorityPopup = new AuthorityPopup();
                    authorityPopup.showDialog(activity, auth_name_p, auth_role_p, "222", "kbkfes");
                }
            });
        }
    }
}
