package com.example.stephanielin.represent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "JPEDqvN8XUs4DRuWN3WtwznDC";
    private static final String TWITTER_SECRET = "hdJtDKdNUw20Xq1CEKsSWh54KJrkihlxriDVCWQy7KNDWQwX0D";


    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.



    private Button ZipButton;
    private Button CurrLocButton;
    private String names = "";
    private String parties = "";
    private String ids = "";
    private String emails = "";
    private String websites = "";
    private String terms = "";
    private String latitude = "";
    private String longitude = "";
    private String twitter_name = "";
    private String zip;
    private boolean called;
    private String county;
    private String state;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient mGoogleApiClient;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
//        Fabric.with(this, new Twitter(authConfig));


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        called = false;

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        if (extras != null) {
            zip = extras.getString("ZIP");
            names = "";
            parties = "";
            ids = "";
            emails = "";
            websites = "";
            terms = "";
            called = false;
            latitude = "";
            longitude = "";
            twitter_name = "";


            String apikey = getString(R.string.sunlight_api_key);
            String host = getString(R.string.sunlight_url);
            String path = "/legislators/locate?zip=" + zip + "&apikey=" + apikey;
            String url = host+path;

            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {

                new DownloadWebpageTask().execute(url);
            } else {
                textView.setText("No network connection available.");
            }
        } else {
            setContentView(R.layout.activity_main);
            Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(myToolbar);
            ActionBar ab = getSupportActionBar();
            ZipButton = (Button) findViewById(R.id.zip_btn);
            CurrLocButton = (Button) findViewById(R.id.curr_loc_btn);

            final EditText zipCode = (EditText) findViewById(R.id.edit_zip);

            ZipButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    names = "";
                    parties = "";
                    ids = "";
                    emails = "";
                    websites = "";
                    terms = "";
                    called = false;
                    latitude = "";
                    longitude = "";
                    zip = zipCode.getText().toString();

                    String apikey = getString(R.string.sunlight_api_key);
                    String host = getString(R.string.sunlight_url);
                    String path = "/legislators/locate?zip=" + zip + "&apikey=" + apikey;
                    String url = host+path;

                    ConnectivityManager connMgr = (ConnectivityManager)
                            getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {

                        new DownloadWebpageTask().execute(url);
                    } else {
                        textView.setText("No network connection available.");
                    }

                }
            });

            CurrLocButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    names = "";
                    parties = "";
                    ids = "";
                    emails = "";
                    websites = "";
                    terms = "";
                    called = false;
                    zip = "";

                    mGoogleApiClient.connect();

                }
            });

        }


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            System.out.println("location not null");
            latitude = String.valueOf(mLastLocation.getLatitude());
            longitude = String.valueOf(mLastLocation.getLongitude());
        }

        String apikey = getString(R.string.sunlight_api_key);
        String host = getString(R.string.sunlight_url);
        String path = "/legislators/locate?latitude=" + latitude + "&longitude=" +
                longitude + "&apikey=" + apikey;
        String url = host+path;
        System.out.println(latitude + " ; " + longitude);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.d("T", "GOING TO DOWNLOAD WEBPAGE TASK");
            new DownloadWebpageTask().execute(url);
        } else {
            //textView.setText("No network connection available.");
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

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
            if (called) {
                geocode(result);
            } else {
                afterAsync(result);
            }

        }

        private String downloadUrl(String myurl) throws IOException {
            try {
                URL url = new URL(myurl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    //Log.d("T", "In download URL");
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

        protected void afterAsync(String result) {
            try {
                JSONObject jObject = new JSONObject(result);

                JSONArray results = jObject.getJSONArray("results");
                for (int i=0; i<results.length(); i++) {
                    JSONObject actor = results.getJSONObject(i);
                    String name = actor.getString("title") + " " +
                            actor.getString("first_name") + " " + actor.getString("last_name");
                    names += name + ";";

                    String party = actor.getString("party");
                    if (party.equals("D")) {
                        parties += "Democrat;";
                    } else {
                        parties += "Republican;";
                    }

                    String id = actor.getString("bioguide_id");
                    ids += id + ";";

                    emails += actor.getString("oc_email") + ";";

                    websites += actor.getString("website") + ";";

                    terms += actor.getString("term_end") + ";";
                    twitter_name += actor.getString("twitter_id") + ";";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Intent phoneIntent = new Intent(getBaseContext(), CongressionalView.class);
            Bundle phoneBundle = new Bundle();
            phoneBundle.putString("NAMES", names);
            phoneBundle.putString("PARTIES", parties);
            phoneBundle.putString("EMAILS", emails);
            phoneBundle.putString("WEBSITES", websites);
            phoneBundle.putString("IDS", ids);
            phoneBundle.putString("TERMS", terms);
            phoneBundle.putString("TWITTER", twitter_name);

            phoneIntent.putExtras(phoneBundle);
            startActivity(phoneIntent);
            called = true;

            /// ************** find county for watch stuff **************** ///
            String host = getString(R.string.geocode_url);
            String path;
            if (zip.equals("")) {
                path = "latlng=" + latitude + "," + longitude + "&key=" + getString(R.string.google_api_key);
            } else {
                path = "address=" + zip + "&key=" + getString(R.string.google_api_key);
            }
            String url = host+path;

            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                Log.d("T", "GOING TO DOWNLOAD WEBPAGE TASK");
                new DownloadWebpageTask().execute(url);
            } else {
                textView.setText("No network connection available.");
            }
        }

        protected void geocode(String result) {
            Log.d("T", "IN GEOCODE");
            try {
                JSONObject jObject = new JSONObject(result);

                JSONArray results = jObject.getJSONArray("results");
                Log.d("T", "result: " + results);
                JSONObject obj = results.getJSONObject(0);
                JSONArray addressStuff = obj.getJSONArray("address_components");
                for (int i=0; i<addressStuff.length(); i++) {
                    JSONObject actor = addressStuff.getJSONObject(i);
                    JSONArray types = actor.getJSONArray("types");
                    if (types.get(0).equals("administrative_area_level_1")) {
                        state = actor.getString("short_name");
                    } else if (types.get(0).equals("administrative_area_level_2")) {
                        Log.d("T", "got a county");
                        county = actor.getString("long_name");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("T", "County: " + county + ", " + state);

            ParseJSON info = new ParseJSON(county, state, getBaseContext());
            String stuff = info.find2012();

            Intent watchIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
            Bundle watchBundle = new Bundle();
            watchBundle.putString("NAMES", names);
            watchBundle.putString("PARTIES", parties);
            watchBundle.putString("IDS", ids);
            watchBundle.putString("TERMS", terms);
            watchBundle.putString("2012", stuff);
            watchIntent.putExtras(watchBundle);

            startService(watchIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

}