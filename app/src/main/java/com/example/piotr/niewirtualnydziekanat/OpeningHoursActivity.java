package com.example.piotr.niewirtualnydziekanat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OpeningHoursActivity extends AppCompatActivity {

    private TextView title;
    private String[] types;
    private String[] hours;
    private ListView lv;
    private final Context context = OpeningHoursActivity.this;
    private List<HashMap<String,String>> myList;
    private SimpleAdapter adp;
    private OpeningHours openingHours = null;
    private SharedPreferences saved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_hours);
        saved = this.getPreferences(Context.MODE_PRIVATE);

        //Intent intent = getIntent();
        title = findViewById(R.id.title);
        title.setText("GODZINY OTWARCIA DZIEKANATU");
        showOpeningHours("http://hmkcode.appspot.com/rest/controller/get.json");

        /*types = intent.getStringArrayExtra("Array of titles");
        hours = intent.getStringArrayExtra("Array of hours");*/
        myList = new ArrayList<>();
        lv = findViewById(R.id.lv);
        adp = adapterMethod(myList);
    }

    private void initializer() {
        for (int i = 0; i < types.length; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("Types", types[i]);
            map.put("Hours", hours[i]);
            myList.add(map);
        }
    }

    private SimpleAdapter adapterMethod(List<HashMap<String,String>> llist) {
        SimpleAdapter adapter = new SimpleAdapter(context, llist, R.layout.custom_list, new String[] {"Types", "Hours"},
                new int[] {R.id.type, R.id.content});
        return adapter;
    }

    private void showOpeningHours(String address) {
        openingHours = new OpeningHours(address);
        openingHours.execute();
    }

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

    /**
     * Asynchronous task attempts to get data from the server
     * then starts the Opening Hours Activity
     * and displays opening hours
     * If there is no internet connection then
     * displays the most recently downloaded content
     */
    public class OpeningHours extends AsyncTask<Void, Void, Boolean> {

        private String address;
        private String content;
        private JSONArray arr;

        OpeningHours(String text) {
            address = text;
            types = new String[]{};
            hours = new String[]{};
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                content = HttpGet(address);
                arr = stringToJSON(content);
                types = getTitle();
                hours = getTime();
                if(types.length != 0 && hours.length != 0) { //if able to connect, saves data from server
                    saveData(types, hours);
                }
            } catch (Exception e) {
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
                Toast.makeText(context, "cos poszlo nie tak", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "cos poszlo nie tak", Toast.LENGTH_SHORT).show();
            }
            return myHours;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            /*if (success) {
                Intent intent = new Intent(context, OpeningHoursActivity.class);
                intent.putExtra("Opening Hours", displayTitle);
                intent.putExtra("Array of titles", titles);
                intent.putExtra("Array of hours", hours);
                startActivity(intent); }*/
            if(!success) { //if unable to connect, restores the most recently available data
                restoreData();
                Toast.makeText(context, "brak polaczenia z internetem", Toast.LENGTH_SHORT).show();
            }
            initializer();
            lv.setAdapter(adp);
        }
    }

    /**
     * The two following methods allow the most recent version of opening hours
     * to be displayed when there is no internet connection
     */
    private void saveData(String[] arr1, String[] arr2) {
        SharedPreferences.Editor edit = saved.edit();
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<arr1.length; i++) {
            sb.append(arr1[i]).append(",");
        }
        edit.putString("first set", sb.toString());
        edit.commit();
        sb.setLength(0);
        for(int i=0; i<arr2.length; i++) {
            sb.append(arr2[i]).append(",");
        }
        edit.putString("second set", sb.toString());
        edit.commit();
    }

    public void restoreData() {
        String typesStr = saved.getString("first set", "");
        String hoursStr = saved.getString("second set", "");
        types = typesStr.split(",");
        hours = hoursStr.split(",");
    }
}
