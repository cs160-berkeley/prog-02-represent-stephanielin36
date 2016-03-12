package com.example.stephanielin.represent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class DetailedView extends AppCompatActivity {

    private String name;
    private String party;
    private String term;
    private String id;
    private Bitmap img;
    private String committees = "";
    private String bills = "";
    private ListView detailedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("T", "ONCREATE");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        detailedList = (ListView) findViewById(R.id.detailed_list_view);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Log.d("T", "got intent");
            name = extras.getString("NAME");
            Log.d("T", name);
            party = extras.getString("PARTY");

            term = extras.getString("TERM");
            id = extras.getString("ID");

            if (extras.containsKey("IMAGE")) {
                byte[] bArray = extras.getByteArray("IMAGE");
                img = BitmapFactory.decodeByteArray(bArray, 0, bArray.length);
            } else {
                String host = getString(R.string.photo_url);
                String path = "/" + id + ".jpg";
                String url = host+path;
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    new DownloadPhotoTask().execute(url);
                } else {

                }
            }

            setTitle(name);

            String apikey = getString(R.string.sunlight_api_key);
            String host = getString(R.string.sunlight_url);
            String path = "/committees?member_ids=" + id + "&apikey=" + apikey;
            String url = host+path;

            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                Log.d("T", "GOING TO DOWNLOAD WEBPAGE TASK");
                new DownloadWebpageTask().execute(url);
                path = "/bills?sponsor_id=" + id + "&apikey=" + apikey;
                url = host+path;
                new DownloadWebpageTask().execute(url);
            } else {

            }

        }

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void afterAsync() {
        if (committees != "" && bills != "") {
            String[] c = committees.split(";");
            String[] b = bills.split(";");
            final DetailedAdapter adapter = new DetailedAdapter(this, img, party, term, c, b);
            detailedList.setAdapter(adapter);
        }
    }

    private class DownloadPhotoTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            afterAsync();
        }

        private String downloadUrl(String myurl) throws IOException {
            Bitmap photo = null;
            try {
                URL url = new URL(myurl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream is = urlConnection.getInputStream();
                photo = BitmapFactory.decodeStream(is);
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
            img = photo;
            return "success";
        }

    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Log.d("T", "OUTPUT: " + result);
            try {
                JSONObject jObject = new JSONObject(result);

                JSONArray results = jObject.getJSONArray("results");
                for (int i=0; i<results.length(); i++) {
                    if (i >= 5) {
                        break;
                    }
                    JSONObject actor = results.getJSONObject(i);
                    if (actor.has("name")) {
                        committees += actor.getString("name") + ";";
                    } else if (actor.has("short_title") && !actor.getString("short_title").equals("null")) {
                        bills += actor.getString("short_title") + ";";
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            afterAsync();
        }

        private String downloadUrl(String myurl) throws IOException {
            try {
                URL url = new URL(myurl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }
    }


}
