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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

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
    private OpeningHours openingHours = null;

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

        Button dptWebsiteButton = findViewById(R.id.department_website);
        dptWebsiteButton.setOnClickListener(new OnClickListener() {
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
                //TODO: change to actual server address
                showOpeningHours("http://hmkcode.appspot.com/rest/controller/get.json");
            }
        });

        final Button authoritiesButton = findViewById(R.id.authorities);
        authoritiesButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO popup or activity showing department authorities
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

    private void showOpeningHours(String address) {
        openingHours = new OpeningHours(address);
        openingHours.execute();
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
        //Will hold the server url address once it is established
        //For now it is just a random server for testing purposes
        String address = "http://hmkcode.appspot.com/rest/controller/get.json";

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
            mAuthTask = new UserLoginTask(albumNumber, address);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isNumberValid(String number) {
        return number.length() == 6;
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
     * Performs the GET request on the server
     *
     * @param urlString is the url address of the server
     * @return is the server content (converted to string) to be displayed
     */
    public String HttpGet(String urlString) {
        String result = "";
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            result = inputStreamToString(inputStream);
        } catch (Exception e) {
            Toast.makeText(context, "Coś poszło nie tak :c", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    public JSONArray stringToJSON(String text) {
        JSONObject json = null;
        JSONArray array = null;
        try {
            json = new JSONObject(text);
            array = json.getJSONArray("articleList"); //TODO: change to actual array name
        } catch (JSONException e) {
            Toast.makeText(context, "Coś poszło nie tak :c", Toast.LENGTH_SHORT).show();
        }
        return array;
    }

    /**
     * Represents an asynchronous number checking task.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String albumNumber;
        private String content;
        private String serverAddress;

        UserLoginTask(String email, String address) {
            albumNumber = email;
            content = "";
            serverAddress = address;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                // Simulate network access.
                Thread.sleep(2000);
                content = HttpGet(serverAddress);
            } catch (Exception e) {
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
                intent.putExtra("Server Content", content);
                startActivity(intent);
                mLoginFormView.setVisibility(View.INVISIBLE);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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

    /**
     * Converts the input stream to string
     */
    private String inputStreamToString(InputStream is) {
        String result = "";
        String line = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = br.readLine()) != null) {
                result += line;
            }
            is.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return result;
    }
    //TODO: further data conversion (to a JSON object maybe?)

    /**
     * Asynchronous task attempts to get data from the server
     * then starts the Opening Hours Activity
     * and displays opening hours
     */
    public class OpeningHours extends AsyncTask<Void, Void, Boolean> {

        private String address;
        private String content;
        private JSONArray arr;
        private String displayTitle;
        private String[] titles;
        private String[] hours;

        OpeningHours(String text) {
            address = text;
            displayTitle = "GODZINY OTWARCIA DZIEKANATU";
            titles = new String[]{};
            hours = new String[]{};
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                content = HttpGet(address);
                arr = stringToJSON(content);
                titles = getTitle();
                hours = getTime();
            } catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }
            return true;
        }

        /**
         * Those two methods place the data received from the server into arrays
         * which can later be passed through intent to another activity
         */
        private String[] getTitle() {
            String[] myTitles = new String[arr.length()];
            try {
                for (int i = 0; i < arr.length(); ++i) {
                    myTitles[i] = arr.getJSONObject(i).getString("title"); //TODO: update when the server is established
                }
            } catch (JSONException e) {
                Toast.makeText(context, "cos poszlo nie tak w http get", Toast.LENGTH_SHORT).show();
            }
            return myTitles;
        }

        private String[] getTime() {
            String[] myHours = new String[arr.length()];
            try {
                for (int i = 0; i < arr.length(); i++) {
                    myHours[i] = arr.getJSONObject(i).getString("url"); //TODO: update when the server is established
                }
            } catch (JSONException e) {
                Toast.makeText(context, "cos poszlo nie tak w http get", Toast.LENGTH_SHORT).show();
            }
            return myHours;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Intent intent = new Intent(context, OpeningHoursActivity.class);
                intent.putExtra("Opening Hours", displayTitle);
                intent.putExtra("Array of titles", titles);
                intent.putExtra("Array of hours", hours);
                startActivity(intent);
            } else {
                Toast.makeText(context, "cos poszlo nie tak w post", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

