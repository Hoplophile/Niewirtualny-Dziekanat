package com.example.piotr.niewirtualnydziekanat;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AuthorityPopup {

    String phone, mail;

    public void showDialog(final Activity activity, final Context context, String auth_name, String auth_role,
                           String auth_phone, String auth_mail, Boolean photo_available, int id){

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);

        phone = auth_phone;
        mail = auth_mail;

        if(photo_available){
            final ImageView photo = dialog.findViewById(R.id.auth_photo);
            photo.setVisibility(View.VISIBLE);
            switch (id){
                case 0:
                    photo.setImageResource(R.drawable.rs);
                    break;
                case 1:
                    photo.setImageResource(R.drawable.ai);
                    break;
                case 2:
                    photo.setImageResource(R.drawable.jg);
                    break;
                case 3:
                    photo.setImageResource(R.drawable.sm);
                    break;
                case 4:
                    photo.setImageResource(R.drawable.kk);
                    break;
                case 5:
                    photo.setImageResource(R.drawable.rst);
                    break;
                case 6:
                    photo.setImageResource(R.drawable.zm);
                    break;
                default:
                    photo.setVisibility(View.GONE);
                    break;
            }
        }
        final TextView nameField = dialog.findViewById(R.id.auth_name);
        nameField.setText(auth_name);

        final TextView roleField = dialog.findViewById(R.id.auth_role);
        roleField.setText(auth_role);

        final Button callButton = dialog.findViewById(R.id.auth_phone);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                context.startActivity(intent);
            }
        });

        final Button mailButton = dialog.findViewById(R.id.auth_mail);
        mailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+mail));
                intent.putExtra(android.content.Intent.EXTRA_EMAIL, mail);
                context.startActivity(Intent.createChooser(intent, "Send via email"));
            }
        });
        dialog.show();
    }
}