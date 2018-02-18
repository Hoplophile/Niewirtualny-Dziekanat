package com.example.piotr.niewirtualnydziekanat;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by piotr on 18.02.2018.
 */

public class AuthorityPopup {

    public void showDialog(Activity activity, String auth_name, String auth_role, String auth_phone, String auth_mail){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);

        final String phone = auth_phone;
        final String mail = auth_mail;

        final TextView nameField = dialog.findViewById(R.id.auth_name);
        nameField.setText(auth_name);

        final TextView roleField = dialog.findViewById(R.id.auth_role);
        roleField.setText(auth_role);

        final Button callButton = dialog.findViewById(R.id.auth_phone);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri call = Uri.parse("tel:" + phone);
                Intent intent = new Intent(Intent.ACTION_CALL, call);
            }
        });

        final Button mailButton = dialog.findViewById(R.id.auth_mail);
        mailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(android.content.Intent.EXTRA_EMAIL, mail);
            }
        });

        dialog.show();
    }
}