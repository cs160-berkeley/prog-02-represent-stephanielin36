package com.example.stephanielin.represent;

import android.app.Fragment;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class CongressionalView extends AppCompatActivity {

    private Button btn;
    private String[] names;
    private String[] parties;
    private String[] emails;
    private String[] websites;
    private String[] ids;
    private String[] terms;
    private ListView congressionalList;
    private String[] twitter_ids;
    private ArrayList<Bitmap> photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressional_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        congressionalList = (ListView) findViewById(R.id.congressional_list_view);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        names = bundle.getString("NAMES").split(";");
        System.out.println("names: " + bundle.getString("NAMES"));
        System.out.println("parties: " + bundle.getString("PARTIES"));

        parties = bundle.getString("PARTIES").split(";");

        emails = bundle.getString("EMAILS").split(";");

        websites = bundle.getString("WEBSITES").split(";");
        photos = new ArrayList<Bitmap>();

        ids = bundle.getString("IDS").split(";");

        terms = bundle.getString("TERMS").split(";");

        twitter_ids = bundle.getString("TWITTER").split(";");

        String host = getString(R.string.photo_url);
        for (int i = 0; i < ids.length; i++) {
            String path = "/" + ids[i] + ".jpg";
            String url = host+path;

            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                new DownloadWebpageTask().execute(url);
            } else {

            }
        }
    }

    protected void AsyncTask() {

        if (photos.size() == ids.length) {
            Bitmap[] imgs = new Bitmap[photos.size()];
            imgs = photos.toArray(imgs);
            final CongressionalAdapter adapter = new CongressionalAdapter(this, names, parties, emails,
                    websites, twitter_ids, imgs, ids, terms);
            congressionalList.setAdapter(adapter);
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
            AsyncTask();
        }

        private String downloadUrl(String myurl) throws IOException {
            Bitmap img = null;
            try {
                URL url = new URL(myurl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream is = urlConnection.getInputStream();
                img = BitmapFactory.decodeStream(is);
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
            photos.add(img);
            return "success";
        }

    }

}
