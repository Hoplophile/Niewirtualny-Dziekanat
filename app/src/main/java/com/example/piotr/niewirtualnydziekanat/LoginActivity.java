package com.example.piotr.niewirtualnydziekanat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    private TextView errorView;
    private EditText albumNumberView;
    private View mProgressView;
    private View mLoginFormView;
    private final Context context = LoginActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button dstWebsiteButton = findViewById(R.id.district_website);
        dstWebsiteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.eaiib.agh.edu.pl/"));
                startActivity(intent);
            }
        });

        final Button openingHoursButton = findViewById(R.id.opening_hours);
        openingHoursButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO popup or activity showing opening hours
                Toast.makeText(context, "Tu narazie jest ściernisco, ale będą godziny otwarcia",
                        Toast.LENGTH_SHORT).show();
            }
        });

        final Button authoritiesButton = findViewById(R.id.authorities);
        authoritiesButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO popup or activity showing district authorities
                Toast.makeText(context, "A tu będą prodziekani, panie z dziekanatu itp.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        Button stFanPageButton = findViewById(R.id.st_fan_page);
        stFanPageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.facebook.com/agh.si.tech/"));
                startActivity(intent);

                //TODO invalid facebook fanpage id
                /*try{
                    context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/Hgsfo7tbdUK"));
                    startActivity(intent);
                } catch (Exception e){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.facebook.com/agh.si.tech/"));
                    startActivity(intent);
                }*/
            }

        });

        errorView = findViewById(R.id.error_view);
        albumNumberView = findViewById(R.id.album_number_view);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

    /**
     * Attempts to sign in the number. If there are form errors (empty/too short/long),
     * the errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        errorView.setVisibility(View.GONE);

        // Store values at the time of the login attempt.
        String albumNumber = albumNumberView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid number
        if (TextUtils.isEmpty(albumNumber)) {
            errorView.setVisibility(View.VISIBLE);
            errorView.setText(getString(R.string.error_field_required));
            focusView = albumNumberView;
            cancel = true;
        } else if (!isNumberValid(albumNumber)) {
            errorView.setVisibility(View.VISIBLE);
            errorView.setText(getString(R.string.error_invalid_number));
            focusView = albumNumberView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(albumNumber);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isNumberValid(String number) {
        return number.length()==6;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.

        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    /**
     * Represents an asynchronous number checking task.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String albumNumber;

        UserLoginTask(String email) {
            albumNumber = email;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Toast.makeText(context, "Coś poszło nie tak :c", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("Album Number", albumNumber);
                startActivity(intent);
                mLoginFormView.setVisibility(View.INVISIBLE);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            } else {
                Toast.makeText(context, "Numer nie został znaleziony", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

