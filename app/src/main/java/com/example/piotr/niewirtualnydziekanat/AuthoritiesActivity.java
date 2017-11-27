package com.example.piotr.niewirtualnydziekanat;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.lang.reflect.Array;

public class AuthoritiesActivity extends AppCompatActivity {

    String auth_name[] = {"Małgorzata Tabor", "Alina Wącławska", "Małgorzata Frączek", "Angelika Burgknap-Rumian", "Małgorzata Frączek"};
    String ext_name = "Anna Jasuba";
    String others_name[] = {"Wioletta Serwatka", "Sara Rahman-Kula"};
    String auth_role[] = {"Automatyka i Robotyka", "Elektrotechnika", "Informatyka", "Inżynieria Biomedyczna", "Mikroelektronika w Technice i Medycynie"};
    String others_role[] = {"Sekcja socjalna", "Studia doktoranckie"};
    String ext_role = "Automatyka i Robotyka, Elektrotechnika";
    String auth_mail[] = {"mtabor@agh.edu.pl", "awaclaw@agh.edu.pl", "fraczek@agh.edu.pl", "biomed@agh.edu.pl", "fraczek@agh.edu.pl"};
    String auth_tel[] = {"126174566", "126174893", "126174892", "126172860", "126174892"};
    Context context = AuthoritiesActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorities);

        LinearLayout fullTimeList = findViewById(R.id.full_time_list);
        LinearLayout extramuralList = findViewById(R.id.extramural_list);
        LinearLayout othersList = findViewById(R.id.others_list);

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
    }
}
