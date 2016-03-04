package com.example.stephanielin.represent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;


public class MainActivity extends AppCompatActivity {

    private Button ZipButton;
    private Button CurrLocButton;
    private Button mFredButton;
    private Button mLexyButton;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ZipButton = (Button) findViewById(R.id.zip_btn);
        CurrLocButton = (Button) findViewById(R.id.curr_loc_btn);


        ZipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phoneIntent = new Intent(getBaseContext(), CongressionalView.class);
                Intent watchIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
                Bundle bundle = new Bundle();
                bundle.putString("NAME", "Barbara Boxer");
                bundle.putString("PARTY", "Democrat");
                bundle.putInt("IMAGE", R.drawable.barbaraboxer);
                watchIntent.putExtras(bundle);
                Log.d("T", "starting phoneToWatchService");
                startService(watchIntent);
                startActivity(phoneIntent);
            }
        });

        CurrLocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phoneIntent = new Intent(getBaseContext(), CongressionalView.class);
                Intent watchIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
                Bundle bundle = new Bundle();
                bundle.putString("NAME", "Barbara Boxer");
                bundle.putString("PARTY", "Democrat");
                bundle.putInt("IMAGE", R.drawable.barbaraboxer);
                watchIntent.putExtras(bundle);
                startService(watchIntent);
                startActivity(phoneIntent);
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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

}