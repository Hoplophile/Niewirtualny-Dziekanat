package com.example.piotr.niewirtualnydziekanat;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView hello;
    private String albumNumber;
    private TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        albumNumber = intent.getStringExtra("Album Number");

        hello = findViewById(R.id.album_number);
        hello.setText(albumNumber);

        display = (TextView) findViewById(R.id.textView);
        //Will exectue on the server url address once it is established
        //For now it is just a random server for testing purposes
        new serverView().execute("http://hmkcode.appspot.com/rest/controller/get.json");
    }

    /**
     * This asynchronous task performs the GET request on a server,
     * then converts the data to string and displays it in the text field
     */
    private class serverView extends AsyncTask<String, Void, String> {

        //TODO: enable the POST request
        @Override
        protected String doInBackground(String...urls) {
            String result = "";
            try {
                URL url = new URL(urls[0]);
                //For now only the GET request is enabled
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                result = inputStreamToString(inputStream);
            } catch(Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        /**
         * Converts the input stream to string
         * @param is the input stream
         * @return the result (String) to be displayed
         */
        private String inputStreamToString (InputStream is) {
            String result = "";
            String line = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            try {
                while ((line = br.readLine()) != null) {
                    result += line;
                }
                is.close();
            } catch(IOException ioe) {
                ioe.printStackTrace();
            }
            return result;
        }

        //TODO: further data conversion (to a JSON object maybe?)

        @Override
        protected void onPostExecute(String result) {
            display.setText(result);
        }
    }
}