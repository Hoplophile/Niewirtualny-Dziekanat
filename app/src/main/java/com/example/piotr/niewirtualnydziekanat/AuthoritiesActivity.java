package com.example.piotr.niewirtualnydziekanat;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AuthoritiesActivity extends NavigationActivity {

    String dean_name[] = {"Ryszard Sroka", "Andrzej Izworski", "Janusz Gajda", "Szczepan Moskwa",   //
            "Krzysztof Kluza", "Robert Stala", "Zbigniew Marszałek"};
    String dean_full_name[] = {"dr hab. inż. Ryszard Sroka", "dr inż. Andrzej Izworski",
            "prof. dr hab. inż. Janusz Gajda", "dr inż. Szczepan Moskwa", "dr inż. Krzysztof Kluza",
            "dr hab. inż. Robert Stala", "mgr inż. Zbigniew Marszałek"};
    String auth_name[] = {"Małgorzata Tabor", "Alina Wącławska", "Małgorzata Frączek",
            "Angelika Burgknap-Rumian", "Małgorzata Frączek"};
    String others_name[] = {"Wioletta Serwatka", "Sara Rahman-Kula"};
    String ext_name[] = {"Anna Jasuba"};
    String dean_role[] = {"Dziekan Wydziału", "Prodziekan ds. Kształcenia - IB",
            "Prodziekan ds. Nauki i Współpracy", "Prodziekan ds. Studenckich i Ogólnych",
            "Prodziekan ds. Kształcenia - AiR, Informatyka",
            "Prodziekan ds. Kształcenia - MTM, Elektrotechnika", "Dyrektor Administracyjny Wydziału"};
    String auth_role[] = {"Automatyka i Robotyka", "Elektrotechnika", "Informatyka",
            "Inżynieria Biomedyczna", "Mikroelektronika w Technice i Medycynie"};
    String others_role[] = {"Sekcja socjalna", "Studia doktoranckie"};
    String ext_role[] = {"Automatyka i Robotyka, Elektrotechnika"};
    String dean_tel[] = {"12 617 28 00", "12 617 41 32", "12 617 41 37", "12 617 41 38",
            "12 617 41 35", "12 617 41 36", "12 617 32 93"};
    String auth_tel[] = {"126174566", "126174893", "126174892", "126172860", "126174892"};
    String others_tel[] = {"12 617 41 70", "12 617 28 44"};
    String ext_tel[] = {"12 617 28 05"};
    String dean_mail[] = {"dziekan@eaiib.agh.edu.pl", "dziek.ib@eaiib.agh.edu.pl",
            "dziek.nauka@eaiib.agh.edu.pl", "dziek.stud@eaiib.agh.edu.pl",
            "dziek.air@eaiib.agh.edu.pl; dziek.it@eaiib.agh.edu.pl",
            "dziek.mtm@eaiib.agh.edu.pl; dziek.et@eaiib.agh.edu.pl", "zbigniew.marszalek@agh.edu.pl"};
    String auth_mail[] = {"mtabor@agh.edu.pl", "awaclaw@agh.edu.pl", "fraczek@agh.edu.pl",
            "biomed@agh.edu.pl", "fraczek@agh.edu.pl"};
    String others_mail[] = {"foszczka@agh.edu.pl", "sara@agh.edu.pl"};
    String ext_mail[] = {"jasuba@agh.edu.pl"};
    String duty_hours[] = {"środa: 10.45-11.45\nczwartek: 12.00-13.00", "wtorek: 13.00-14.00", "wtorek: 12.30-14.30",
    "wtorek: 11.00-12.00\npiątek: 11.30-12.30", "poniedziałek: 11.00-12.00\npiątek 14.00-15.00"};

    private final Context context = AuthoritiesActivity.this;
    private final Activity activity = this;
    LinearLayout deanLayout, authoritiesLayout, othersLayout, extraLayout;

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

        deanLayout = findViewById(R.id.deans_list);
        authoritiesLayout = findViewById(R.id.full_time_list);
        othersLayout = findViewById(R.id.others_list);
        extraLayout = findViewById(R.id.extramural_list);

        setupLayout(dean_name, dean_role, deanLayout);
        setupLayout(auth_name, auth_role, authoritiesLayout);
        setupLayout(others_name, others_role, othersLayout);
        setupLayout(ext_name, ext_role, extraLayout);
    }

    private void setupLayout(String[] name, String[] role, final LinearLayout layout) {
        for(int i=0; i<name.length; i++) {
            final View child = getLayoutInflater().inflate(R.layout.authorities_list_item, null);
            TextView authorityName = child.findViewById(R.id.authority_name);
            authorityName.setText(name[i]);
            TextView authorityRole = child.findViewById(R.id.authority_role);
            authorityRole.setText(role[i]);
            child.setId(i);
            layout.addView(child);
            child.setOnClickListener(new View.OnClickListener() {
                /**
                 * After clicking one of the names, there appears a custom dialog with full info
                 * about the person, including photo (for deans), phone number and email.
                 */
                @Override
                public void onClick(View view) {

                    int id = child.getId();
                    if(layout == deanLayout){
                        if(id==0 || id==6) {
                            AuthorityPopup authorityPopup = new AuthorityPopup();
                            authorityPopup.showDialog(activity, context, dean_full_name[id],
                                    dean_role[id], dean_tel[id],
                                    dean_mail[id], Boolean.TRUE, id);
                        } else {
                            AuthorityPopup authorityPopup = new AuthorityPopup();
                            authorityPopup.showDialogWithDutyHours(activity, context, dean_full_name[id],
                                    dean_role[id], dean_tel[id],
                                    dean_mail[id], Boolean.TRUE, id,
                                    duty_hours[id-1]);
                        }
                    } else if(layout == authoritiesLayout){
                        AuthorityPopup authorityPopup = new AuthorityPopup();
                        authorityPopup.showDialog(activity, context, auth_name[id],
                                auth_role[id], auth_tel[id],
                                auth_mail[id], Boolean.FALSE, id);
                    } else if(layout == othersLayout){
                        AuthorityPopup authorityPopup = new AuthorityPopup();
                        authorityPopup.showDialog(activity, context, others_name[id],
                                others_role[id], others_tel[id],
                                others_mail[id], Boolean.FALSE, id);
                    } else if(layout == extraLayout){
                        AuthorityPopup authorityPopup = new AuthorityPopup();
                        authorityPopup.showDialog(activity, context, ext_name[id],
                                ext_role[id], ext_tel[id],
                                ext_mail[id], Boolean.FALSE, id);
                    } else {}
                }
            });
        }
    }
}
