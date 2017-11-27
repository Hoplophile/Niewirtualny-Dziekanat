package com.example.piotr.niewirtualnydziekanat;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by piotr on 26.11.2017.
 */

public class AuthorityItem extends RelativeLayout {

    public AuthorityItem(Context context, String name, String role){
        super(context);
        View.inflate(context,R.layout.authorities_list_item, this);

        TextView authorityName = findViewById(R.id.authority_name);
        TextView authorityRole = findViewById(R.id.authority_role);

        authorityName.setText(name);
        authorityRole.setText(role);
    }
}
